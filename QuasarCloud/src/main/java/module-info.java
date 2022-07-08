open module easy.stars {
    exports easy.stars;
    exports easy.stars.javafx.controllers;
    exports easy.stars.server;
    exports easy.stars.server.data;
    exports easy.stars.server.log;
    exports easy.stars.server.object;
    exports easy.stars.server.utils;
    exports easy.stars.system;
    exports easy.stars.system.identifier;
    exports easy.stars.system.interfaces;
    exports easy.stars.system.utils;

    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires com.github.oshi;
}