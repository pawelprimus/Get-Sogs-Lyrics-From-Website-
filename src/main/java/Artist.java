import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Artist {

    private  String name;
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

    public int getSumOfAllWords() {
        return getMapWordsOccurances().values().stream().mapToInt(i -> i).sum();
    }

    private Elements getElementsFromUrlClass(String URL, String className){
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


    public List getTextFromUrl(String URL){

        ArrayList<String> textList = new ArrayList<String>();

        String []textArray = getElementsFromUrlClass(URL,"song-text" ).text().toLowerCase().replaceAll("\\p{Punct}", "").split(" ");
        textList.addAll(Arrays.asList(textArray));

        return textList;
    }


    public Integer getNumberOfSongs() {
        String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",alfabetycznie,strona,1.html";
        String allSongs= getElementsFromUrlClass(url,"belka short").text().replaceAll("\\D+", "");
        return Integer.valueOf(allSongs);
    }

    public void exportToExcel() throws IOException {
        //create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("AllWords");

        int rownum = 0;
        int cellnum = 0;

        Row row = sheet.createRow(rownum++);

        Cell cell = row.createCell(cellnum++);
        cell.setCellValue("Words");
        cell = row.createCell(cellnum++);
        cell.setCellValue("Occurances");
        cell = row.createCell(cellnum++);
        cell.setCellValue("%");
        cell = row.createCell(cellnum++);
        cell.setCellValue("All words =" + getSumOfAllWords());
        cell = row.createCell(cellnum++);
        cell.setCellValue("Number of Songs =" + getNumberOfSongs());

        Double allWords = Double.valueOf(getSumOfAllWords());

        for (Map.Entry<String, Integer> entry : getWordsAscending().entrySet()) {

            cellnum = 0;
            row = sheet.createRow(rownum++);
            cell = row.createCell(cellnum++);
            cell.setCellValue(entry.getKey());
            cell = row.createCell(cellnum++);
            cell.setCellValue(entry.getValue());
            cell = row.createCell(cellnum++);

            Double thatWord = Double.valueOf(entry.getValue());

            cell.setCellValue(thatWord / allWords);
        }

        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(getName() + "Words.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println(getName() + ".xlsx has been created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }


    public List<String> getAllSongLinks() {  //GET ALL OF exists songs

        List songsUrlList = new ArrayList();
        for (int i = 1; i <= getNumberOfSongs() / 30 + 1; i++) {
            String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",popularne,malejaco,strona,"+ i +".html";

            for (Element link : getElementsFromUrlClass(url,"ranking-lista").select("a[href]")) {   // + .select("a[href]") to get only URLs from that divclass
                if (!link.attr("abs:href").isEmpty()) {
                    songsUrlList.add(link.attr("abs:href"));
                } else {

                }
            }
    }

        return songsUrlList;
    }


}
