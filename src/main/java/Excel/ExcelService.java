/*
package Excel;

import Model.Artist;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

class ExcelService {


    Artist artist;

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
        cell.setCellValue("All words =" + artist.getSumOfAllWords());
        cell = row.createCell(cellnum++);
        cell.setCellValue("Number of Songs =" + artist.getNumberOfSongs());

        Double allWords = Double.valueOf(artist.getSumOfAllWords());

        for (Map.Entry<String, Integer> entry : artist.getWordsAscending().entrySet()) {

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
            FileOutputStream out = new FileOutputStream(new File(artist.getName() + "Words.xlsx"));
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
*/
