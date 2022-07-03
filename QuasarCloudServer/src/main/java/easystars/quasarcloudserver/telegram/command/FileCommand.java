package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.spring.management.busnes.UserServes;
import easystars.quasarcloudserver.telegram.system.Bot;
import easystars.quasarcloudserver.telegram.utils.enums.UpdateType;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class FileCommand extends Command{
    public FileCommand() {
        super("file", " - file control");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Bot.getUserRepository().findById(user.getId()).get().getClients().forEach(client -> UserServes.requestAdd(client, new easystars.quasarcloudserver.spring.management.busnes.object.Update(UpdateType.FILE.withArgs(strings))));
    }
}
