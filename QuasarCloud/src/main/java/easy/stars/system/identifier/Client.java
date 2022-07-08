package easy.stars.system.identifier;

import java.util.UUID;

public class Client {
    UUID uuid;
    byte[] hash;
    byte[] salt;

    public Client(UUID uuid, byte[] hash, byte[] salt) {
        this.uuid = uuid;
        this.hash = hash;
        this.salt = salt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
