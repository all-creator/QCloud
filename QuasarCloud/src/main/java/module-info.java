module easy.stars {
    exports easy.stars;
    exports easy.stars.controllers;
    exports easy.stars.identifier;
    exports easy.stars.server;
    exports easy.stars.server.data;
    exports easy.stars.server.log;
    exports easy.stars.server.object;
    exports easy.stars.server.utils;

    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires com.github.oshi;

    opens easy.stars.controllers to javafx.fxml;
    opens easy.stars.identifier to com.google.gson;
    opens easy.stars.server to com.google.gson;
    opens easy.stars.server.object to com.google.gson;
    opens easy.stars.server.log to com.google.gson;
    opens easy.stars.server.utils to com.google.gson;
    opens easy.stars.server.data to com.google.gson;
}