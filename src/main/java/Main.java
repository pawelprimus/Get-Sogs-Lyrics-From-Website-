
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import java.io.*;
import java.util.*;




public class Main {

    public static void main(String[] args) throws IOException {


        Artist artist = new Artist("Taco Hemingway");


       /* System.out.println("Before sort" +artist.getAllWords());
        System.out.println("Before sort with occurances" + artist.getMapWordsOccurances());
        System.out.println("After sort" + artist.getWordsAscending());

        System.out.println("Sum of all words: " + artist.getSumOfAllWords());
*/

        artist.importToExcel();




    }















}
