package easy.stars.javafx.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Error extends AbstractFXController implements Initializable {

    static String errorMessage;

    public Error(String error) {
        setScene("error");
        setErrorMessage(error);
    }

    public Error() {
        setScene("error");
    }

    public static void setErrorMessage(String errorMessage) {
        Error.errorMessage = errorMessage;
    }

    @FXML
    public void buttonClicked() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
