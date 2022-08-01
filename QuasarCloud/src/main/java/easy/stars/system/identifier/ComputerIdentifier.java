package easy.stars.system.identifier;

import easy.stars.App;
import easy.stars.system.os.object.SystemInformation;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.UUID;

public class ComputerIdentifier {

    private ComputerIdentifier() {}

    private static LicenseKey generateLicenseKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        StringBuilder uuid = new StringBuilder(UUID.randomUUID().toString());
        uuid.append(":").append(SystemInformation.getHardwareUUID());
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(uuid.toString().toCharArray(), salt, 6553, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        saveLicenseKey(uuid.toString());
        return new LicenseKey(uuid.toString().split(":")[0], SystemInformation.getHardwareUUID(), hash);
    }

    public static LicenseKey getLicenseKey() throws LicenseKeyException {
        try {
            return loadLicenseKey();
        } catch (IOException e) {
            try {
                return generateLicenseKey();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
                throw new ComputerIdentifier.LicenseKeyException("Failed try to generate a license key: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            throw new ComputerIdentifier.LicenseKeyException("Failed try to load a license key: " + e.getMessage());
        }
    }

    private static LicenseKey loadLicenseKey() throws IOException, ClassNotFoundException {
        try (ObjectInputStream fr = new ObjectInputStream(new FileInputStream(new File(
                Path.of(App.system.getOsController().getCurrentOS().getResourcePath().toString(), "key.dat").toUri()
        )))) {
            return (LicenseKey) fr.readObject();
        }
    }

    private static void saveLicenseKey(String uuid) {
        try (ObjectOutputStream fw = new ObjectOutputStream(new FileOutputStream(
                Path.of(App.system.getOsController().getCurrentOS().getResourcePath().toString(), "key.dat").toFile()
        ))) {
            fw.writeObject(new LicenseKey(uuid.split(":")[0], uuid.split(":")[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class LicenseKeyException extends RuntimeException {

        public LicenseKeyException(String message) {
            super(message);
        }
    }

}
