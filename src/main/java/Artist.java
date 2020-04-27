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

    public int getSumOfAllWords() {
        return getMapWordsOccurances().values().stream().mapToInt(i -> i).sum();
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


    public void makeWordsList() {
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


    public Integer getNumberOfSongs() {


        //String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",popularne,malejaco,strona,1.html";
        String url = "https://www.tekstowo.pl/piosenki_artysty," + name.replace(" ", "_") + ",alfabetycznie,strona,1.html";

        String allSongs = "";

        try {
            Document document = Jsoup.connect(url).ignoreHttpErrors(true).get();
            Elements elements = document.getElementsByClass("belka short");

            allSongs = elements.text().replaceAll("\\D+", "");// delete all non digits

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.valueOf(allSongs);

    }

    public void importToExcel() throws IOException {
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
        cell.setCellValue("All words =");
        cell = row.createCell(cellnum++);
        cell.setCellValue(getSumOfAllWords());

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


}
