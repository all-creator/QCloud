package easy.stars;

import com.google.gson.Gson;
import easy.stars.server.Config;
import easy.stars.server.Server;
import easy.stars.server.data.FileUtils;
import easy.stars.server.data.Loader;
import easy.stars.server.log.LogBase;
import easy.stars.server.object.RegisterClient;
import easy.stars.server.utils.Download;
import easy.stars.server.utils.Updater;
import easy.stars.server.utils.Zip;
import easy.stars.system.identifier.ComputerIdentifier;
import easy.stars.system.utils.OSUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Objects;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    public static final Config config = new Config();
    public static final Updater updater = new Updater();
    public static final Gson parser = new Gson();
    public static final String MAIN_URL = "http://88.99.240.171:8081/";

    @Override
    public synchronized void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("scene"), 600, 400);
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("DarkTheme.css")).toExternalForm());
        App.stage = stage;
        stage.setTitle("Q CLOUD");
        InputStream iconStream = App.class.getResourceAsStream("icon.jpeg");
        assert iconStream != null;
        stage.getIcons().add(new Image(iconStream));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void generateData() {
        try {
            config.setClient(ComputerIdentifier.generateLicenseKey());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public static void downloadResource() throws IOException {
        if (OSUtils.isWindows()) {
            Download download = new Download("nircmd.exe.zip", "res/download");
            download.download();
            download = new Download("QuasarSetup.exe.zip", "res/download");
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

    public static void loadResource() throws IOException {
        if (OSUtils.isWindows()) Runtime.getRuntime().exec(new String[]{FileUtils.getResPath("QuasarSetup.exe").toAbsolutePath().toString()});
    }

    public static void startServer() {
        Server server = new Server(new Loader().loadData());
        Server.setInstance(server);
        updater.checkUpdate();
        server.start();
        server.getLogger().logIn(LogBase.SUCCESS_START_SERVER);
    }

    public static void register() {
        String gson = parser.toJson(new RegisterClient(config.getClient()));
        byte[] out = gson.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        try {
            java.net.URL url = new URL(MAIN_URL + "tg/register");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        if (Arrays.asList(args).contains("-install")) launch();
        else startServer();
    }
}

