package easy.stars.javafx.controllers;

import easy.stars.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FinishController {

    public FinishController() {
    }

    @FXML
    private Label online;

    @FXML
    private Label uuid;

    @FXML
    public void start() {
        App.getStage().close();
        App.startServer();
    }

    @FXML
    public void getStatus() {
        try {
            URL url = new URL("http://88.99.240.171:8081/status");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.connect();
            if (http.getResponseCode() == 200) online.setText("Статус сервера: Онлайн");
        } catch (IOException ignored) {
            online.setText("Статус сервера: Офлайн");
        }
    }

    @FXML
    public void register() {
        App.register();
        uuid.setText(App.config.getClient().getUuid().toString());
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString("/reg " + App.config.getClient().getUuid().toString());
        clipboard.setContent(content);
    }
}
