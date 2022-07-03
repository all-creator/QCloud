package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.bigdata.logging.LogTemplate;
import easystars.quasarcloudserver.spring.management.busnes.UserServes;
import easystars.quasarcloudserver.spring.management.busnes.object.Update;
import easystars.quasarcloudserver.telegram.system.Bot;
import easystars.quasarcloudserver.telegram.utils.enums.UpdateType;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SayCommand extends Command{
    public SayCommand() {
        super("say", " - проговорить текст указанный в сообщении, только macOS");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (getPermissions(user)) {
            log.info(LogTemplate.PROCESSING, user.getId(), getCommandIdentifier());

            SendMessage message = new SendMessage();

            message.setChatId(user.getId().toString());

            easystars.quasarcloudserver.bigdata.mysql.models.User esUser = Bot.getUserRepository().findById(user.getId()).orElse(null);

            assert esUser != null;
            esUser.getClients().forEach(client -> UserServes.requestGet(client).add(new Update(UpdateType.COMMAND.setCommand("say", strings))));
        } else absSender.execute(new SendMessage(chat.getId().toString(),"Пользователь не имеет доступа -> /start"));
    }
}
