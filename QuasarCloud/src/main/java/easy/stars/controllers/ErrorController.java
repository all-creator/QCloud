package easy.stars.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class ErrorController {

    public ErrorController() {
    }

    @FXML
    public void buttonClicked() {
        Platform.exit();
    }
}
