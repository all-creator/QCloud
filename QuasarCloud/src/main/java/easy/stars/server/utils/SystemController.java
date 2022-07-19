package easy.stars.server.utils;

import easy.stars.server.data.FileUtils;
import easy.stars.system.os.utils.OSUtils;

import java.io.IOException;

public class SystemController {

    public static void setVolume(int volume) throws IOException {
        if (OSUtils.isWindows()) {
            Runtime.getRuntime().exec(new String[]{FileUtils.getResPath("nircmd.exe").toAbsolutePath().toString(), "changesysvolume" ,String.valueOf(volume*655.35)});
        } else if (OSUtils.isMac()) {
            Runtime.getRuntime().exec(new String[]{"osascript", "-e", "set volume output volume "+volume});
        }
    }

    public static void setBrightness(int brightness) throws IOException {
        if (OSUtils.isMac()) {
            Runtime.getRuntime().exec(new String[]{"brightness", String.valueOf(((float)brightness)/100)});
        } else if (OSUtils.isWindows()) {
            Runtime.getRuntime().exec(new String[]{FileUtils.getResPath("nircmd.exe").toAbsolutePath().toString(), "setbrightness" , String.valueOf(brightness)});
        }
    }
}
