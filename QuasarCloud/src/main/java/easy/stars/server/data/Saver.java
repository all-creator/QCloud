package easy.stars.server.data;

import easy.stars.App;
import easy.stars.server.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class Saver {
    Config config;

    public Saver(Config config) {
        this.config = config;
    }

    public void saveData() {
        saveConfig();
        savePatch();
    }

    private void savePatch() {
        File file = Paths.get(System.getProperty("user.home"), "patch.est").toFile();
        try (FileOutputStream writerStream = new FileOutputStream(file)) {
            file.createNewFile();
            writerStream.write(config.getMainDirectory().getBytes());
            writerStream.flush();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void saveConfig() {
        File file = FileUtils.getDataPath("config.est").toFile();
        try (FileOutputStream writerStream = new FileOutputStream(file)) {
            file.createNewFile();
            writerStream.write(App.parser.toJson(config).getBytes());
            writerStream.flush();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
