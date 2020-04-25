import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // getSongText("Taco hemingway", "nastepna stacja");

        //getWordsOccurances(getSongText("Taco hemingway", "nastepna stacja"));
        //getSongsList("Taco Hemingway");

        // List<String> list = new ArrayList();

        // list = getSongLinks("Taco Hemingway");

        getSongLinks("Taco Hemingway");

        getSongText("Taco Hemingway", getSongLinks("Taco Hemingway"));


    }


    public static String[] getSongText(String artist, List<String> URLlist) {


        artist = artist.replace(" ", "_");
        //title = title.replace(" ", "_");
        //String url = "https://www.tekstowo.pl/piosenka," + artist + "," + title + ".html";
        //  String url;


        List<String> list = Arrays.asList();

        Set<String> uniqueWords = new HashSet<String>(list);

        for (String url : URLlist) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.getElementsByClass("song-text");
                String[] textArray = elements.text().toLowerCase().replaceAll("\\p{Punct}", "").split(" ");
                //return textArray;


                getWordsOccurances(textArray);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    static void getWordsOccurances(String[] words) {

        List<String> list = Arrays.asList(words);

        Set<String> uniqueWords = new HashSet<String>(list);
        Map<String, Integer> freqWords = new HashMap<>();



        for (String word : uniqueWords) {
            System.out.println(word + ": " + Collections.frequency(list, word));
            freqWords.put(word,Collections.frequency(list, word));
        }




    }


    static List<String> getSongLinks(String artist) {


        String artistDash = artist.replace(" ", "_");
        String url = "https://www.tekstowo.pl/piosenki_artysty," + artistDash + ",popularne,malejaco,strona,1.html";

        List urlList = new ArrayList();

        try {
            Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
            Elements links = doc.getElementsByClass("ranking-lista").select("a[href]");
            for (Element link : links) {
                if (!link.attr("abs:href").isEmpty()) {
                    urlList.add(link.attr("abs:href"));
                } else {

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlList;
    }
}
