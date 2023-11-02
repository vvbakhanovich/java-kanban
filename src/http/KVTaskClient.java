package http;

import exceptions.BadRequestException;
import exceptions.RegistrationException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Http клиент для обработки запросов на сохранение и получение данных с KVServer
 */

public class KVTaskClient {
    //токен, получаемый при регистрации на сервере
    private final String API_TOKEN;
    //адрес KVServer'а
    private final URI serverUri;
    private final HttpClient httpClient;

    /**
     * При создании объекта класса отправляется запрос на регистрацию в KVServer. В теле ответа содержится API_TOKEN,
     * который в дальнейшем используется для авторизации обращений.
     *
     * @param uri адрес KVServer'a
     */
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
            if (response.statusCode() == 200) {
                API_TOKEN = response.body();
                System.out.println("После запроса на регистрацию получен API_TOKEN: " + API_TOKEN);
            } else {
                throw new RegistrationException("Не был получен API_TOKEN. Код ответа сервера: "
                        + response.statusCode());
            }
        } catch (IOException | InterruptedException e1) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте URL-адрес и повторите запрос.");
            throw new BadRequestException("Во время выполнения запроса возникла ошибка. " +
                    "Проверьте URL-адрес и повторите запрос.");
        } catch (IllegalArgumentException e2) {
            System.out.println("Введеный адрес не соответствует формату URL.");
            throw new BadRequestException("Введеный адрес не соответствует формату URL.");
        }
    }

    /**
     * Метод отправляет запрос в KVServer для сохранения пары key:json в базе данных сервера.
     *
     * @param key  ключ, по которому данные будут храниться на сервере
     * @param json данные, которые необходимо сохранить
     */
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
            if (response.statusCode() != 200) {
                System.out.println("Код ответа после отправки элемента на сервер: " + response.statusCode());
                throw new BadRequestException("Код ответа после отправки элемента на сервер: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e1) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте URL-адрес и повторите запрос.");
            throw new BadRequestException("Во время выполнения запроса возникла ошибка. " +
                    "Проверьте URL-адрес и повторите запрос.");
        } catch (IllegalArgumentException e2) {
            System.out.println("Введеный адрес не соответствует формату URL.");
            throw new BadRequestException("Введеный адрес не соответствует формату URL.");
        }
    }

    /**
     * Метод для получения данных из сервера по ключу key.
     *
     * @param key ключ, по которому требуется получить данные.
     * @return данные, полученные по ключу, в строковом представлении
     */
    public String load(String key) {
        URI loadUri = URI.create(serverUri + "load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(loadUri)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response;
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            response = httpClient.send(request, handler);
            if (response.statusCode() != 200) {
                System.out.println("Код ответа после отправки элемента на сервер: " + response.statusCode());
                throw new BadRequestException("Код ответа после отправки элемента на сервер: " + response.statusCode());
            }
            System.out.println("\nПолучен ответ от сервера с кодом: " + response.statusCode());
            System.out.println("Тело ответа: " + response.body());
        } catch (IOException | InterruptedException e1) {
            System.out.println("Во время выполнения запроса возникла ошибка. Проверьте URL-адрес и повторите запрос.");
            throw new BadRequestException("Во время выполнения запроса возникла ошибка. " +
                    "Проверьте URL-адрес и повторите запрос.");
        } catch (IllegalArgumentException e2) {
            System.out.println("Введеный адрес не соответствует формату URL.");
            throw new BadRequestException("Введеный адрес не соответствует формату URL.");
        }
        return response.body();
    }
}
