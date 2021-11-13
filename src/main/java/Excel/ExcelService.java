package Excel;

import Model.Artist;
import Model.ArtistStats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ExcelService {

    //Cells name in excel
    private static final String WORDS = "words";
    private static final String OCCURANCES = "Occurances";
    private static final String PERCENT = "%";
    private static final String ALL_WORDS = "All words :";
    private static final String NUM_OF_SONGS = "Number of Songs :";
    private static final String SHEET_NAME = "Words occurences";


    public void exportToExcel(Artist artist) throws IOException {
        //create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet(SHEET_NAME);

        int rownum = 0;
        int cellnum = 0;

        Row row = sheet.createRow(rownum++);

        Cell cell = row.createCell(cellnum++);
        cell.setCellValue(WORDS);
        cell = row.createCell(cellnum++);
        cell.setCellValue(OCCURANCES);
        cell = row.createCell(cellnum++);
        cell.setCellValue(PERCENT);
        cell = row.createCell(cellnum++);
        cell.setCellValue(ALL_WORDS + artist.getArtistStats().getSumOfAllWords());
        cell = row.createCell(cellnum++);
        cell.setCellValue(NUM_OF_SONGS + artist.getArtistStats().getNumberOfAllSong());


        double allWords =  artist.getArtistStats().getSumOfAllWords();
        Map<String, Integer> allWordsMap = artist.getArtistStats().getWordsOccurences();

        for (Map.Entry<String, Integer> entry : allWordsMap.entrySet()) {

            cellnum = 0;
            row = sheet.createRow(rownum++);
            cell = row.createCell(cellnum++);
            cell.setCellValue(entry.getKey());
            cell = row.createCell(cellnum++);
            cell.setCellValue(entry.getValue());
            cell = row.createCell(cellnum++);

            double currentLoopWord = Double.valueOf(entry.getValue());
            double percentageOccurencesValue = currentLoopWord / allWords;
            cell.setCellValue(percentageOccurencesValue);
        }

        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(artist.getName() + "_words.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println(artist.getName() + ".xlsx has been created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

    }
}
