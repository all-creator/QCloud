package easy.stars.server;

import easy.stars.system.identifier.LicenseKey;
import easy.stars.system.os.utils.OSUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Config {
    List<String> settings = new ArrayList<>();
    private LicenseKey licenseKey;
    private String mainDirectory;

    public Config(List<String> settings, LicenseKey licenseKey, File mainDirectory) {
        this.settings = settings;
        this.licenseKey = licenseKey;
        this.mainDirectory = mainDirectory.getAbsolutePath();
    }

    public Config() {
    }

    public void setStartup(boolean startup) {
        if (OSUtils.isMac()) {
            String plistText =
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDsPropertyList-1.0.dtd">
            <plist version="1.0">
                <dict>
                    <key>Label</key>
                    <string>ru.sp.QCloud</string>
                    <key>ProgramArguments</key>
                    <array>
                        <string>/usr/bin/open</string>
                        <string>/Applications/ChatMySquperApp</string>
                    </array>
                    <key>RunAtLoad</key>
                    <true/>
                </dict>
            </plist>
            """;
            File plist = new File(System.getProperty("user.home")
                    + "/Library/LaunchAgents/ru.sp.QCloud.plist");
            if (startup) {
                try (FileWriter writeFile = new FileWriter(plist)) {
                    writeFile.write(plistText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (plist.exists()) {
                    plist.delete();
                }
            }
        } else if (OSUtils.isWindows()){
            String s = "";
            if(startup){
                s = "cmd /C reg add HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                        "QCloud /t REG_SZ /d \""+mainDirectory+"\\EasyStars\\QCloud\\QuasarCloud\\bin\\launcher.bat\" /f";
            }else{
                s = "cmd /C reg delete HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run " +
                        "/v QCloud /f\r\n";
            }
            try {
                Runtime.getRuntime().exec(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setClient(LicenseKey licenseKey) {
        this.licenseKey = licenseKey;
    }

    public LicenseKey getClient() {
        return licenseKey;
    }

    public String getMainDirectory() {
        return mainDirectory;
    }

    public void setMainDirectory(String mainDirectory) {
        this.mainDirectory = mainDirectory;
    }

    public List<String> getSettings() {
        return settings;
    }

    public void addSetting(String setting){
        settings.add(setting);
    }

    public void setSettings(List<String> settings) {
        this.settings = settings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config config = (Config) o;

        if (!Objects.equals(settings, config.settings)) return false;
        if (!Objects.equals(licenseKey, config.licenseKey)) return false;
        return Objects.equals(mainDirectory, config.mainDirectory);
    }

    @Override
    public int hashCode() {
        int result = settings != null ? settings.hashCode() : 0;
        result = 31 * result + (licenseKey != null ? licenseKey.hashCode() : 0);
        result = 31 * result + (mainDirectory != null ? mainDirectory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "settings: " + settings +
                ", client: " + licenseKey +
                ", mainDirectory: " + mainDirectory +
                '}';
    }
}
