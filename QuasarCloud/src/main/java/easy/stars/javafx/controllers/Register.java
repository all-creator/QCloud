package easy.stars.javafx.controllers;

import easy.stars.App;
import easy.stars.javafx.AbstractFXController;
import easy.stars.server.Server;
import easy.stars.server.protocol.QCProtocol;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

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

    private QCProtocol protocol;

    @FXML
    public TextField uuid;

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
        uuid.setDisable(false);
        uuid.setText(new String(App.system.getLicenseKey().getUuid()));
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString("/reg " + new String(App.system.getLicenseKey().getUuid()));
        clipboard.setContent(content);
        btnFinish.setDisable(false);
        prompt.setText("Команда скопирована в буфер обмена");
        Server.register();
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
        protocol = new QCProtocol(this::run, QCProtocol.ConnectionType.PING, true);
    }

    public void run() {
        if (protocol.getResponseCode() == 200){
            online.setText("Статус сервера: Онлайн");
            btnRegister.setDisable(false);
            prompt.setText("");
        } else {
            online.setText("Статус сервера: Офлайн");
            btnRegister.setDisable(true);
            btnReconnect.setDisable(false);
            prompt.setText("Статус сервера Офлайн - регистрация не доступна");
        }
    }
}
