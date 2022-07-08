package easy.stars.server.utils;

import com.google.gson.Gson;
import easy.stars.server.data.FileUtils;
import easy.stars.server.object.Message;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Download {
    protected static final String URL = "http://88.99.240.171:8081/";
    protected static final Gson parser = new Gson();
    protected String filename;
    protected String urlPath;
    protected String[] mmTypes = {"Content-Type", "application/json"};

    public Download(String filename, String urlPath, String... mmTypes) {
        this.filename = filename;
        this.urlPath = urlPath;
        if (mmTypes.length > 1) this.mmTypes = mmTypes;
    }

    public void download() throws IOException {
        File file = FileUtils.getResPath(filename).toFile();
        if (!file.exists() && file.createNewFile()) System.out.println("Загрузка ресурса - " + filename);

        String jsonFileName = parser.toJson(new Message(filename));

        URLConnection conection = new URL(URL + urlPath).openConnection();
        conection.setDoOutput(true);
        conection.setRequestProperty(mmTypes[0], mmTypes[1]);
        conection.connect();

        try (OutputStream outputStream = new BufferedOutputStream(conection.getOutputStream())) {
            outputStream.write(jsonFileName.getBytes());
            }
        try (InputStream in = new BufferedInputStream(conection.getInputStream())) {
            try(FileOutputStream out = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int count;
                while((count = in.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                System.out.println("Загрузка завешена - " + filename);
            }
        }
    }
}
