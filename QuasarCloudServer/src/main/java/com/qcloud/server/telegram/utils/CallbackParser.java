package com.qcloud.server.telegram.utils;

import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.spring.management.busnes.Session;
import com.qcloud.server.spring.management.api.API;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.telegram.utils.enums.InlineKeyboard;
import com.qcloud.server.telegram.utils.enums.Keyboard;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CallbackParser {

    static final Logger log = LogManager.getLogger();

    public static EditMessageText volume(Session session, CallbackQuery main){
        int volume = parseVolume(main.getData().split(":")[1], session.getVolume());
        session.setVolumeLevel(volume);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Клиент: " + session.getCurrentClient().getIp() + "\n\nЗначение громкости: " + session.getVolume());
        editMessageText.setChatId(main.getMessage().getChatId().toString());
        editMessageText.setMessageId(main.getMessage().getMessageId());
        editMessageText.setReplyMarkup(InlineKeyboard.VOLUME.getKeyboard());
        return editMessageText;
    }

    private static int parseVolume(String volume, int currentVolume) {
        if (volume.startsWith("-")) currentVolume -= Integer.parseInt(volume.substring(1));
        else if (volume.startsWith("+")) currentVolume += Integer.parseInt(volume.substring(1));
        else if (volume.endsWith("%")) currentVolume = Integer.parseInt(volume.substring(0, volume.length() - 1));
        else return currentVolume;
        if (currentVolume > 100) currentVolume = 100;
        if (currentVolume < 0) currentVolume = 0;
        return currentVolume;
    }

    @Deprecated(since = "4.0.0")
    public static EditMessageText brightness(Session session, SendMessage message, CallbackQuery main){
        String data = main.getData().split(":")[1];
        int brightness = session.getBrightness();
        switch (data){
            case "+1" -> brightness++;
            case "+5" -> brightness += 5;
            case "+10" -> brightness += 10;
            case "+25" -> brightness += 25;
            case "+50" -> brightness += 50;
            case "-50" -> brightness -= 50;
            case "-25" -> brightness -= 25;
            case "-10" -> brightness -= 10;
            case "-5" -> brightness -= 5;
            case "-1" -> brightness--;
            case "0%" -> brightness = 0;
            case "50%" -> brightness = 50;
            case "100%" -> brightness = 100;
            default -> message.setText("Что-то явно пошло не так, скорее всего я ещё не поддерживаю эту функцию, но её точно уже реализуют. Следите за обновлениями!");
        }
        if (brightness > 100) brightness = 100;
        if (brightness < 0) brightness = 0;
        session.setBrightnessLevel(brightness);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Клиент: " + session.getCurrentClient().getIp() + "\n\nЗначение яркости: " + session.getBrightness());
        editMessageText.setChatId(main.getMessage().getChatId().toString());
        editMessageText.setMessageId(main.getMessage().getMessageId());
        editMessageText.setReplyMarkup(InlineKeyboard.BRIGHTNESS.getKeyboard());
        return editMessageText;
    }

    public static User createUser(CallbackQuery main){
        if (Bot.getUserRepository().existsById(main.getFrom().getId())){
            return Bot.getUserRepository().findById(main.getFrom().getId()).orElse(null);
        }
        User user = new User();
        user.setTelegramId(main.getFrom().getId());
        user.setKeyGen("Not-used");
        if (main.getFrom().getUserName().equals("mc_maksim"))
            user.setLicense(new License("Administration", LocalDateTime.now(), LocalDateTime.MAX).parseToDataBase());
        else if (API.getLicenseByUsername(main.getFrom().getUserName())) user.setLicense(new License("User", LocalDateTime.now(), LocalDateTime.now().plusDays(30)).parseToDataBase());
        else user.setLicense(new License("Tester", LocalDateTime.now(), LocalDateTime.now().plusDays(14)).parseToDataBase());
        return user;
    }

    @Deprecated(since = "4.0.0")
    @SneakyThrows
    public static EditMessageText getLicense(CallbackQuery main, User user, Update update) {

        EditMessageText.EditMessageTextBuilder editMessageText = EditMessageText.builder()
                .chatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()))
                .messageId(update.getCallbackQuery().getMessage().getMessageId());

        StringBuilder builder = new StringBuilder();
        editMessageText.text("Пользователь " + main.getFrom().getUserName() + " не имеет доступа.");

        if (user == null) return editMessageText.build();

        License license = new License(user.getLicense());

        if (!license.isActive()) return editMessageText.build();

        switch (license.level) {
            case "Administration" -> builder.append("Добро пожаловать администратор ");
            case "User" -> builder.append("Добро пожаловать ");
            case "Tester" ->
                    builder.append("Вы не зарегистрированы в системе, и имеете ограниченную лицензию\n   Лицензия истекает через: ")
                            .append(license.toLogString()).append("\n\nДобро пожаловать ");
        }
        builder.append(main.getFrom().getUserName()).append("!\n\n");
        if (Scripts.hasClient(main.getFrom().getId())) {
            builder.append("Используйте команду /reg <UUID> для добавления ПК в систему или выберите из доступных:");
            //Keyboard.CLIENTS.getKeyboard(user);
        } else {
            builder.append("Я не нашёл машин привязанных к данному пользователю, начинаю загрузку...\n\n");
            builder.append("Используйте команду /reg <UUID> для добавления ПК в систему.");
            SendDocument document = new SendDocument();
            document.setDocument(new InputFile(new File("QuasarCloud.zip")));
            document.setChatId(String.valueOf(main.getMessage().getChatId()));
            Bot.getInstance().execute(document);
        }
        log.log(Level.FATAL, editMessageText.build().getInlineMessageId());
        editMessageText.text(builder.toString());
        return editMessageText.build();
    }
}
