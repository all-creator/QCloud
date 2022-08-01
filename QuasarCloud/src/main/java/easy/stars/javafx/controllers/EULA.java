package easy.stars.javafx.controllers;

import easy.stars.App;
import easy.stars.javafx.AbstractFXController;
import easy.stars.javafx.controllers.errors.Error;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.io.IOException;


public class EULA extends AbstractFXController {

    public EULA() {
        setScene("eula");
    }

    @FXML
    private CheckBox log;

    @FXML
    private CheckBox agree;

    @FXML
    private Label warning;

    @FXML
    private Button continue_b;

    @FXML
    public void buttonClicked() throws IOException {
        if (!agree.isSelected()) App.setRoot(new Error());
        App.config.addSetting("sendlog:" + log.isSelected());
        App.setRoot(new Register());
    }

    @FXML
    public void eulaNoAccept() {
        if (!agree.isSelected()) {
            warning.setText("Для продолжения установки необходимо принять пользовательское соглашение");
            continue_b.setDisable(true);
        } else {
            warning.setText("");
            continue_b.setDisable(false);
        }
    }
}
