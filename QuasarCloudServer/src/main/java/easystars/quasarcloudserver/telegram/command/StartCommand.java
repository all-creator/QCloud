package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.bigdata.logging.LogTemplate;
import easystars.quasarcloudserver.telegram.utils.ButtonAPI;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends Command{
    public StartCommand() {
        super("start", " - начало работы с ботом");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info(LogTemplate.PROCESSING, user.getId(), getCommandIdentifier());

        SendPhoto photo = new SendPhoto();
        photo.setPhoto(new InputFile("https://sun9-12.userapi.com/impg/n-faxxeeW5xDpWMMtApyERRRPgfsFmdgMIJVBA/LFiklyskYHc.jpg?size=1024x576&quality=96&sign=69547815095e40c4019db4acd778291d&type=album"));
        photo.setChatId(String.valueOf(chat.getId()));
        absSender.execute(photo);

        SendMessage message = new SendMessage(String.valueOf(user.getId()),"Давайте проверим вашу лицензию:");
        message.setReplyMarkup(ButtonAPI.createInlineKeyboard(new String[][]{{"Проверить"}}, new String[][]{{"getLicense"}}));

        execute(absSender, message, user);
    }
}
