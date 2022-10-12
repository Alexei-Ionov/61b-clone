package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.TreeMap;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 */
public class NGramMap {

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    private TreeMap wordMap;
    private TimeSeries countMap;
    public NGramMap(String wordsFilename, String countsFilename) {
        // thoughts: each word in wordsFileName should map to its own TimeSeriesMap (year --> #number of times it is seen)
        wordMap = new TreeMap<String, TimeSeries>();
        countMap = new TimeSeries();
        //order in wordsFile is Word(String) --> Year(Integer) --> Count(Double) --> unnecessary
        In in = new In(wordsFilename);
        while (in.hasNextLine()) {
            String word = in.readString();
            Integer year = in.readInt();
            Integer count = in.readInt();
            in.readInt(); //useless column

            if (!wordMap.containsKey(word)) {
                wordMap.put(word, new TimeSeries());
            }
            TimeSeries val = (TimeSeries) wordMap.get(word);
            val.put(year, (double) count);
        }
        //order in countFile is Year(Integer) --> total number of words recorded (Double)
        In inCount = new In(countsFilename);
        while (in.hasNextLine()) {
            Integer year = inCount.readInt();
            Integer count = inCount.readInt();
            inCount.readInt();
            inCount.readInt();
            countMap.put(year, (double) count);
        }

    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
       return helperMethod(word, 0, 0, 0);
    }

    private TimeSeries helperMethod(String word, int startYear, int endYear, int val) {
        TimeSeries map = (TimeSeries) wordMap.get(word);
        TimeSeries res = new TimeSeries();
        for (Integer year : map.keySet()) {
            // gets called only for Count History  with no start and end date
            if (val == 0) {
                res.put(year, map.get(year));
                continue;
            }
            // gets called only for countHistory with start and end date
            if (year >= startYear && year <= endYear) {
                res.put(year, map.get(year));
            }
        }
        return res;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return helperMethod(word, startYear, endYear, 1);
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries res = new TimeSeries();
        for (Integer year: countMap.keySet()) {
            res.put(year, countMap.get(year));
        }
        return res;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        TimeSeries res = new TimeSeries();
        TimeSeries map = (TimeSeries) wordMap.get(word);
        for (Integer year: map.keySet()) {
            res.put(year, map.get(year) / countMap.get(year));
        }
        return res;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return null;
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return null;
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        return null;
    }


}