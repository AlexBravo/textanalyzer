package com.alexbravo;

import java.util.*;

class WordData implements IWordData {

    private final String analyzedWord;
    private long count;
    Map<String, IWordData> words;

    WordData(long count, String text) {
        this.count = count;
        this.analyzedWord = text;

        words = new HashMap<>();
    }

    //Method to update the count if the key already exists in the map
    void updateFrequencyCount() {
        //Always increments by 1 so there is no need for a parameter to specify a number
        this.count++;
    }

    @Override
    public long getFrequencyCount() {
        //Return the frequency count specific to this object
        return count;
    }

    @Override
    public String getText() {
        //Return the word specific to this object
        return analyzedWord;
    }

    @Override
    public int getWordCount(int minCount) {
        int count = 0;
        List<IWordData> wordsList = new LinkedList<>(words.values());
        for (IWordData word : wordsList) {
            if (word.getFrequencyCount() >= minCount) {
                count++;
            }
        }
        return count;
    }


    //@Override
    @SuppressWarnings("unused")
    public void printWords(int minCount) {
        //Put all of the values in the map (which are IWordData objects) into a list
        List<IWordData> wordsOrderedByFrequency = new LinkedList<>(words.values());
        //Sort the list based on the comparator below. This will sort them in descending order
        Collections.sort(wordsOrderedByFrequency, new Comparator<IWordData>() {

            @Override
            public int compare(IWordData o1, IWordData o2) {
                long frequency1 = o1.getFrequencyCount();
                long frequency2 = o2.getFrequencyCount();
                //Descending order
                return (int) (frequency2 - frequency1);
            }
        });

        //Collection<IWordData> wordsOrderedByFrequency = treeAnalyzer.allWordsOrdedByFrequencyCount();
        Iterator<IWordData> it = wordsOrderedByFrequency.iterator();
        int count = 0;
        while (it.hasNext()) {
            IWordData word = it.next();
            if (word.getFrequencyCount() >= minCount) {
                String wordText = word.getText();
                System.out.println("   '" + wordText + "' " + word.getFrequencyCount());
                count++;
            }
        }
        if(count > 0) {
            System.out.println("   " + count + " words with count of " + minCount + " or more");
        }
    }

}
