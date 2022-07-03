package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.bigdata.logging.LogTemplate;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.io.File;

public class DownloadCommand extends Command{
    public DownloadCommand() {
        super("download", " - отпровляет вам файл актуальной версии QCloudLauncher");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (getPermissions(user)) {
            log.info(LogTemplate.PROCESSING, user.getId(), getCommandIdentifier());

            SendDocument document = new SendDocument();
            document.setDocument(new InputFile(new File("QuasarCloud.zip")));
            document.setChatId(String.valueOf(chat.getId()));
            absSender.execute(document);
        } else absSender.execute(new SendMessage(chat.getId().toString(),"Пользователь не имеет доступа -> /start"));
    }
}
