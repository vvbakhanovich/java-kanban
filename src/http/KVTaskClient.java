package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String API_TOKEN;
    private URI serverUri;
    private final HttpClient httpClient;

    public KVTaskClient(String uri) throws IOException, InterruptedException {
        serverUri = URI.create(uri);
        URI registerUri = URI.create(uri + "register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(registerUri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        httpClient = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = httpClient.send(request, handler);
        API_TOKEN = response.body();
        System.out.println("После запроса на регистрацию получен API_TOKEN: " + API_TOKEN);
    }

    //POST /save/<key>?API_TOKEN_
    public void put(String key, String json) throws IOException, InterruptedException {
        URI saveUri = URI.create(serverUri + "save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(saveUri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = httpClient.send(request, handler);
        System.out.println("Код ответа после отправки элемента на сервер: " + response.statusCode());
    }

    //GET /load/<key>?API_TOKEN_
    public String load(String key) throws IOException, InterruptedException {
        URI loadUri = URI.create(serverUri + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(loadUri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = httpClient.send(request, handler);
        if (response.statusCode() == 200) {
            return response.body();
        } else {

        }

        return "";
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        KVTaskClient client = new KVTaskClient("http://localhost:8078/");
        client.put("test", "json");
    }

}
