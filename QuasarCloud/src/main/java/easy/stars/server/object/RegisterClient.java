package easy.stars.server.object;

import easy.stars.system.identifier.LicenseKey;

import java.util.Arrays;
import java.util.Objects;

public class RegisterClient {
    String uuid;
    byte[] hash;

    public RegisterClient(LicenseKey client) {
        uuid = client.getUuid_global().toString();
        hash = client.getHash();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisterClient registerClient = (RegisterClient) o;

        if (!Objects.equals(uuid, registerClient.uuid)) return false;
        return Arrays.equals(hash, registerClient.hash);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(hash);
        return result;
    }
}
