package easy.stars.system;

import easy.stars.App;
import easy.stars.exceptions.VersionException;
import easy.stars.javafx.AbstractFXController;
import easy.stars.javafx.controllers.EULA;
import easy.stars.javafx.controllers.errors.NetworkError;
import easy.stars.system.identifier.ComputerIdentifier;
import easy.stars.system.identifier.LicenseKey;
import easy.stars.system.os.OSController;
import oshi.SystemInfo;

import java.io.IOException;
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
                mainController = new EULA();
                osController.getCurrentOS().registerPaths();
                licenseKey = ComputerIdentifier.getLicenseKey();
                //TODO: check files, if not - install, if yes - load system UUID and check it on server side.

                //TODO: if yes - load, if not - register
            }
        });
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

    public LicenseKey getLicenseKey() {
        return licenseKey;
    }

    public boolean isSendLog() {
        return isSendLog;
    }

    public void setSendLog(boolean sendLog) {
        isSendLog = sendLog;
    }
}
