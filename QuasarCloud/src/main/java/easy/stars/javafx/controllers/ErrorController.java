package easy.stars.javafx.controllers;

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
