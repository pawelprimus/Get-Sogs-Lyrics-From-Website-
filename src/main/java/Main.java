import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {


        Artist artist = new Artist("mata");

        System.out.println(artist.checkIfArtistExist());

        System.out.println(artist.getNumberOfSongs());
        System.out.println(artist.getSongLinks());

        //System.out.println(artist.getElementsFromUrlClass("https://www.tekstowo.pl/piosenki_artysty,mata,alfabetycznie,strona,1.html", "col-md-7 col-lg-8 px-0"));

        System.out.println("Before sort" + artist.getAllWords());
        System.out.println("Before sort with occurances" + artist.getMapWordsOccurances());
        System.out.println("After sort" + artist.getWordsAscending());
        System.out.println("Sum of all words: " + artist.getSumOfAllWords());

        //artist.exportToExcel();


    }


}
