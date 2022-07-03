package easy.stars.server.utils;

import easy.stars.server.data.FileUtils;
import easy.stars.server.object.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Updater extends Download {

    public static final String VERSION = "1.0.4-beta";

    public Updater() {
        super("QuasarCloud.zip", "update/download", "Content-Type", "application/zip");
    }

    public void checkUpdate() {
        String gson = parser.toJson(new Message(VERSION));
        byte[] out = gson.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        try {
            URL url = new URL(URL +"update/check");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (response.toString().equals("find")) {
                download();
                Zip.extract(FileUtils.getResPath(this.filename).toFile(), FileUtils.getMainPath().toFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadResource() throws IOException {
        download();
        Zip.extract(FileUtils.getResPath(filename).toFile(), FileUtils.getMainPath().toFile());
    }
}
