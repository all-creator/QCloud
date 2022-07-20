package easy.stars.javafx.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class ErrorController extends AbstractFXController {

    String errorMessage;

    public ErrorController(String error) {
        setScene("error");
        errorMessage = error;
    }

    @FXML
    public void buttonClicked() {
        Platform.exit();
    }

    @Override
    public void prepare(Object[] args) {}
}
