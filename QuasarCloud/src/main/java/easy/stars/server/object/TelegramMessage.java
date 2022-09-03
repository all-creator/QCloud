package easy.stars.server.object;

public class TelegramMessage {
    String message;
    String clientId;

    public TelegramMessage(String message, String clientId) {
        this.message = message;
        this.clientId = clientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
