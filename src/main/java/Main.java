import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String url = "https://www.tekstowo.pl/piosenka,taco_hemingway,mieso.html";

        try {

            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("song-text");

            System.out.println(elements);


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
