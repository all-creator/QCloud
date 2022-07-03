package easy.stars.server.object;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelegramMessage that = (TelegramMessage) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return Arrays.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(clientId);
        return result;
    }
}
