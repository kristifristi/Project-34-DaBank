package server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetInfo {
    private static final String USER_AGENT = "BANK/DA";
    private static int status;
    public static String post(String url, String json) throws IOException{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        con.setRequestProperty("NOOB-TOKEN", "KAAAS");
        con.setDoOutput(true);
        con.setUseCaches(false);

        OutputStream out = con.getOutputStream();
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.close();

        status = con.getResponseCode();
        System.out.println("GET Response code: " + status);

        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            return response.toString();
        }
        else {
            System.out.println("No Cheese");
        }
        return "";
    }
    public static String get(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("NOOB-TOKEN", "KAAAS");

        status = con.getResponseCode();
        System.out.println("Get response code: " + status);

        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            return response.toString();
        }
        else {
            System.out.println("No Cheese");
        }
        return "";
    }
    public static int getStatus() {
        return status;
    }
}
