open module easy.stars {
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
}