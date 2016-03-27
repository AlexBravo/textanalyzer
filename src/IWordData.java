interface IWordData {

    /**
     * @return Returns the number of times the word occurred in the analyzed text
     */
    long getFrequencyCount();

    /**
     * @return Returns the word text
     */
    String getText();

    int getWordCount(int minCount);

    //void printWords(int minCount);
}
