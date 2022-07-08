package com.qcloud.server.telegram.utils.enums;

import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.spring.management.busnes.Session;
import com.qcloud.server.spring.management.busnes.UserServes;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public enum Holders {
    MENU("💻 Меню 🖥"){
        @Override
        public SendMessage process(Session session, SendMessage message) {
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Request(UpdateType.FULL_INFO)));
            message.setReplyMarkup(Keyboard.MENU.getKeyboard(user));
            return message;
        }
    },
    @Deprecated(since = "3.0.0")
    CERBERUS("🔥 Цербер 🔥") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Цербер - платформа позволяющая защитить ваш компьютер от вредоносных файлов.\n" +
                    "На данный момент находиться в стадии тестирования, следите за обновлениями!");
            return message;
        }
    },
    INFO("Инфо 🌐") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Request(UpdateType.FULL_INFO)));
            return null;
        }
    },
    @Deprecated
    ABOUT("О нас ❓") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Главный бот компании - @quasar_infobot");
            return message;
        }
    },
    HOME("Домой 🏠") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Переход в главное меню");
            message.setReplyMarkup(Keyboard.HOME.getKeyboard(session.getUser()));
            return message;
        }
    },
    ALL_CLIENT("Все 💻") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText(Message.OBT_VERSION_ONLY_SUPPORT.getMessage());
            return message;
        }
    },
    INFO_HOME("Помощь ⓘ") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("""
                    *Доступные возможности:*
                    
                    *Информация* - Выводит полную информацию о системе
                    *Файлы* - Переход в режим работы с файлами (Бетта)
                    *Яркость* - Изменение яркости экрана (Только: моноблоки, ноутбуки)
                    *Звук* - Изменение громкости мультимедиа
                    *Перезагрузка/Выключение* - Перезагрузка/Выключение ПК (Может быть не безопасно на macOS)
                    *Команда /say* - проговорить любой текст (Только macOS или Windows с поддержкой синтеза речи)
                    *Команда /run* - полный доступ к терминалу компьютера, выполнение любой команды в фоновом режиме
                    """);
            message.enableMarkdown(true);
            return message;
        }
    },
    INFORMATION_OS("Информация") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Request(UpdateType.FULL_INFO)));
            return null;
        }
    },
    @Deprecated(since = "4.0.0")
    FILES("Файлы") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Для работы с файлами используйте команду /file, напишите *help для получения справки");
            return message;
        }
    },
    BRIGHTNESS("Яркость") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Клиент: " + session.getCurrentClient().getIp() + "\n\nЗначение яркости: " + session.getBrightness());
            message.setReplyMarkup(InlineKeyboard.BRIGHTNESS.getKeyboard());
            return message;
        }
    },
    VOLUME("Звук") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Клиент: " + session.getCurrentClient().getIp() + "\n\nЗначение громкости: " + session.getVolume());
            message.setReplyMarkup(InlineKeyboard.VOLUME.getKeyboard());
            return message;
        }
    },
    RESTART("Перезагрузка") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Request(UpdateType.RELOAD)));
            message.setText("Перезагружаю...");
            return message;
        }
    },
    END_OF_WORK("Выключение") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Request(UpdateType.SHUTDOWN)));
            message.setText("Выключаю...");
            return message;
        }
    },
    @Deprecated(since = "4.0.0")
    VERSION_CONTROL("Контроль версий"){
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("На данный момент доступны версии:\n\nРелиз - 3.0.0\n\nОТБ(открытый бета тест) - 3.5.1\n\nЗТБ(закрытый бета тест) - 3.5.7\n\nДоступные вам версии:\n\nРелиз - 3.0.0\n\nвы можете подать запрос на доступ к ОТБ, ЗБТ доступно только для администрации");
            return message;
        }
    },
    ;

    String string;

    Holders(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static List<String> getAll() {
        List<String> list = new ArrayList<>();
        for (Holders holder : Holders.values()) {
            list.add(holder.getString());
        }
        return list;
    }

    public static Holders getByString(String string) {
        for (Holders holder : Holders.values()) {
            if (holder.getString().equals(string)) return holder;
        }
        return null;
    }

    public abstract SendMessage process(Session session, SendMessage message);
}
