package easy.stars.server.object;

import java.util.Arrays;

public class Hash {
    byte[] hashId;

    public Hash(byte[] hashId) {
        this.hashId = hashId;
    }

    public Hash() {
    }

    public byte[] getHashId() {
        return hashId;
    }

    public void setHashId(byte[] hashId) {
        this.hashId = hashId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hash hash = (Hash) o;

        return Arrays.equals(hashId, hash.hashId);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hashId);
    }
}
