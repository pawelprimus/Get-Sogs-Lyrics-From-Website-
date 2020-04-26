import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {


        Artist artist = new Artist("Taco Hemingway");

        ArrayList<String> text = artist.getSongText();

        Map<String, Integer> words = artist.getWordsOccurances(text);
        System.out.println("Before sort" +artist.getWordOccurancy());

        System.out.println("After sort" + artist.getWordsAscending());

    }













}
