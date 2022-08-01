package easy.stars.javafx.controllers.errors;

import easy.stars.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class NetworkError extends Error implements Initializable {

    public NetworkError() {
        setScene("connection_error");
    }

    @FXML
    public ImageView networkErrorImg = new ImageView();

    @FXML
    public void repeat() {
        App.restart();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputStream iconStream = App.class.getResourceAsStream("img/WiFi.png");
        assert iconStream != null;
        networkErrorImg.setImage(new Image(iconStream));
    }
}
