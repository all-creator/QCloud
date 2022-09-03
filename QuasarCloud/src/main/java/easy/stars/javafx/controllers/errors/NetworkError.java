package easy.stars.javafx.controllers.errors;

import easy.stars.App;
import javafx.fxml.Initializable;

public class NetworkError extends Error implements Initializable {

    public NetworkError() {
        super("Похоже вы не подключены к сети", "img/WiFi.png", App::restart, "Перезайти");
    }
}
