package easy.stars.server.object;

public class TelegramMessage {
    String message;
    byte[] clientId;

    public TelegramMessage(String message, byte[] clientId) {
        this.message = message;
        this.clientId = clientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getClientId() {
        return clientId;
    }

    public void setClientId(byte[] clientId) {
        this.clientId = clientId;
    }
}
