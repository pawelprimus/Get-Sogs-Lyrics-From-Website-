import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Artist {

    private static String name;
  //  private static Map<String, Integer> wordOccurancy = new HashMap<>();
    ArrayList<String> wordOccurancy = new ArrayList<String>();



    public Artist(String name) {
        this.name = name.replace(" ", "_");
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getWordOccurancy() {
        return wordOccurancy;
    }

    public Map<String, Integer> getWordsAscending() {

        Map<String, Integer> words = getWordsOccurances(getWordOccurancy());

        Map<String, Integer> sortedMap = words.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }


    public ArrayList<String> getSongText() {
        //List<String> list = Arrays.asList();
        ArrayList<String> list = new ArrayList<String>();

        List<String> URLlist = getSongLinks();

        for (String url : URLlist) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.getElementsByClass("song-text");
                //String[] textArray = elements.text().toLowerCase().replaceAll("\\p{Punct}", "").split(" ");
                String[] textArray = elements.text().toLowerCase().replaceAll("\\p{Punct}", "").split(" ");
                list.addAll(Arrays.asList(textArray));

               //System.out.println(list.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        wordOccurancy = list;

        //System.out.println(list.toString());
        return list;
    }

    public List<String> getSongLinks() {
        String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",popularne,malejaco,strona,1.html";

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

    public Map<String, Integer> getWordsOccurances(ArrayList<String> words) {

        ArrayList<String> list = words;



        Set<String> uniqueWords = new HashSet<String>(list);
        Map<String, Integer> frequencyWords = new HashMap<>();

        for (String word : uniqueWords) {
            //System.out.println(word + ": " + Collections.frequency(list, word));
            frequencyWords.put(word,Collections.frequency(list, word));
        }
        return frequencyWords;

    }

}
