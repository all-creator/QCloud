package easystars.quasarcloudserver.bigdata.logging;

public class LogTemplate {

    private LogTemplate() {
        throw new IllegalCallerException();
    }

    public static final String EXCEPTION = "Ползователь {} полуил ошибку, когда использовал '{}' команду..";
    public static final String PROCESSING = "Ползователь {} использовал '{}' команду...";
    public static final String SUCCESS = "Ползователь {} успешно использовал '{}' команду";
    public static final String SEND = "Клиент {} отправил сообщение: '{}'";
    public static final String ERROR = "У клиента {} появилась ошибка: '{}'";
}
