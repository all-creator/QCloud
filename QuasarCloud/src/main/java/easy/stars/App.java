package easy.stars;

import com.google.gson.Gson;
import easy.stars.javafx.AbstractFXController;
import easy.stars.server.Config;
import easy.stars.server.Server;
import easy.stars.server.data.FileUtils;
import easy.stars.server.data.Loader;
import easy.stars.server.log.LogBase;
import easy.stars.server.utils.Download;
import easy.stars.server.utils.Updater;
import easy.stars.server.utils.Zip;
import easy.stars.system.System;
import easy.stars.system.os.utils.OSUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.function.Predicate;

// TODO: Repair, Reinstall and QCProtocol whit support Process (post process, process and pre process), unite Error window

public class App extends Application {

    public static final System system = new System();

    private static Scene scene;
    private static Stage stage;
    public static final Config config = new Config();
    public static final Updater updater = new Updater();
    public static final Gson parser = new Gson();
    private static final Loader loader = new Loader();

    @Override
    public synchronized void start(Stage stage) throws IOException {
        AbstractFXController res = system.getMainController();
        scene = new Scene(res.loadFXML(), 600, 400);
        res.loadCSS(scene);
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("css/main.css")).toExternalForm());
        App.stage = stage;
        stage.setTitle("QCloud");
        InputStream iconStream = App.class.getResourceAsStream("img/icon.png");
        assert iconStream != null;
        stage.getIcons().add(new Image(iconStream));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void restart() {
        system.start();
        AbstractFXController res = system.getMainController();
        try {
            setRoot(res);
        } catch (IOException e) {
            e.printStackTrace();
            assert true;
        }
    }

    private static void update() {
        throw new MissingFormatArgumentException("Miss args update-skip");
        // open -a QCloud.app --args update-skip
    }

    public static void setRoot(AbstractFXController controller) throws IOException {
        scene.setRoot(controller.loadFXML());
        controller.prepare(scene);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void downloadResource() throws IOException {
        if (OSUtils.isWindows()) {
            Download download = new Download("nircmd.exe.zip", "res/download");
            download.download();
            try {
                Zip.extract(FileUtils.getResPath("nircmd.exe.zip").toFile(), FileUtils.getResPath().toFile());
            } catch (Exception ignored) {
            }
            try {
                Zip.extract(FileUtils.getResPath("QuasarSetup.exe.zip").toFile(), FileUtils.getResPath().toFile());
            } catch (Exception ignored) {
            }
        }
    }

    public static void startServer() {
        Server server = new Server(loader.loadData());
        Server.setInstance(server);
        updater.checkUpdate();
        server.start();
        server.getLogger().logIn(LogBase.SUCCESS_START_SERVER);
    }

    public static void launcher() {
        launch();
    }

    public static void main(String[] args) {
        Arrays.stream(args).anyMatch(Predicate.isEqual("update-skip"));
        if (true) {
            system.start();
        } else update();
    }
}

