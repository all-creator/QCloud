package easy.stars.system;

import easy.stars.javafx.controllers.AbstractFXController;
import easy.stars.javafx.controllers.EULA;
import easy.stars.javafx.controllers.NetworkError;
import oshi.SystemInfo;

public class System {

    AbstractFXController mainController = null;

    public void start() {
        mainController = new NetworkError();
        new SystemInfo().getHardware().getNetworkIFs().forEach(a -> {
            if (a.getIPv4addr().length > 0) {
                mainController = new EULA();
            }
        });
        if (mainController instanceof NetworkError) return;
    }

    //TODO: update if necessary
    //TODO: get UUID from OSHI Hardware and check if it on server side. if not - install
    //TODO: check files, if not - install, if yes - load system UUID and check it on server side.
    //TODO: if yes - load, if not - sync (connect)


    public AbstractFXController getMainController() {
        return mainController;
    }
}
