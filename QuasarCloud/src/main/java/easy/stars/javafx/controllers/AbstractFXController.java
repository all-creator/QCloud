package easy.stars.javafx.controllers;

import easy.stars.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractFXController implements FXController {
    String scene = null;
    Parent parent;

    public Parent loadFXML() throws IOException {
        parent = new FXMLLoader(App.class.getResource(scene + ".fxml")).load();
        return parent;
    }

    public void loadCSS(Scene show) {
        URL url = App.class.getResource("css/" + scene + ".css");
        if (url != null) show.getStylesheets().addAll(url.toExternalForm());
    }

    public void onLoadCSS(Scene show) {
        URL url = App.class.getResource("css/" + scene + ".css");
        if (url != null) show.getStylesheets().removeAll(url.toExternalForm());
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    @Override
    public void prepare(Object... args) {
        if (args.length > 0) loadCSS((Scene) args[0]);
    }

    @Override
    public void stopped(Object[] args) {
        if (args.length > 0) onLoadCSS((Scene) args[0]);
    }
}
