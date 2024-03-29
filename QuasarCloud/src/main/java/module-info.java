open module easy.stars {
    exports easy.stars;
    exports easy.stars.javafx;
    exports easy.stars.javafx.controllers;
    exports easy.stars.javafx.controllers.errors;
    exports easy.stars.server;
    exports easy.stars.server.log;
    exports easy.stars.server.object;
    exports easy.stars.server.protocol;
    exports easy.stars.server.utils;
    exports easy.stars.system;
    exports easy.stars.system.identifier;
    exports easy.stars.system.language;
    exports easy.stars.system.os;
    exports easy.stars.system.os.interfaces;
    exports easy.stars.system.os.object;
    exports easy.stars.system.os.utils;
    exports easy.stars.exceptions;

    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires com.github.oshi;
}