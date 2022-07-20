package easy.stars.javafx.controllers;

import easy.stars.App;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class ConnectionError extends AbstractFXController {

    public ConnectionError() {
        setScene("connection_error");
        prepare();
    }

    @FXML
    public ImageView networkErrorImg = new ImageView();

    @Override
    public void prepare(Object... args) {
        InputStream iconStream = App.class.getResourceAsStream("img/WiFi.png");
        assert iconStream != null;
        networkErrorImg.setImage(new Image(iconStream));
    }

    @FXML
    public void repeat() {
        prepare();
    }
}
