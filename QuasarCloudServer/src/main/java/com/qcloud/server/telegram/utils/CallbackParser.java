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
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;

public class CallbackParser {

    static final Logger log = LogManager.getLogger();

    public static EditMessageText volume(Session session, SendMessage message, CallbackQuery main){
        String data = main.getData().split(":")[1];
        int volume = session.getVolume();
        switch (data){
            case "+1" -> volume ++;
            case "+5" -> volume += 5;
            case "+10" -> volume += 10;
            case "+25" -> volume += 25;
            case "+50" -> volume += 50;
            case "-50" -> volume -= 50;
            case "-25" -> volume -= 25;
            case "-10" -> volume -= 10;
            case "-5" -> volume -= 5;
            case "-1" -> volume --;
            case "0%" -> volume = 0;
            case "50%" -> volume = 50;
            case "100%" -> volume = 100;
            default -> message.setText("Что-то явно пошло не так, скорее всего я ещё не поддерживаю эту функцию, но её точно уже реализуют. Следите за обновлениями!");
        }
        if (volume > 100) volume = 100;
        if (volume < 0) volume = 0;
        session.setVolumeLevel(volume);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Клиент: " + session.getCurrentClient().getIp() + "\n\nЗначение громкости: " + session.getVolume());
        editMessageText.setChatId(main.getMessage().getChatId().toString());
        editMessageText.setMessageId(main.getMessage().getMessageId());
        editMessageText.setReplyMarkup(InlineKeyboard.VOLUME.getKeyboard());
        return editMessageText;
    }

    private int setVolume(String volume, int currentVolume) {
        if (volume.startsWith("-")) {
            currentVolume -= Integer.parseInt(volume.substring(1));
        } else if (volume.startsWith("+")) {
            currentVolume += Integer.parseInt(volume.substring(1));
        }
        if (currentVolume > 100) currentVolume = 100;
        if (currentVolume < 0) currentVolume = 0;
        return currentVolume;
    }

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
        if (main.getFrom().getUserName().equals("mc_maksim")) user.setLicense("Administration");
        else if (API.getLicenseByUsername(main.getFrom().getUserName())) user.setLicense("User");
        else user = null;
        return user;
    }

    @SneakyThrows
    public static EditMessageText getLicense(CallbackQuery main, User user, Update update) {

        EditMessageText.EditMessageTextBuilder editMessageText = EditMessageText.builder().chatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId())).messageId(update.getCallbackQuery().getMessage().getMessageId());

        StringBuilder builder = new StringBuilder();
        editMessageText.text("Пользователь " + main.getFrom().getUserName() + " не имеет доступа.");

        if (user == null) return editMessageText.build();

        if (user.getLicense().equals("Administration")) builder.append("Добро пожаловать администратор ");
        else if (user.getLicense().equals("User")) builder.append("Добро пожаловать ");
        builder.append(main.getFrom().getUserName()).append("!\n\n");
        if (Scripts.hasClient(main.getFrom().getId())) {
            builder.append("Используйте команду /reg <UUID> для добавления ПК в систему или выберите из доступных:");
            editMessageText.replyMarkup((InlineKeyboardMarkup) Keyboard.CLIENTS.getKeyboard(user));
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
