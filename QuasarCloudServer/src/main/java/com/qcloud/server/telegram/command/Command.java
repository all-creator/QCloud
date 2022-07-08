package com.qcloud.server.telegram.command;

import com.qcloud.server.telegram.system.Bot;
import com.qcloud.server.bigdata.logging.LogLevel;
import com.qcloud.server.bigdata.logging.LogTemplate;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@Deprecated(since = "4.0.0")
@EqualsAndHashCode(callSuper = true)
abstract class Command extends BotCommand {
    final Logger log = LogManager.getLogger(getClass());

    Command(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    void execute(AbsSender sender, SendMessage message, User user){
        try {
            sender.execute(message);
            log.log(Level.getLevel(LogLevel.SUCCESS.level.name()), LogTemplate.SUCCESS, user.getId(), getCommandIdentifier());
        } catch (TelegramApiException e) {
            log.error(LogTemplate.EXCEPTION, user.getId(), getCommandIdentifier(), e);
        }
    }

    protected boolean getPermissions(User user){
        com.qcloud.server.bigdata.mysql.models.User esuser = Bot.getUserRepository().findById(user.getId()).orElse(null);
        return esuser != null;
    }
}
