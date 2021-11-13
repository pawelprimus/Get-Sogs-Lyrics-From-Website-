import Excel.ExcelService;
import Model.Artist;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws IOException {

        Artist artist = new Artist("mata");

        artist.setArtistStats();
        ExcelService excelService = new ExcelService();


        excelService.exportToExcel(artist);

    }


}
