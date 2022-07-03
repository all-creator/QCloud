package easy.stars.server.log;

public enum LogBase {
    ALREADY_HAVE_SALT("Пользователь обновил значение локальной соли"),
    SET_SALT("Пользователь устоновил значение локальной соли"),
    ALREADY_HAVE_DATA("Пользователь обновил значение информации о пользователе"),
    SET_DATA("Лаунчер обновил значение информации о пользователе"),
    LOAD_DATA_SUCCESS("Лаунчер загрузил информацию о пользователе успешно"),
    LOAD_SALT_SUCCESS("Лаунчер загрузил информацию о локальной соли успешно"),
    LOAD_DATA_NOT_SUCCESS("Лаунчер не смог загрузить информацию о пользователе"),
    LOAD_SALT_NOT_SUCCESS("Лаунчер не смог загрузить  информацию о локальной соли"),
    SUCCESS_START_SERVER("Лаунчер успешно запустил сервер")
    ;

    String message;

    LogBase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
