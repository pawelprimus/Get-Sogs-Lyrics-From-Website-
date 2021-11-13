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
import java.util.Set;

public class ExcelService {


    private static final String WORDS = "words";
    private static final String OCCURANCES = "Occurances";
    private static final String PERCENT = "%";
    private static final String ALL_WORDS = "All words :";
    private static final String NUM_OF_SONGS = "Number of Songs :";


    public void exportToExcel(String artistName, ArtistStats artistStats) throws IOException {
        //create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("AllWords");

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
        cell.setCellValue(ALL_WORDS + artistStats.getSumOfAllWords());
        cell = row.createCell(cellnum++);
        cell.setCellValue(NUM_OF_SONGS + artistStats.getNumberOfAllSong());

        Double allWords = Double.valueOf(artistStats.getSumOfAllWords());

        Map<String, Integer> allWordsMap = artistStats.getWordsOccurences();

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
            FileOutputStream out = new FileOutputStream(new File(artistName + "_words.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println(artistName + ".xlsx has been created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

    }
}
