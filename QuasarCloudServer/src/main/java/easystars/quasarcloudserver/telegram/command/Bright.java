package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.spring.management.busnes.UserServes;
import easystars.quasarcloudserver.telegram.system.Bot;
import easystars.quasarcloudserver.telegram.utils.enums.UpdateType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Bright extends Command{
    public Bright() {
        super("bright", " - set brightness");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        easystars.quasarcloudserver.bigdata.mysql.models.User esuser = Bot.getUserRepository().findById(user.getId()).orElse(null);
        SendMessage message = new SendMessage(user.getId().toString(),"Новое значение яркости: " + strings[0]);
        esuser.getClients().forEach(client -> UserServes.requestAdd(client, new easystars.quasarcloudserver.spring.management.busnes.object.Update(UpdateType.LIGHT.withArgs(strings[0]))));
        execute(absSender, message, user);
    }
}
