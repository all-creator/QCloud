package com.qcloud.server.telegram.command;

import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.spring.management.busnes.UserServes;
import com.qcloud.server.telegram.utils.enums.UpdateType;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class FileCommand extends Command{
    public FileCommand() {
        super("file", " - file control");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Bot.getUserRepository().findById(user.getId()).get().getClients().forEach(client -> UserServes.requestAdd(client, new Request(UpdateType.FILE.withArgs(strings))));
    }
}
