package parzulpan.com.java11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : Java11 全新的 HTTP 客户端 API
 */

public class HttpTest {
    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:8080/test/")).build();
            HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString();

            // 同步
            HttpResponse<String> response = client.send(request, responseBodyHandler);
            String body = response.body();
            System.out.println(body);

            // 异步
            CompletableFuture<HttpResponse<String>> sendAsync = client.sendAsync(request, responseBodyHandler);
            sendAsync.thenApply(HttpResponse::body).thenAccept(System.out::println);
            HttpResponse<String> response1 = sendAsync.get();
            String body1 = response1.body();
            System.out.println(body1);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
