package easy.stars.system;

import easy.stars.App;
import easy.stars.exceptions.VersionException;
import easy.stars.javafx.AbstractFXController;
import easy.stars.javafx.controllers.EULA;
import easy.stars.javafx.controllers.errors.Error;
import easy.stars.javafx.controllers.errors.NetworkError;
import easy.stars.system.identifier.ComputerIdentifier;
import easy.stars.system.identifier.LicenseKey;
import easy.stars.system.os.OSController;
import oshi.SystemInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class System {

    AbstractFXController mainController = null;

    boolean isSendLog = false;

    final OSController osController = new OSController();

    LicenseKey licenseKey;


    public void start() {
        mainController = new NetworkError();
        new SystemInfo().getHardware().getNetworkIFs().forEach(a -> {
            if (a.getIPv4addr().length > 0) {
                java.lang.System.out.println("Сеть найдена");
                mainController = new EULA();
                osController.getCurrentOS().registerPaths();
                licenseKey = ComputerIdentifier.getLicenseKey();
                //TODO: check it on server side.
                if (licenseKey.getUuid() != null) App.launcher();
                else App.startServer();
            }
        });
        if (mainController instanceof Error) App.launcher();
    }

    public static String getVersion() {
        final Properties properties = new Properties();
        try {
            properties.load(App.class.getResourceAsStream("version.properties"));
        } catch (IOException e) {
            throw new VersionException(e);
        }
        return properties.getProperty("version");
    }



    public OSController getOsController() {
        return osController;
    }

    public AbstractFXController getMainController() {
        return mainController;
    }

    public void setMainController(AbstractFXController mainController) {
        this.mainController = mainController;
    }

    public LicenseKey getLicenseKey() {
        return licenseKey;
    }

    public boolean isSendLog() {
        return isSendLog;
    }

    public void setSendLog(boolean sendLog) {
        isSendLog = sendLog;
    }

    public void creteLockFile() {
        if (this.getOsController().getCurrentOS().getResourceByName("lock.lock").toFile().exists()) return;
        try {
            Files.createFile(this.getOsController().getCurrentOS().getResourceByName("lock.lock"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLockFile(){
        if (!this.getOsController().getCurrentOS().getResourceByName("lock.lock").toFile().exists()) return;
        try {
            Files.delete(this.getOsController().getCurrentOS().getResourceByName("lock.lock"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean lock(){
        return this.getOsController().getCurrentOS().getResourceByName("lock.lock").toFile().exists();
    }
}
