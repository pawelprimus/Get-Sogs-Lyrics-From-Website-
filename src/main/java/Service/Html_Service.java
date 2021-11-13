package Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Html_Service {

    //Links
    private  static final String ALL_SONGS_URL = "https://www.tekstowo.pl/piosenki_artysty,%s,popularne,malejaco,strona,1.html";
    private  static final String ALL_SONGS_URL_PAGE = "https://www.tekstowo.pl/piosenki_artysty,%s,popularne,malejaco,strona,%d.html";
    //HTML Class names
    private  static final String RANKING_LIST = "ranking-lista";
    private  static final String SONG_TEXT = "song-text";
    private  static final String SONGS_AMOUNT_BOX = "col-md-7 col-lg-8 px-0";
    //HTML Headers
    private  static final String A_HREF = "a[href]";
    private  static final String ABS_HREF = "abs:href";


    public int getSumOfAllWords(String name) {
        return getMapWordsOccurances(name).values().stream().mapToInt(i -> i).sum();
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


    public Map<String, Integer> getMapWordsAscending(String name) {

        Map<String, Integer> words = getMapWordsOccurances(name);

        Map<String, Integer> sortedWordsMap = words.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedWordsMap;
    }

    public Map<String, Integer> getMapWordsOccurances(String name) {

        List<String> allWordsList = makeWordsList(name);

        Set<String> uniqueWords = new HashSet<String>(allWordsList);
        Map<String, Integer> occurancesWordsMap = new HashMap<>();

        for (String word : uniqueWords) {
            //System.out.println(word + ": " + Collections.frequency(list, word));
            occurancesWordsMap.put(word, Collections.frequency(allWordsList, word));
        }

        return occurancesWordsMap;
    }


    private List<String> makeWordsList(String name) {
        ArrayList<String> allWordsList = new ArrayList<String>();
        List<String> URLlist = getAllSongLinks(name.replace(" ", "_"));

        for (String url : URLlist) {
            allWordsList.addAll(getTextFromUrl(url));
        }
        return allWordsList;
    }

    // Only TOP30 SONGS
    public List<String> getSongLinks(String name) {

        String url = String.format(ALL_SONGS_URL, name.replace(" ", "_"));

        List songsUrlList = new ArrayList();

        try {
            Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
            Elements links = doc.getElementsByClass(RANKING_LIST).select(A_HREF);
            for (Element link : links) {
                if (!link.attr(ABS_HREF).isEmpty()) {
                    songsUrlList.add(link.attr(ABS_HREF));
                } else {

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return songsUrlList;
    }


    private List getTextFromUrl(String URL) {

        ArrayList<String> textList = new ArrayList<String>();

        String[] textArray = getElementsFromUrlClass(URL, SONG_TEXT).text().toLowerCase().replaceAll("\\p{Punct}", "").split(" ");
        textList.addAll(Arrays.asList(textArray));

        return textList;
    }


    public int getNumberOfSongs(String name) {
        String url = String.format(ALL_SONGS_URL, name.replace(" ", "_"));

        String allSongs = getElementsFromUrlClass(url, SONGS_AMOUNT_BOX).text().replaceAll("\\D+", "");
        return Integer.parseInt(allSongs);
    }


    private List<String> getAllSongLinks(String name) {  //GET ALL OF exists songs

        List songsUrlList = new ArrayList();
        for (int i = 1; i <= getNumberOfSongs(name) / 30 + 1; i++) {
            String url = String.format(ALL_SONGS_URL_PAGE, name.replace(" ", "_"),i);

            for (Element link : getElementsFromUrlClass(url, RANKING_LIST).select(A_HREF)) {   // + .select("a[href]") to get only URLs from that divclass
                if (!link.attr(ABS_HREF).isEmpty()) {
                    songsUrlList.add(link.attr(ABS_HREF));
                } else {

                }
            }
        }

        return songsUrlList;
    }

    public boolean checkIfArtistExist(String name) {
        String url = "https://www.tekstowo.pl/piosenki_artysty/" + name.replace(" ", "_");
        String numberOfSongs = getElementsFromUrlClass(url, SONGS_AMOUNT_BOX).text().replaceAll("\\D+", "");
        return Integer.valueOf(numberOfSongs) > 0 ? true : false;
    }
}
