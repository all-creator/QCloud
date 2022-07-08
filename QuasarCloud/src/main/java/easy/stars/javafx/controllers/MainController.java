package easy.stars.javafx.controllers;

import easy.stars.App;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.io.IOException;


public class MainController {

    public MainController() {
    }

    @FXML
    private CheckBox log;

    @FXML
    private CheckBox agree;

    @FXML
    public void buttonClicked() throws IOException {
        if (!agree.isSelected()) App.setRoot("error");
        App.config.addSetting("sendlog:" + log.isSelected());
        App.setRoot("path");
    }
}
