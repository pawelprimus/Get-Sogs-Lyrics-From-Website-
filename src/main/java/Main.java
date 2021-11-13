import Excel.ExcelService;
import Model.Artist;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws IOException {


        Artist artist = new Artist("mata");

        System.out.println(artist.getNumberOfSongs());
        List<String> songsLinks = artist.getAllSongLinks();

        for (String link : songsLinks){
            System.out.println(link);
        }

        Map<String, Integer> map = artist.getAllWords(Artist.OrderType.ASCENDING);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        ExcelService excelService = new ExcelService();

        excelService.exportToExcel(artist);
        //System.out.println(artist.getElementsFromUrlClass("https://www.tekstowo.pl/piosenki_artysty,mata,alfabetycznie,strona,1.html", "col-md-7 col-lg-8 px-0"));


        //artist.exportToExcel();


    }


}
