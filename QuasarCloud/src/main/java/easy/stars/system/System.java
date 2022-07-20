package easy.stars.system;

import oshi.SystemInfo;

public class System {

    boolean isConnected = false;

    public void start() {
        new SystemInfo().getHardware().getNetworkIFs().forEach(a -> {
            if (a.getIPv4addr().length > 0) isConnected = true;
        });

    }

    //TODO: check internet connection and show error message if it fails.
    //TODO: update if necessary
    //TODO: get UUID from OSHI Hardware and check if it on server side. if not - install
    //TODO: check files, if not - install, if yes - load system UUID and check it on server side.
    //TODO: if yes - load, if not - sync (connect)


    public boolean isConnected() {
        return isConnected;
    }
}
