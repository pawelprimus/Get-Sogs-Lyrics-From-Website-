package Model;

import java.util.Map;

public class ArtistStats {

    private int sumOfAllWords;
    private int numberOfAllSong;
    private Map<String, Integer> wordsOccurences;

    public int getSumOfAllWords() {
        return sumOfAllWords;
    }

    public void setSumOfAllWords(int sumOfAllWords) {
        this.sumOfAllWords = sumOfAllWords;
    }

    public Integer getNumberOfAllSong() {
        return numberOfAllSong;
    }

    public void setNumberOfAllSong(Integer numberOfAllSong) {
        this.numberOfAllSong = numberOfAllSong;
    }

    public Map<String, Integer> getWordsOccurences() {
        return wordsOccurences;
    }

    public void setWordsOccurences(Map<String, Integer> wordsOccurences) {
        this.wordsOccurences = wordsOccurences;
    }
}
