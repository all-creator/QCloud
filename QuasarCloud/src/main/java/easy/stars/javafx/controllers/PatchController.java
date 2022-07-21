package easy.stars.javafx.controllers;

import easy.stars.App;
import easy.stars.server.data.FileUtils;
import easy.stars.server.data.Saver;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
@Deprecated(since = "4.0.15")
public class PatchController extends AbstractFXController {

    public PatchController() {
        setScene("path");
    }

    @FXML
    private TextField hash;

    final DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    public synchronized void buttonClicked() throws IOException {
        if (App.config.getMainDirectory() == null) App.setRoot(new Error());
        App.generateData();
        FileUtils.createPath();
        new Saver(App.config).saveData();
        App.updater.loadResource();
        App.downloadResource();
        App.loadResource();
        App.setRoot(new Register());
    }

    @FXML
    public synchronized void onClickHash() {
        configuringDirectoryChooser(directoryChooser);
        App.config.setMainDirectory(directoryChooser.showDialog(App.getStage()).getAbsolutePath());
        hash.setText(App.config.getMainDirectory());
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Укажите путь установки");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}
