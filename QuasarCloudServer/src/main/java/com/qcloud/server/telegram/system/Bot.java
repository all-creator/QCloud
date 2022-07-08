package com.qcloud.server.telegram.system;

import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.bigdata.mysql.repository.ClientRepository;
import com.qcloud.server.bigdata.mysql.repository.UserRepository;
import com.qcloud.server.spring.management.api.API;
import com.qcloud.server.spring.management.busnes.Session;
import com.qcloud.server.spring.management.busnes.UserServes;
import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.command.*;
import com.qcloud.server.telegram.utils.CallbackParser;
import com.qcloud.server.telegram.utils.enums.Holders;
import com.qcloud.server.telegram.utils.enums.Message;
import com.qcloud.server.telegram.utils.enums.RandomText;
import com.qcloud.server.telegram.utils.enums.UpdateType;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Deprecated(since = "4.0.0")
@Component
public class Bot extends TelegramLongPollingCommandBot {

    private static final Logger log = LogManager.getLogger(Bot.class);
    @Getter
    private static Bot instance;
    @Getter
    private static UserRepository userRepository;
    @Getter
    private static ClientRepository clientRepository;
    @Getter
    private static final Map<String, Session> sessionMap = new HashMap<>();

    public Bot(UserRepository userRepository, ClientRepository clientRepository) {
        log.info("Registering commands...");
        log.info("Registering '/start'...");
        register(new StartCommand());
        log.info("Registering '/reg'...");
        register(new RegCommand());
        log.info("Registering '/download'...");
        register(new DownloadCommand());
        log.info("Registering '/say'...");
        register(new SayCommand());
        log.info("Registering '/run'...");
        register(new RunCommand());
        log.info("Registering '/vol'...");
        register(new Volume());
        log.info("Registering '/bright'...");
        register(new Bright());
        log.info("Registering '/file'...");
        register(new FileCommand());
        log.info("Registering '/auth'...");
        register(new Auth());
        var helpCommand = new HelpCommand(this);
        log.info("Registering '/help'...");
        register(helpCommand);
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        instance = this;
    }

    @Getter
    @Value("${bot.name}")
    private String botUsername;

    @Getter
    @Value("${launcher.ver}")
    private String clientVersion;

    @Getter
    @Value("${bot.token}")
    private String botToken;

    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {
        processTask(update);
    }

    public void processTask(Update update) {
        Runnable r = () -> {
            BotApiMethod<? extends Serializable> message;
            if (update.getCallbackQuery() != null) message = processCallbackUpdate(update);
            else message = processTextUpdate(update);
            if (message != null) {
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        };
        r.run();
    }

    public SendMessage processTextUpdate(Update update) {
        SendMessage message = new SendMessage(String.valueOf(update.getMessage().getChatId()), RandomText.getSRandomString());
        if (UserServes.userCheck(userRepository, update.getMessage().getChatId())) {
            Session session = sessionMap.get(update.getMessage().getChatId().toString());
            if (session == null){
                User user = UserServes.userGet(userRepository, update.getMessage().getFrom());
                if (update.getMessage().getText().equals(Holders.ALL_CLIENT.getString())){
                    message.setText(Message.OBT_VERSION_ONLY_SUPPORT.getMessage());
                    return message;
                }
                user.getClients().forEach(client -> {
                    if (client.getIp().equals(update.getMessage().getText())) Session.openNewSession(user, client);
                });
                if (!sessionMap.containsKey(update.getMessage().getChatId().toString())) {
                    message.setText("Похоже у вас пока нет подключённых машин (((");
                    return message;
                }
            }
            session = sessionMap.get(update.getMessage().getChatId().toString());
            String s = "Отправьте мне только число в диапазоне от 0 до 100";
            String text = update.getMessage().getText();
            if (text.contains(".")) return Holders.HOME.process(session,message);
            switch (session.getState()){
                case "volume" -> {
                    try {
                        int volume = Integer.parseInt(update.getMessage().getText());
                        if (volume < 0 || volume > 100){
                            message.setText(s);
                            return message;
                        }
                        session.setVolumeLevel(volume);
                        message.setText("Отлично, уровень громкости системы был установлен на " + volume);
                        sendMessage(message);
                        session.sessionBrightnessConfig();
                        return null;
                    } catch (NumberFormatException ignored) {
                        message.setText(s);
                        return message;
                    }
                }
                case "brightness" -> {
                    try {
                        int brightness = Integer.parseInt(update.getMessage().getText());
                        if (brightness < 0 || brightness > 100){
                            message.setText(s);
                            return message;
                        }
                        session.setBrightnessLevel(brightness);
                        message.setText("Отлично, уровень яркости системы был установлен на " + brightness);
                        sendMessage(message);
                        session.sessionAuthConfig();
                        return null;
                    } catch (NumberFormatException ignored) {
                        message.setText(s);
                        return message;
                    }
                }
                case "auth" -> {
                    message.setText("Отлично теперь QCloud автоматически авторизует вас во всех необходимых программах");
                    String[] data = update.getMessage().getText().strip().split(":");
                    if (data.length == 2 && API.getLicenseByUsername(update.getMessage().getFrom().getUserName())) {
                        User esUser = Bot.getUserRepository().findById(update.getMessage().getChatId()).orElse(null);
                        esUser.getClients().forEach(client -> UserServes.requestGet(client).add(new Request(UpdateType.FILE.withArgs("create", "auth", data[0], data[1]))));
                    } else message.setText("Что-то пошло не так, проверьте правильность вводимых данных и убедитесь что вы зарегистрированы в нашей главной системе");
                    sendMessage(message);
                    session.sessionFinishConfig();
                    return null;
                }
                default -> {
                    if (Holders.getAll().contains(text)) {
                        Holders holder = Holders.getByString(text);
                        assert holder != null;
                        return holder.process(session, message);
                    }
                    return message;
                }
            }
        } else message.setText("Система не нашла пользователя, а я не разговариваю с незнакомцами \n -> /start");
        return message;
    }

    @SneakyThrows
    public BotApiMethod<? extends Serializable> processCallbackUpdate(Update update) {
        CallbackQuery main = update.getCallbackQuery();
        SendMessage message = new SendMessage(String.valueOf(main.getFrom().getId()),"");
        Session session = sessionMap.get(main.getFrom().getId().toString());
        if (main.getData().equals("getLicense")){
            User user = CallbackParser.createUser(main);
            if (user == null) return CallbackParser.getLicense(main, null, update);
            log.info(() -> UserServes.userCheckLog(userRepository, user));
            saveUser(user);
            return CallbackParser.getLicense(main, user, update);
        } else if (main.getData().startsWith("v:")) {
            execute(CallbackParser.volume(session, main));
            return null;
        } else if (main.getData().startsWith("b:")) {
            execute(CallbackParser.brightness(session, message, main));
            return null;
        }
        return message;
    }

    private static User saveUser(User user) {
        if (!UserServes.userCheck(getUserRepository(), user)) UserServes.userSave(getUserRepository(), user);
        return user;
    }

    public static void sendMessage(SendMessage message) {
        if (message == null || message.getText().equals("")) return;
        try {
            instance.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
