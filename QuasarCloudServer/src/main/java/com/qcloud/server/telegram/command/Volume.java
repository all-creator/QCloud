package com.qcloud.server.telegram.command;

import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.spring.management.busnes.UserServes;
import com.qcloud.server.telegram.utils.enums.UpdateType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Volume extends Command{
    public Volume() {
        super("vol", " - set volume");
    }

    @Override
    public void execute(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.User user, Chat chat, String[] strings) {
        User esuser = Bot.getUserRepository().findById(user.getId()).orElse(null);
        SendMessage message = new SendMessage(user.getId().toString(),"Новое значение громкости: " + strings[0]);
        esuser.getClients().forEach(client -> UserServes.requestAdd(client, new Request(UpdateType.SOUND.withArgs(strings[0]))));
        execute(absSender, message, user);
    }
}
