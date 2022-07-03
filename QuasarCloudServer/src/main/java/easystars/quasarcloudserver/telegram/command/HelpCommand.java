package easystars.quasarcloudserver.telegram.command;

import easystars.quasarcloudserver.bigdata.logging.LogTemplate;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends Command {

    private final ICommandRegistry mCommandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", " - список доступных команд");
        mCommandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info(LogTemplate.PROCESSING, user.getId(), getCommandIdentifier());

        StringBuilder helpMessageBuilder = new StringBuilder("*Available commands:*\n");
        mCommandRegistry.getRegisteredCommands().forEach(cmd -> helpMessageBuilder.append("*").append(cmd.getCommandIdentifier())
                .append("*").append(cmd.getDescription()).append("\n"));

        SendMessage helpMessage = new SendMessage();
        helpMessage.enableMarkdown(true);
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.setText(helpMessageBuilder.toString());

        log.info(helpMessage.getText());
        execute(absSender, helpMessage, user);
    }
}
