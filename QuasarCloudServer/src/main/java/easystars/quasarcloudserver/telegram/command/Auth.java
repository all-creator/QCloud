package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.spring.management.busnes.UserServes;
import easystars.quasarcloudserver.spring.management.busnes.object.Update;
import easystars.quasarcloudserver.telegram.system.Bot;
import easystars.quasarcloudserver.telegram.utils.enums.UpdateType;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Auth extends Command {

    public Auth() {
        super("auth", " - auth in main system");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage(user.getId().toString(), "Отлично теперь QCloud автоматически авторизует вас во всех необходимых программах");
        if (getPermissions(user)) {
            String[] data = strings[0].strip().split(":");
            if (data.length == 2 /*&& API.getLicenseByUsername(user.getUserName())*/) {
                easystars.quasarcloudserver.bigdata.mysql.models.User esUser = Bot.getUserRepository().findById(user.getId()).orElse(null);
                esUser.getClients().forEach(client -> UserServes.requestGet(client).add(new Update(UpdateType.FILE.withArgs("create", "auth", data[0], data[1]))));
            } else message.setText("Что-то пошло не так, проверьте правильность вводимых данных");
        } else message.setText("Пользователь не имеет доступа -> /start");
        execute(absSender, message, user);
    }
}
