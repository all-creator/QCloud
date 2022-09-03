package easy.stars.system.os;

import easy.stars.system.os.interfaces.OperationSystem;
import easy.stars.system.os.object.*;

public class OSController {

    public OSController() {
        final String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) currentSystem = new Windows();
        else if (OS.contains("mac")) currentSystem = new MacOS();
        /*else if (OS.contains("nix")
                || OS.contains("nux")
                || OS.contains("aix")) currentSystem = new Unix();
        else if (OS.contains("sunos")) currentSystem = new SunOS();
         */
    }

    OS currentSystem;

    public OS getCurrentOS() {
        return currentSystem;
    }
}
