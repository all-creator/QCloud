package com.qcloud.server.telegram.command;

import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.spring.management.busnes.UserServes;
import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.telegram.utils.enums.UpdateType;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Auth extends Command {

    public Auth() {
        super("auth", " - auth in main system");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage(user.getId().toString(), "Отлично теперь QCloud автоматически авторизует вас во всех необходимых программах");
        if (getPermissions(user)) {
            String[] data = strings[0].strip().split(":");
            if (data.length == 2 /*&& API.getLicenseByUsername(user.getUserName())*/) {
                User esUser = Bot.getUserRepository().findById(user.getId()).orElse(null);
                esUser.getClients().forEach(client -> UserServes.requestGet(client).add(new Request(UpdateType.FILE.withArgs("create", "auth", data[0], data[1]))));
            } else message.setText("Что-то пошло не так, проверьте правильность вводимых данных");
        } else message.setText("Пользователь не имеет доступа -> /start");
        execute(absSender, message, user);
    }
}
