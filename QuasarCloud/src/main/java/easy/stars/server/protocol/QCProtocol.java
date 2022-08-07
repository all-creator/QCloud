package easy.stars.server.protocol;

import easy.stars.exceptions.ServerNotAllowedException;
import easy.stars.exceptions.URLNotFound;
import easy.stars.server.Server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QCProtocol extends Process {

    HttpURLConnection connection;

    final URL url;

    ConnectionType connectionType;

    protected QCProtocol(Runnable process, String url, ConnectionType connectionType) {
        super(process);
        this.connectionType = connectionType;
        try {
            this.url = new URL(Server.MAIN_URL + url);
        } catch (MalformedURLException e) {
            throw new URLNotFound(e, Server.MAIN_URL + url);
        }
        try {
            connection = (HttpURLConnection) this.url.openConnection();
        } catch (IOException e) {
            throw new ServerNotAllowedException(e);
        }
    }

    protected QCProtocol(Runnable process, URL url, ConnectionType connectionType) {
        super(process);
        this.url = url;
        this.connectionType = connectionType;
    }

    @Override
    protected void preProcess() {
        try {
            connection.connect();
        } catch (IOException e) {
            throw new ServerNotAllowedException(e);
        }
    }

    @Override
    protected void postProcess() {
        connection.disconnect();
    }

    public enum ConnectionType {
        META("meta"),
        REGISTER("register"),
        UPDATE("update"),
        PING("ping"),
        ;

        final String type;

        ConnectionType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
