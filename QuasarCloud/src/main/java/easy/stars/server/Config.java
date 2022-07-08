package easy.stars.server;

import easy.stars.system.identifier.Client;
import easy.stars.system.utils.OSUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
    List<String> settings = new ArrayList<>();
    private Client client;
    private String mainDirectory;

    public Config(List<String> settings, Client client, File mainDirectory) {
        this.settings = settings;
        this.client = client;
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

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
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

        if (settings != null ? !settings.equals(config.settings) : config.settings != null) return false;
        if (client != null ? !client.equals(config.client) : config.client != null) return false;
        return mainDirectory != null ? mainDirectory.equals(config.mainDirectory) : config.mainDirectory == null;
    }

    @Override
    public int hashCode() {
        int result = settings != null ? settings.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (mainDirectory != null ? mainDirectory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "settings: " + settings +
                ", client: " + client +
                ", mainDirectory: " + mainDirectory +
                '}';
    }
}
