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
    ArrayList<String> allWords = new ArrayList<String>();


    public Artist(String name) {
        this.name = name.replace(" ", "_");
        makeWordsList();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAllWords() {
        return allWords;
    }

    public Map<String, Integer> getWordsAscending() {

        Map<String, Integer> words = getMapWordsOccurances();

        Map<String, Integer> sortedWordsMap = words.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedWordsMap;
    }


    public void makeWordsList() {
        //List<String> list = Arrays.asList();
        ArrayList<String> list = new ArrayList<String>();

        List<String> URLlist = getSongLinks();

        for (String url : URLlist) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.getElementsByClass("song-text");
                String[] textArray = elements.text().toLowerCase().replaceAll("\\p{Punct}", "").split(" ");
                list.addAll(Arrays.asList(textArray));

                //System.out.println(list.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        allWords = list;
    }

    public List<String> getSongLinks() {

        String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",popularne,malejaco,strona,1.html";

        List songsUrlList = new ArrayList();

        try {
            Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
            Elements links = doc.getElementsByClass("ranking-lista").select("a[href]");
            for (Element link : links) {
                if (!link.attr("abs:href").isEmpty()) {
                    songsUrlList.add(link.attr("abs:href"));
                } else {

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return songsUrlList;
    }

    public Map<String, Integer> getMapWordsOccurances() {


        ArrayList<String> allWordsList = getAllWords();



        Set<String> uniqueWords = new HashSet<String>(allWordsList);
        Map<String, Integer> frequencyWordsMap = new HashMap<>();

        for (String word : uniqueWords) {
            //System.out.println(word + ": " + Collections.frequency(list, word));
            frequencyWordsMap.put(word, Collections.frequency(allWordsList, word));
        }

        return frequencyWordsMap;
    }

}
