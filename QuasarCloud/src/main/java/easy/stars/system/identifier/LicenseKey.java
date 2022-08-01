package easy.stars.system.identifier;

import java.io.Serializable;
import java.util.UUID;

public class LicenseKey implements Serializable {
    UUID uuidGlobal;
    String uuidLocal;
    byte[] hash;
    byte[] salt;

    public LicenseKey(UUID uuidGlobal, String uuidLocal, byte[] hash, byte[] salt) {
        this.uuidGlobal = uuidGlobal;
        this.uuidLocal = uuidLocal;
        this.hash = hash;
        this.salt = salt;
    }

    public UUID getUuidGlobal() {
        return uuidGlobal;
    }

    public void setUuidGlobal(UUID uuidGlobal) {
        this.uuidGlobal = uuidGlobal;
    }

    public String getUuidLocal() {
        return uuidLocal;
    }

    public void setUuidLocal(String uuidLocal) {
        this.uuidLocal = uuidLocal;
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
