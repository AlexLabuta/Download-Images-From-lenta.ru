import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> urlImages = getUrlImages("https://lenta.ru/");
        downloadImages(urlImages);
    }

    private static List getUrlImages(String url) {
        List<String> urlImages = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements images = doc.select("img[src$=.jpg]");

            for (Element image : images) {
                urlImages.add(image.attr("src"));
            }
        } catch (IOException ex) {
            System.out.println("Сайт не найден, проверьте подключение к интернету.");
        }
        return urlImages;
    }

    private static void downloadImages(List<String> urlImages) {
        for (String url : urlImages) {
            try (InputStream in = new URL(url).openStream()) {
                File file = new File(url);
                Files.copy(in, Paths.get("images/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Загрузка " + file.getName() + " завершена");
            } catch (IOException ex) {
                System.out.println("Не удалось загрузить картинки");
                ex.printStackTrace();
            }
        }
        System.out.println("Все картинки загружены");
    }
}







