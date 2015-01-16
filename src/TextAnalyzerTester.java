import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class TextAnalyzerTester {

    public static void main(String[] args) {
        //With the tree map
        TextAnalyzer treeAnalyzer = new TextAnalyzer("treemap");
        try {
            treeAnalyzer.analyzeText("txt/812_notes.txt");
            treeAnalyzer.mccEfficiencies("txt/812_notes.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Test the frequency count ordering
        Collection<IWordData> wordsOrderedByFrequency = treeAnalyzer.allWordsOrdedByFrequencyCount();
        Iterator<IWordData> it = wordsOrderedByFrequency.iterator();
        //for (IWordData word : wordsOrderedByFrequency) {
        String mccs = "";
        String mccList = "\"the\", \"ing\", \"and\", \"ion\"";
        int i = 4;
        while (true) {
            IWordData word = it.next();
            double count = (double)((word.getFrequencyCount() + 50)/100)/10.0;
            if (count >= 8.0) {
                String wordText = word.getText();
                // "  " is not spaces, but something similar
                if (!wordText.equals("io") && !wordText.equals("ng") && !wordText.equals("  ")) {
                    i++;
                    mccs += i + " '" + wordText + "' " + count + "\n";
                    // Generate the list of MCCs
                    mccList += ", \"" + wordText + "\"";
                }
            } else {
                break;
            }
        }

        //System.out.println(mccs);
        System.out.println(mccList);

        /*AB
        //Test the text ordering
        Collection<IWordData> wordsOrderedByText = treeAnalyzer.allWordsOrderByText();
        for (IWordData word : wordsOrderedByText) {
            System.out.println(word.getText());
        }
        //Find a word
        try {
            System.out.println(treeAnalyzer.findWord("test").getFrequencyCount()); //This word doesn't exist
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(treeAnalyzer.findWord("the").getFrequencyCount()); //But this one does
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        */
        //Get unique word count
        //System.out.println("Unique Words " + treeAnalyzer.getUniqueWordCount());
        //Get word count
        //System.out.println("Words " + treeAnalyzer.getWordCount());
/*AB
        //With the hash map
        TextAnalyzer hashAnalyzer = new TextAnalyzer("hashmap");
        try {
            hashAnalyzer.analyzeText("txt/aliceinwonderland.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Test the frequency count ordering
        Collection<IWordData> hashWordsOrderedByFrequency = treeAnalyzer.allWordsOrdedByFrequencyCount();
        for (IWordData word : hashWordsOrderedByFrequency) {
            System.out.println(word.getFrequencyCount());
        }
        //Test the text ordering
        Collection<IWordData> hashWordsOrderedByText = treeAnalyzer.allWordsOrderByText();
        for (IWordData word : hashWordsOrderedByText) {
            System.out.println(word.getText());
        }
        //Find a word
        try {
            System.out.println(hashAnalyzer.findWord("test").getFrequencyCount()); //This word doesn't exist
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(hashAnalyzer.findWord("the").getFrequencyCount()); //But this one does
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        //Get unique word count
        System.out.println(hashAnalyzer.getUniqueWordCount());
        //Get word count
        System.out.println(hashAnalyzer.getWordCount());
*/
    }
}
