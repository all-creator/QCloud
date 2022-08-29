package easy.stars.server.object;

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
}
