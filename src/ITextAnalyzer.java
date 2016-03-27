import java.io.IOException;
import java.util.Collection;

interface ITextAnalyzer {

    /**
     * Reads the words from the text file and builds the word count data.
     *
     * @throws IOException
     */
    void analyzeText(String filename) throws IOException;

    /**
     * @return Returns the total number of words in the analyzed text file
     */
    long getWordCount();

    /**
     * @return Returns the total number of unique words in the analyzed text file
     */
    long getUniqueWordCount();

    /**
     * @return Returns the word data associated with the word. Returns null if the word
     * does nor exist in the analyzed text.
     */
    IWordData findWord(String word);

    /**
     * @return Returns all the unique words in the analyzed text sorted by frequency
     * count in descending order.
     */
    Collection<IWordData> allWordsOrdedByFrequencyCount();

    /**
     * @return Returns all the unique words in the analyzed text sorted alphabetically
     * in ascending order.
     */
    Collection<IWordData> allWordsOrderByText();

}
