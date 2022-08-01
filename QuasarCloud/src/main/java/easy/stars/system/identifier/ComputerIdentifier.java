package easy.stars.system.identifier;

import easy.stars.system.os.object.SystemInformation;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.UUID;

public class ComputerIdentifier {

    private ComputerIdentifier() {}

    public static LicenseKey generateLicenseKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        UUID uuid = UUID.randomUUID();
        SecureRandom random = new SecureRandom();
        byte[]salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(uuid.toString().toCharArray(), salt, 6553, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new LicenseKey(uuid, SystemInformation.getHardwareUUID(), hash, salt);
    }

    public static class LicenseKeyException extends RuntimeException {

        public LicenseKeyException(String message) {
            super(message);
        }
    }

}
