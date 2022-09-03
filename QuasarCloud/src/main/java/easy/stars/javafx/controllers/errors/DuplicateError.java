package easy.stars.javafx.controllers.errors;

import javafx.fxml.Initializable;

public class DuplicateError extends Error implements Initializable {

    public DuplicateError() {
        super("Программа уже запущена", "img/duplicate.png");
    }
}
