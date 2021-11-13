package Excel;

import Model.Artist;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


public class ExcelService {

    private static final Path WORKING_DIRECTORY = Paths.get(System.getProperty("user.dir"));
    private static final String OUTPUT_FOLDER_NAME = "output";

    //Cells name in excel
    private static final String WORDS = "words";
    private static final String OCCURANCES = "Occurances";
    private static final String PERCENT = "%";
    private static final String ALL_WORDS = "All words :";
    private static final String NUM_OF_SONGS = "Number of Songs :";
    private static final String SHEET_NAME = "Words occurences";

    private static final String OUTPUT_FILE_NAME = "Words";
    private static final String XLSX_EXTENSION = ".xlsx";


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


        double allWords = artist.getArtistStats().getSumOfAllWords();
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
            File outputDirectory = new File(WORKING_DIRECTORY + "\\" + OUTPUT_FOLDER_NAME);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdir();
            }

            //Write the workbook in file system
            String outputFileName = artist.getName() + OUTPUT_FILE_NAME + XLSX_EXTENSION;
            FileOutputStream out = new FileOutputStream(new File(outputDirectory + "\\" + outputFileName));
            workbook.write(out);
            out.close();
            System.out.println(outputFileName + " has been created successfully in " + outputDirectory.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

    }
}
