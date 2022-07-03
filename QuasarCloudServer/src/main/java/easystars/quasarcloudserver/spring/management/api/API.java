package easystars.quasarcloudserver.spring.management.api;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    private static final String TOKEN = "token=D!3%26%23!@aidaDHAI(I*12331231AKAJJjjjho1233h12313^%%23%@4112dhas91^^^^31";
    private static final String URL = "https://api.easy-stars.ru";
    private static final String URL_USER_GET = "/api/query/user/get";
    private static final String URL_STARS = "/api/query/stars";

    private API() {
    }

    public static String getQuery(String query, Actions action){
        return getQuery(action.getAction() + "&" + query);
    }

    @SneakyThrows
    public static String getQuery(String query){
        var url = new URL(URL + URL_USER_GET + "?" + TOKEN + "&" + query);
        var httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        var in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        var buffer = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            buffer.append(inputLine);
        }
        return buffer.toString();
    }

    public static boolean getStar(String by, String text, String star){
        return getQuery("&by=" +by + "&by_text=" + text + "&product_name=" + star).contains("\"activate\": true");
    }

    @SneakyThrows
    public static boolean getLicense(String by, String text){
        Actions action = Actions.GET;
        URL url = new URL(URL + URL_USER_GET + "?" + TOKEN + "&" + action.getAction() + "&by=" + by + "&by_text=" + text);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            String[] inputLineArray = inputLine.replace("{", "").replace("}", "").split(",");
            for (String s : inputLineArray) {
                if (s.startsWith("\"status\":")) {
                    System.out.println(s.split(":")[1].equals(" \"successful\""));
                    return s.split(":")[1].equals(" \"successful\"");
                }
            }
        }
        return false;
    }

    public static boolean getLicenseByUsername(String text){
        return getLicense("username", text);
    }

    public static boolean getStarByUsername(String text, String star){
        return getStar("username", text, star);
    }
}
