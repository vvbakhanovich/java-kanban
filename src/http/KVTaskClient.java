package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String API_TOKEN;
    private final URI serverUri;
    private final HttpClient httpClient;

    public KVTaskClient(String uri) {
        serverUri = URI.create(uri);
        URI registerUri = URI.create(uri + "register/");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(registerUri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = httpClient.send(request, handler);
            API_TOKEN = response.body();
            System.out.println("После запроса на регистрацию получен API_TOKEN: " + API_TOKEN);
        } catch (IOException | InterruptedException e1) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте URL-адрес и повторите запрос.");
        } catch (IllegalArgumentException e2) {
            System.out.println("Введеный адрес не соответсвует формату URL.");
        }
    }

    //POST /save/<key>?API_TOKEN_
    public void put(String key, String json) {
        URI saveUri = URI.create(serverUri + "save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(saveUri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = httpClient.send(request, handler);
            System.out.println("Код ответа после отправки элемента на сервер: " + response.statusCode());
        } catch (IOException | InterruptedException e1) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте URL-адрес и повторите запрос.");
        } catch (IllegalArgumentException e2) {
            System.out.println("Введеный адрес не соответсвует формату URL.");
        }
    }

    //GET /load/<key>?API_TOKEN_
    public String load(String key) {
        URI loadUri = URI.create(serverUri + "load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(loadUri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = null;
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            response = httpClient.send(request, handler);
            System.out.println("\nПолучен ответ от сервера с кодом: " + response.statusCode());
            System.out.println("Тело ответа: " + response.body());
        } catch (IOException | InterruptedException e1) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте URL-адрес и повторите запрос.");
        } catch (IllegalArgumentException e2) {
            System.out.println("Введеный адрес не соответсвует формату URL.");
        }
        return response != null ? response.body() : null;
    }

    public static void main(String[] args) {
        KVTaskClient client = new KVTaskClient("http://localhost:8078/");
        client.put("test", "json");
    }

}
