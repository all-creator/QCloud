package easystars.quasarcloudserver.telegram.utils.enums;

import easystars.quasarcloudserver.bigdata.mysql.models.User;
import easystars.quasarcloudserver.spring.management.busnes.Session;
import easystars.quasarcloudserver.spring.management.busnes.UserServes;
import easystars.quasarcloudserver.spring.management.busnes.object.Update;
import easystars.quasarcloudserver.telegram.system.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public enum Holders {
    MENU("💻 Меню 🖥"){
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Полная информация о системе:");
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Update(UpdateType.FULL_INFO)));
            message.setReplyMarkup(Keyboard.MENU.getKeyboard(user));
            return message;
        }
    },
    CERBERUS("🔥 Цербер 🔥") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            message.setText("Цербер - платформа позволющая защитить ваш компьютер от вредоностных файлов.\n" +
                    "На данный момент находиться в стадии тестирования, следите за обновлениями!");
            return message;
        }
    },
    INFO("Инфо 🌐") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Update(UpdateType.FULL_INFO)));
            return null;
        }
    },
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
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Update(UpdateType.FULL_INFO)));
            return null;
        }
    },
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
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Update(UpdateType.RELOAD)));
            message.setText("Перезагружаю...");
            return message;
        }
    },
    END_OF_WORK("Выключение") {
        @Override
        public SendMessage process(Session session, SendMessage message) {
            User user = Bot.getUserRepository().findById(Long.parseLong(message.getChatId())).orElse(null);
            user.getClients().forEach(client -> UserServes.requestAdd(client, new Update(UpdateType.SHUTDOWN)));
            message.setText("Выключаю...");
            return message;
        }
    },
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
