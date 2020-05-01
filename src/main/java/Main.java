import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {


        Artist artist = new Artist("Lona");



       /* System.out.println("Before sort" +artist.getAllWords());
        System.out.println("Before sort with occurances" + artist.getMapWordsOccurances());
        System.out.println("After sort" + artist.getWordsAscending());

        System.out.println("Sum of all words: " + artist.getSumOfAllWords());
*/

        artist.exportToExcel();


        //System.out.println(artist.getAllSongLinks());



    }















}
