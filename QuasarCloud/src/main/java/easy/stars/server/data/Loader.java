package easy.stars.server.data;

import easy.stars.App;
import easy.stars.server.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class Loader {
    Config config;

    public Config loadData() {
        preLoad();
        loadConfig();
        return config;
    }

    private void preLoad() {
        File file = Paths.get(System.getProperty("user.home"), "patch.est").toFile();
        try (FileInputStream readerStream = new FileInputStream(file)) {
            FileUtils.setProperty(new String(readerStream.readAllBytes()));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }


    private void loadConfig() {
        File file = FileUtils.getDataPath("config.est").toFile();
        try (FileInputStream readerStream = new FileInputStream(file)) {
            config = App.parser.fromJson(new String(readerStream.readAllBytes()), Config.class);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}