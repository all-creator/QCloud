package easy.stars.system.identifier;

import java.io.Serializable;

public class LicenseKey implements Serializable {
    String uuidGlobal;
    String uuidLocal;
    byte[] uuid;

    public LicenseKey(String uuidLocal, String uuidGlobal, byte[] uuid) {
        this.uuidGlobal = uuidGlobal;
        this.uuidLocal = uuidLocal;
        this.uuid = uuid;
    }

    public LicenseKey(String uuidLocal, String uuidGlobal) {
        this.uuidGlobal = uuidGlobal;
        this.uuidLocal = uuidLocal;
    }

    public String getUuidLocal() {
        return uuidLocal;
    }

    public void setUuidLocal(String uuidLocal) {
        this.uuidLocal = uuidLocal;
    }

    public String getUuidGlobal() {
        return uuidGlobal;
    }

    public void setUuidGlobal(String uuidGlobal) {
        this.uuidGlobal = uuidGlobal;
    }

    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }
}
