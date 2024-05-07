package ua.kivshar.yurii.demo5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHTML {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Заміна адреси сторінки на ту, яку ви хочете отримати
        String url = "https://download.platezhka.com.ua/Dispatcher/Archive/";

        extracted(url);
    }

    private static void extracted(String url) throws IOException, InterruptedException {
        // Створення екземпляра HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Створення HTTP-запиту
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Відправлення запиту та отримання відповіді
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Перевірка успішності запиту
        if (response.statusCode() == 200) {
            // Отримання HTML-коду з відповіді
            String html = response.body();
            System.out.println(html);
            Map<String, String> linkMap = new ParseHTML(html).getLinksList(html);
            for (Map.Entry<String, String> entry : linkMap.entrySet()) {
                if (!entry.getKey().equals("[В родительский каталог]")) {
                    String url1 = "https://download.platezhka.com.ua" + entry.getValue();
                    if (entry.getValue().endsWith("/")) {
                        System.out.println("Go to " + url1);
                        extracted(url1);
                    } else {
                        System.out.println("Download: " + url1);
                        downloadFile(url1);
                    }
                }
            }
        } else {
            System.out.println("Помилка при отриманні HTML: " + response.statusCode());
        }
    }

    private static void downloadFile(String url) {
        String directoryPath = "D:\\Delete"; // Шлях до локальної директорії

        // Визначення шляху до директорії з використанням регулярних виразів
        String regex = "https://download\\.platezhka\\.com\\.ua/(.*?)/[^/]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String pathFromUrl = matcher.group(1);

            // Створення повної структури директорій
            String fullPath = directoryPath + "/" + pathFromUrl;

            // Перевірка наявності директорії і створення, якщо вона відсутня
            Path directory = Paths.get(fullPath);
            try {
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                    System.out.println("Створено директорію: " + fullPath);
                } else {
                    System.out.println("Директорія вже існує: " + fullPath);
                }

                // Завантаження файлу
                String fileName = url.substring(url.lastIndexOf('/') + 1);
                try (InputStream in = new URL(url).openStream();
                     FileOutputStream fos = new FileOutputStream(fullPath + "/" + fileName)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Файл завантажено до: " + fullPath + "/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Не вдалося отримати шлях до директорії з URL.");
        }

    }
}