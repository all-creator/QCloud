package easy.stars.javafx.controllers;

import easy.stars.App;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.io.IOException;


public class MainController extends AbstractFXController {

    @FXML
    private CheckBox log;

    @FXML
    private CheckBox agree;

    @FXML
    public void buttonClicked() throws IOException {
        if (!agree.isSelected()) App.setRoot(new ErrorController("Вам необходимо согласиться с условиями обработки данных"));
        App.config.addSetting("sendlog:" + log.isSelected());
        App.setRoot(new PatchController());
    }

    @Override
    public void prepare(Object[] args) {

    }
}
