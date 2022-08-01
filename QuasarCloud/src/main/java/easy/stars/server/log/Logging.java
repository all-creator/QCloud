package easy.stars.server.log;

import com.google.gson.Gson;
import easy.stars.server.Config;
import easy.stars.server.object.LogMessage;
import easy.stars.server.utils.Updater;
import easy.stars.system.identifier.LicenseKey;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Logging {

    private static final  String LOG_URL = "http://88.99.240.171:8081/log";
    protected static final Gson parser = new Gson();
    private final boolean sendLog;
    private final LicenseKey user;

    public Logging(boolean sendLog, LicenseKey user) {
        this.sendLog = sendLog;
        this.user = user;
    }

    public static Logging createSystemLog(Config config) {
        if (config.getSettings().contains("sendlog:true")) {
            return new Logging(true, config.getClient());
        }
        return new Logging(false, config.getClient());
    }

    public void logIn(LogMessage obj){
        if (sendLog) log(parser.toJson(obj));
    }

    public void logIn(LogBase obj){
        if (sendLog) log(parser.toJson(new LogMessage(Updater.VERSION, user.getHash(), obj.getMessage())));
    }

    public void logIn(String obj){
        if (sendLog) log(parser.toJson(new LogMessage(Updater.VERSION, user.getHash(), obj)));
    }

    private void log(String str){
        byte[] out = str.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        try {
            URL log = new URL(LOG_URL);
            URLConnection con = log.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
