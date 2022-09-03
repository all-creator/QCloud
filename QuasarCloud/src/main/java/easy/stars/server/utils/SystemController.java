package easy.stars.server.utils;

import easy.stars.App;
import easy.stars.system.os.utils.OSUtils;

import java.io.IOException;
import java.nio.file.Paths;

public class SystemController {

    public static void setBrightness(int brightness) throws IOException {
        if (OSUtils.isMac()) {
            Runtime.getRuntime().exec(new String[]{"brightness", String.valueOf(((float)brightness)/100)});
        } else if (OSUtils.isWindows()) {
            Runtime.getRuntime().exec(new String[]{App.system.getOsController().getCurrentOS().getLibByName("nircmd.exe")
                    .toAbsolutePath().toString(), "setbrightness" , String.valueOf(brightness)});
        }
    }
}
