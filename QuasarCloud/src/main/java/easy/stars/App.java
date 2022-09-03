package easy.stars;

import com.google.gson.Gson;
import easy.stars.javafx.AbstractFXController;
import easy.stars.javafx.controllers.errors.DuplicateError;
import easy.stars.server.Server;
import easy.stars.server.log.LogBase;
import easy.stars.system.System;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.function.Predicate;

// TODO: Repair, Reinstall, unite Error window

public class App extends Application {

    public static final System system = new System();

    private static Scene scene;
    private static Stage stage;
    public static final Gson parser = new Gson();

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

    public static void startServer() {
        Server server = new Server();
        Server.setInstance(server);
        server.start();
        server.getLogger().logIn(LogBase.SUCCESS_START_SERVER);
    }

    public static void launcher() {
        launch();
    }

    public static void main(String[] args) {
        if (system.lock()) {
            system.setMainController(new DuplicateError());
            launch();
        } else {
            Runtime.getRuntime().addShutdownHook(new Thread(system::deleteLockFile));
            system.creteLockFile();
            if (Arrays.stream(args).noneMatch(Predicate.isEqual("update-skip"))) system.start();
            else update();
        }
    }
}

