//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)

package proxy.kindle.amazon;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class EbookAuthorization {
    HttpClient client = HttpClient.newHttpClient();

    public EbookAuthorization() {
    }

    public void verify(String url) {
        try {
            System.out.println("Verifying URL: " + url);
            this.Request(HttpRequest.newBuilder(new URI(url)).headers(new String[]{"Cache-Control", "no-cache", "User-Agent", "PostmanRuntime/7.42.0", "Accept", "*/*", "Accept-Encoding", "gzip, deflate, br"}).GET().build());
        } catch (URISyntaxException var3) {
            URISyntaxException err = var3;
            throw new RuntimeException(err);
        }
    }

    private void Request(HttpRequest request) {
        request.headers();

        try {
            System.out.println(request.headers());
            HttpResponse<String> res = this.client.send(request, BodyHandlers.ofString());
            System.out.println(res.uri());
            System.out.println(res.headers());
            System.out.println((String) res.body());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UrlConnection(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept", "text/html");
            connection.connect();


            connection.setInstanceFollowRedirects(true);


            System.out.println("depois da conexão: " + connection.getURL());

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Response: " + response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UnitConnection(String url) {


        Unirest.setTimeouts(0, 0);

        GetRequest response = Unirest.get(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Cookie", "i18n-prefs=USD; session-id=142-1064730-3604359; session-id-time=2082787201l; sp-cdn=\"L5Z9:BR\"")
                ;

        System.out.println("requisição completa!");
        try {

            System.out.println(response.asString().getBody());
            System.out.println(response.getUrl());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
    public void OkHttpConnection (String url) {
        try {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "session-id=146-9914890-8162433; session-id-time=2082787201l; i18n-prefs=USD; sp-cdn=\"L5Z9:BR\"; ubid-main=132-7710125-2208100; session-token=unSBErg0zPM6k3XMF7KquIMcU9j2A0n42Nu6/I5PQC/RwOzdsiHrA0mXo7ZyOLyWnqw/1ewldWRQLqqXWIDAwpD65po3iN0CtAS6GCG82dibYxyZ2VCoh0dnySVVPQbdAM9NS2AiZ3kW/JHis00PzrXZ+5roL9e/JKVwx7JGabcrlUQ6v8Dfd0DfWN+xb7DXYG+jV/5LKS7GVu5CbaUyMMnrr4ZPPza7B8h0VgakL4YaS9KORThMVoO2A79M+ot8AREzoETYJfUYq0I776Vmp2Ssarews6rwiE5rUPPlVsvWtugwuWqE1fTF05wDtdmkr5mD4Ccx2ecSeFt7bL1cHIjDV3CfsAxK")
                .addHeader("device-memory", "8")
                .addHeader("dnt", "1")
                .addHeader("downlink", "7.45")
                .addHeader("dpr", "2")
                .addHeader("ect", "4g")
                .addHeader("priority", "u=0, i")
                .addHeader("rtt", "50")
                .addHeader("sec-ch-device-memory", "8")
                .addHeader("sec-ch-dpr", "2")
                .addHeader("sec-ch-viewport-width", "339")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1")
                .addHeader("viewport-width", "339")
                .build();
        Response response = client.newCall(request).execute();
            System.out.println(response.headers());
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
