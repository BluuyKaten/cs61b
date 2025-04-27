package ngrams;

import edu.princeton.cs.algs4.In;


import java.util.Collection;
import java.util.Iterator;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import static utils.Utils.*;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
     In wordFile;
     In countFile;
    // TODO: Add any necessary static/instance variables.

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordFile = new In(wordsFilename);
        countFile = new In(countsFilename);
//        int i = 0;
//
//        while (wordFile.hasNextLine()){
//            i++;
//            String nextLine = wordFile.readLine();
//            System.out.print("Line " + i + " is: ");
//            System.out.println(nextLine);
//            System.out.print("After splitting on tab characters, the first word is: ");
//            String[] splitLine = nextLine.split("\t");
//            System.out.println(splitLine[0]);
//        }
//        while (countFile.hasNextLine()){
//            i++;
//            String nextLine = countFile.readLine();
//            System.out.print("Line " + i + " is: ");
//            System.out.println(nextLine);
//            System.out.print("After splitting on tab characters, the first word is: ");
//            String[] splitLine = nextLine.split(",");
//            System.out.println(splitLine[0]);
//        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries t= new TimeSeries();
        In file = wordFile;
        while (file.hasNextLine()){
            String nextLine = file.readLine();
            String[] splitLine = nextLine.split("\t");
            if (splitLine[0].equals(word)  && Integer.parseInt(splitLine[1]) > startYear && Integer.parseInt(splitLine[1]) < endYear){
                t.put(Integer.parseInt(splitLine[1]),Double.parseDouble(splitLine[2]));
            }
        }
        return t;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        In file = wordFile;
        TimeSeries t= new TimeSeries();
        while (file.hasNextLine()){
            String nextLine = file.readLine();
            String[] splitLine = nextLine.split("\t");
            if (splitLine[0].equals(word)){
                t.put(Integer.parseInt(splitLine[1]),Double.parseDouble(splitLine[2]));
            }
        }
        return t;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        In file = countFile;
        TimeSeries t= new TimeSeries();
        while (file.hasNextLine()){
            String nextLine = file.readLine();
            String[] splitLine = nextLine.split(",");
            t.put(Integer.parseInt(splitLine[0]),Double.parseDouble(splitLine[1]));
        }
        return t;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries t= new TimeSeries();
        In file = countFile;
        while (file.hasNextLine()){
            String nextLine = file.readLine();
            String[] splitCountLine = nextLine.split(",");
            if (splitCountLine[0].equals(word)&& Integer.parseInt(splitCountLine[1]) > startYear && Integer.parseInt(splitCountLine[1]) < endYear){
                t.put(Integer.parseInt(splitCountLine[0]),this.countHistory(word).get(Integer.parseInt(splitCountLine[0]))/Double.parseDouble(splitCountLine[1]));
            }
        }
        return t;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        In file = countFile;
        TimeSeries t= new TimeSeries();
        while (file.hasNextLine() ){
            String nextLine = file.readLine();
            String[] splitCountLine = nextLine.split(",");
            if (splitCountLine[0].equals(word)){
                t.put(Integer.parseInt(splitCountLine[0]),this.countHistory(word).get(Integer.parseInt(splitCountLine[0]))/Double.parseDouble(splitCountLine[1]));
            }
        }
        return t;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries timeSeries= new TimeSeries();
        int size = words.size();
        String[] word = new String[size];
        int i = 0;
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()){
            word[i]= iterator.next();
            i++;
        }
        while (i > 0){
            TimeSeries newTimeSeries = countHistory(word[i],startYear,endYear);
            timeSeries.plus(newTimeSeries);
            timeSeries.dividedBy(totalCountHistory());
        }
        return timeSeries;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries timeSeries= new TimeSeries();
        int size = words.size();
        String[] word = new String[size];
        int i = 0;
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()){
            word[i]= iterator.next();
            i++;
        }
        while (i > 0){
            TimeSeries newTimeSeries = countHistory(word[i]);
            timeSeries.plus(newTimeSeries);
            timeSeries.dividedBy(totalCountHistory());
        }
        return timeSeries;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
