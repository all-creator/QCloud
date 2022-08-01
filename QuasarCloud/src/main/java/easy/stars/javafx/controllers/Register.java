package easy.stars.javafx.controllers;

import easy.stars.App;
import easy.stars.javafx.AbstractFXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class Register extends AbstractFXController implements Initializable {
    public Register() {
        setScene("register");
    }

    @FXML
    private Label online;

    @FXML
    public Label prompt;

    @FXML
    private TextField uuid;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnFinish;

    @FXML
    private Button btnReconnect;

    @FXML
    public void start() {
        App.getStage().close();
        App.startServer();
    }

    @FXML
    public void sendRegister() {
        App.register();
        uuid.setDisable(false);
        uuid.setText(App.config.getClient().getUuid_global().toString());
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString("/reg " + App.config.getClient().getUuid_global().toString());
        clipboard.setContent(content);
        btnFinish.setDisable(false);
        prompt.setText("Команда скопирована в буфер обмена");
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnFinish.setDisable(true);
        connect();
    }

    @FXML
    public void connect() {
        btnReconnect.setDisable(true);
        try {
            URL urlStatus = new URL("http://88.99.240.171:8081/status");
            HttpURLConnection http = (HttpURLConnection) urlStatus.openConnection();
            http.connect();
            if (http.getResponseCode() == 200) online.setText("Статус сервера: Онлайн");
            btnRegister.setDisable(false);
            prompt.setText("");
        } catch (IOException ignored) {
            online.setText("Статус сервера: Офлайн");
            btnRegister.setDisable(true);
            btnReconnect.setDisable(false);
            prompt.setText("Статус сервера Офлайн - регистрация не доступна");
        }
    }
}
