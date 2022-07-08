package com.qcloud.server.telegram.command;

import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.bigdata.logging.LogTemplate;
import com.qcloud.server.bigdata.mysql.models.Client;
import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.spring.management.busnes.Session;
import com.qcloud.server.spring.management.busnes.UserServes;
import com.qcloud.server.telegram.utils.enums.Keyboard;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Objects;

public class RegCommand extends Command{

    public RegCommand() {
        super("reg", " - регистрирует ПК в системе. Использование: /reg <UUID>");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.User user, Chat chat, String[] strings) {
        if (getPermissions(user)){
            log.info(LogTemplate.PROCESSING, user.getId(), getCommandIdentifier());

            SendMessage message = new SendMessage(user.getId().toString(),"Успешная регистрация! \nЧто бы завершить текущий сеанс используйте -> /close или /use \"имя пользователя\", для быстрого переключения между машинами. Что бы выбрать все машины введите -> /all");
            User esuser = Bot.getUserRepository().findById(user.getId()).orElse(null);
            if (!UserServes.unregisterUsers.containsKey(strings[0])) {
                Bot.sendMessage(new SendMessage(user.getId().toString(),"Клиент не найден или срок на подключение истёк, повторите запрос"));
                return;
            }
            Client client = UserServes.unregisterUsers.get(strings[0]);
            Objects.requireNonNull(esuser).addClient(client);
            Bot.getClientRepository().save(client);
            Bot.getUserRepository().save(esuser);
            message.setReplyMarkup(Keyboard.HOME.getKeyboard(esuser));
            absSender.execute(message);
            message.setText("Добро пожаловать Домой!");
            execute(absSender, message, user);
            Session session = Session.openNewSession(esuser, client);
            session.sessionAutoConfig();
        } else absSender.execute(new SendMessage(chat.getId().toString(),"Пользователь не имеет доступа -> /start"));
    }
}
