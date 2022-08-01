package easy.stars.system;

import easy.stars.javafx.AbstractFXController;
import easy.stars.javafx.controllers.EULA;
import easy.stars.javafx.controllers.errors.NetworkError;
import easy.stars.system.identifier.ComputerIdentifier;
import easy.stars.system.identifier.LicenseKey;
import easy.stars.system.os.OSController;
import oshi.SystemInfo;

public class System {

    AbstractFXController mainController = null;

    final OSController osController = new OSController();

    LicenseKey licenseKey;


    public void start() {
        mainController = new NetworkError();
        new SystemInfo().getHardware().getNetworkIFs().forEach(a -> {
            if (a.getIPv4addr().length > 0) {
                mainController = new EULA();
                osController.getCurrentOS().registerPaths();
                update();//TODO: update if necessary
                licenseKey = ComputerIdentifier.getLicenseKey();
                connect();//TODO: get UUID from OSHI Hardware and check if it on server side. if not - install

                //TODO: check files, if not - install, if yes - load system UUID and check it on server side.

                //TODO: if yes - load, if not - register
            }
        });
    }

    private void update() {

    }

    private void connect() {

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
}
