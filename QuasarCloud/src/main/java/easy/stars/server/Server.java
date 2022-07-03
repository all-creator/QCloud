package easy.stars.server;

import easy.stars.server.log.LogBase;
import easy.stars.server.log.Logging;
import easy.stars.server.log.LoggingNotSupportFormat;
import easy.stars.server.object.Hash;
import easy.stars.server.object.LogMessage;
import easy.stars.server.object.TelegramMessage;
import easy.stars.server.object.Update;
import easy.stars.server.utils.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import static easy.stars.App.MAIN_URL;
import static easy.stars.App.parser;

public final class Server {

    static Server instance;
    Config config;
    Logging logger;
    public boolean status = false;

    public Server(Config config) {
        this.config = config;
        this.logger = Logging.createSystemLog(config);
    }

    public void send(String message) throws IOException {
        String gson = parser.toJson(new TelegramMessage(message, config.getClient().getHash()));
        byte[] out = gson.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        URL url = new URL(MAIN_URL + "tg/send");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
    }

    public void start() {
        Thread run = new Thread (() -> {
            while (true) {
                try {
                    String gson = parser.toJson(new Hash(config.getClient().getHash()));
                    byte[] out = gson.getBytes(StandardCharsets.UTF_8);
                    int length = out.length;
                    URL url = new URL(MAIN_URL + "tg/update");
                    URLConnection con = url.openConnection();
                    HttpURLConnection http = (HttpURLConnection) con;
                    http.setRequestMethod("POST");
                    http.setDoOutput(true);
                    http.setFixedLengthStreamingMode(length);
                    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    http.connect();
                    try (OutputStream os = http.getOutputStream()) {
                        os.write(out);
                    }
                    BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    gson = response.toString();
                    Update update = parser.fromJson(gson, Update.class);
                    if (update != null) Worker.work(update);
                    http.disconnect();
                    Thread.sleep(300);
                } catch (Exception e) {
                    System.out.println("Server Error");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        });
        run.start();
    }

    public <T> void log(T logging) throws LoggingNotSupportFormat {
        if (logger == null) return;
        if (logging instanceof LogBase) logger.logIn((LogBase) logging);
        else if (logging instanceof LogMessage) logger.logIn((LogMessage) logging);
        else if (logging instanceof String) logger.logIn((String) logging);
        else throw new LoggingNotSupportFormat("Не поддерживаемый формат логирования: "+logging.getClass().getName());
    }

    public Config getConfig() {
        return config;
    }

    public Logging getLogger() {
        return logger;
    }

    public static Server getInstance() {
        return instance;
    }

    public static void setInstance(Server instance) {
        Server.instance = instance;
    }
}
