package easy.stars.javafx.controllers.errors;

import easy.stars.App;
import easy.stars.javafx.AbstractFXController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Error extends AbstractFXController implements Initializable {

    static String errorMessage;
    static String imgPath;
    static Runnable buttonDo;
    static String buttonMessage;

    @FXML
    public ImageView img;
    @FXML
    public Label label;
    @FXML
    public Button button;

    public Error(String errorMessage) {
        this(errorMessage, null);
    }

    public Error(String errorMessage, String imgPath) {
        this(errorMessage, imgPath, null, null);
    }

    public Error(String errorMessage, String imgPath, Runnable buttonDo, String buttonMessage) {
        setScene("error");
        setErrorMessage(errorMessage);
        setImgPath(imgPath);
        setButtonDo(buttonDo);
        setButtonMessage(buttonMessage);
    }

    public static void setErrorMessage(String errorMessage) {
        Error.errorMessage = errorMessage;
    }

    public static void setImgPath(String imgPath) {
        Error.imgPath = imgPath;
    }

    public static void setButtonDo(Runnable buttonDo) {
        Error.buttonDo = buttonDo;
    }

    public static void setButtonMessage(String buttonMessage) {
        Error.buttonMessage = buttonMessage;
    }

    public Error() {
        setScene("error");
        setButtonMessage("Выход");
    }

    @FXML
    public void buttonClicked() {
        if (buttonDo != null) buttonDo.run();
        else Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (buttonMessage != null) button.setText(buttonMessage);
        if (errorMessage != null) label.setText(errorMessage);
        if (imgPath != null) {
            InputStream iconStream = App.class.getResourceAsStream(imgPath);
            assert iconStream != null;
            img.setImage(new Image(iconStream));
        }
    }
}
