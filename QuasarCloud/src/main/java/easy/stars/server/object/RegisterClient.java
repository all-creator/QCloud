package easy.stars.server.object;

import easy.stars.system.identifier.LicenseKey;

public class RegisterClient {
    String uuid;
    byte[] hash;

    public RegisterClient(LicenseKey client) {
        uuid = client.getUuidGlobal();
        hash = client.getUuid();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }
}
