package easy.stars.server.data;

import easy.stars.App;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    private static String property;
    private static final String[] directoryNames = {"QCloud"};
    private static Path main;
    private static Path res;
    private static Path dat;

    public static void setProperty() {
        property = App.config.getMainDirectory();
    }

    public static void setProperty(String string) {
        property = string;
    }

    public static void createPath() throws IOException {
        setProperty();
        main = Paths.get(property, directoryNames[0]);
        res = Paths.get(property, directoryNames[0], "resources");
        dat = Paths.get(property, directoryNames[0], "property");
        create(main, res, dat);
    }

    private static void create(Path main, Path res, Path dat) throws IOException {
        if (main.toFile().exists()) {
            if (!res.toFile().exists()) Files.createDirectory(res);
            if (!dat.toFile().exists()) Files.createDirectory(dat);
        } else {
            Files.createDirectory(main);
            Files.createDirectory(dat);
            Files.createDirectory(res);
        }
    }


    public static Path getDataPath(String path) {
        return Paths.get(property, directoryNames[0], "property", path);
    }

    public static Path getMainPath(String path) {
        return Paths.get(property, directoryNames[0], path);
    }

    public static Path getMainPath() {
        return main;
    }

    public static Path getResPath() {
        return res;
    }

    public static Path getDatPath() {
        return dat;
    }

    public static Path getResPath(String path) {
        return Paths.get(property, directoryNames[0], "resources", path);
    }

    private FileUtils() {
    }
}
