package com.qcloud.server.telegram.command;

import com.qcloud.server.spring.management.busnes.object.Request;
import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.bigdata.logging.LogTemplate;
import com.qcloud.server.bigdata.mysql.models.User;
import com.qcloud.server.spring.management.busnes.UserServes;
import com.qcloud.server.telegram.utils.enums.UpdateType;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SayCommand extends Command{
    public SayCommand() {
        super("say", " - проговорить текст указанный в сообщении, только macOS");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.User user, Chat chat, String[] strings) {
        if (getPermissions(user)) {
            log.info(LogTemplate.PROCESSING, user.getId(), getCommandIdentifier());

            SendMessage message = new SendMessage();

            message.setChatId(user.getId().toString());

            User esUser = Bot.getUserRepository().findById(user.getId()).orElse(null);

            assert esUser != null;
            esUser.getClients().forEach(client -> UserServes.requestGet(client).add(new Request(UpdateType.COMMAND.setCommand("say", strings))));
        } else absSender.execute(new SendMessage(chat.getId().toString(),"Пользователь не имеет доступа -> /start"));
    }
}
