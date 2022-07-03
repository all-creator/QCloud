package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.spring.management.busnes.UserServes;
import easystars.quasarcloudserver.telegram.system.Bot;
import easystars.quasarcloudserver.telegram.utils.enums.UpdateType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Volume extends Command{
    public Volume() {
        super("vol", " - set volume");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        easystars.quasarcloudserver.bigdata.mysql.models.User esuser = Bot.getUserRepository().findById(user.getId()).orElse(null);
        SendMessage message = new SendMessage(user.getId().toString(),"Новое значение громкости: " + strings[0]);
        esuser.getClients().forEach(client -> UserServes.requestAdd(client, new easystars.quasarcloudserver.spring.management.busnes.object.Update(UpdateType.SOUND.withArgs(strings[0]))));
        execute(absSender, message, user);
    }
}
