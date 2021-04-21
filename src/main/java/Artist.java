import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Artist {

    private String name;
    ArrayList<String> allWords = new ArrayList<String>();


    public Artist(String name) {
        this.name = name.replace(" ", "_");
        //makeWordsList();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAllWords() {
        return allWords;
    }

    public int getSumOfAllWords() {
        return getMapWordsOccurances().values().stream().mapToInt(i -> i).sum();
    }

    private Elements getElementsFromUrlClass(String URL, String className) {
        Elements elements = new Elements();
        try {
            Document document = Jsoup.connect(URL).ignoreHttpErrors(true).get();
            elements = document.getElementsByClass(className);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }


    public Map<String, Integer> getWordsAscending() {

        Map<String, Integer> words = getMapWordsOccurances();

        Map<String, Integer> sortedWordsMap = words.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedWordsMap;
    }

    public Map<String, Integer> getMapWordsOccurances() {

        ArrayList<String> allWordsList = getAllWords();

        Set<String> uniqueWords = new HashSet<String>(allWordsList);
        Map<String, Integer> occurancesWordsMap = new HashMap<>();

        for (String word : uniqueWords) {
            //System.out.println(word + ": " + Collections.frequency(list, word));
            occurancesWordsMap.put(word, Collections.frequency(allWordsList, word));
        }

        return occurancesWordsMap;
    }


    private void makeWordsList() {
        ArrayList<String> list = new ArrayList<String>();
        List<String> URLlist = getAllSongLinks();

        for (String url : URLlist) {
            list.addAll(getTextFromUrl(url));
        }
        allWords = list;
    }

    // Only TOP30 SONGS
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


    public List getTextFromUrl(String URL) {

        ArrayList<String> textList = new ArrayList<String>();

        String[] textArray = getElementsFromUrlClass(URL, "song-text").text().toLowerCase().replaceAll("\\p{Punct}", "").split(" ");
        textList.addAll(Arrays.asList(textArray));

        return textList;
    }


    public Integer getNumberOfSongs() {
        String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",alfabetycznie,strona,1.html";
        String allSongs = getElementsFromUrlClass(url, "belka short").text().replaceAll("\\D+", "");
        return Integer.valueOf(allSongs);
    }


    public List<String> getAllSongLinks() {  //GET ALL OF exists songs

        List songsUrlList = new ArrayList();
        for (int i = 1; i <= getNumberOfSongs() / 30 + 1; i++) {
            String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",popularne,malejaco,strona," + i + ".html";

            for (Element link : getElementsFromUrlClass(url, "ranking-lista").select("a[href]")) {   // + .select("a[href]") to get only URLs from that divclass
                if (!link.attr("abs:href").isEmpty()) {
                    songsUrlList.add(link.attr("abs:href"));
                } else {

                }
            }
        }

        return songsUrlList;
    }

    public boolean checkIfArtistExist(){
        String url = "https://www.tekstowo.pl/piosenki_artysty/" + name.replace(" ", "_");
        String numberOfSongs = getElementsFromUrlClass(url, "col-md-7 col-lg-8 px-0").text().replaceAll("\\D+", "");
        return Integer.valueOf(numberOfSongs) > 0  ?  true :  false;
    }


}
