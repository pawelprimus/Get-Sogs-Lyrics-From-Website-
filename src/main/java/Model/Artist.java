package Model;

import Service.Html_Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Artist {

    private String name;
    private ArtistStats artistStats = new ArtistStats();
    private Html_Service html_service = new Html_Service();

    public Artist(String name) {
        this.name = name;
    }

    public void setArtistStats(){
        artistStats.setSumOfAllWords(getSumOfAllWords());
        artistStats.setNumberOfAllSong(getNumberOfSongs());
        artistStats.setWordsOccurences(getAllWords(OrderType.ASCENDING));
    }

    public String getName() {
        return name;
    }

    public ArtistStats getArtistStats() {
        return artistStats;
    }

    public List<String> getAllSongLinks() {
        List<String> links = html_service.getSongLinks(name);
        return links;
    }

    private int getSumOfAllWords() {
        int sumOfAllWords = html_service.getSumOfAllWords(name);
        return sumOfAllWords;
    }

    private Integer getNumberOfSongs() {
        int numberOfAllSongs = html_service.getNumberOfSongs(name);
        return numberOfAllSongs;
    }


    private Map<String, Integer> getAllWords(OrderType orderType) {
        Map<String, Integer> words = null;

        if (orderType.equals(OrderType.DEFAULT)) {
            words = html_service.getMapWordsOccurances(name);
        } else if (orderType.equals(OrderType.ASCENDING)) {
            words = html_service.getMapWordsAscending(name);
        }
        return words;
    }

    public enum OrderType {
        DEFAULT, ASCENDING;
    }

}
