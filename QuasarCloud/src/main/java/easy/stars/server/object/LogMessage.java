package easy.stars.server.object;

import java.util.Arrays;

public class LogMessage {
    String version;
    byte[] client;
    String message;

    public LogMessage(String version, byte[] client, String message) {
        this.version = version;
        this.client = client;
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getClient() {
        return client;
    }

    public void setClient(byte[] client) {
        this.client = client;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogMessage that = (LogMessage) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (!Arrays.equals(client, that.client)) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(client);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
