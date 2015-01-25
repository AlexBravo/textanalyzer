import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class TextAnalyzerTester {

    public static void main(String[] args) {

        //analizeEfficiencyOfMCCs();
        analizeEfficiencyOfSuffixes();

        /*
        //Test the text ordering

        TextAnalyzer treeAnalyzer = new TextAnalyzer("treemap");
        String fileName = "812_notes.txt";
        //fileName = "aliceinwonderland.txt";

        try {
            treeAnalyzer.analyzeText(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        //Get unique word count
        System.out.println("Unique words " + treeAnalyzer.getUniqueWordCount());
        //Get word count
        System.out.println("All words " + treeAnalyzer.getWordCount());

        //With the hash map
        TextAnalyzer hashAnalyzer = new TextAnalyzer("hashmap");
        try {
            hashAnalyzer.analyzeText("txt/aliceinwonderland.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        /*
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

    public static void analizeEfficiencyOfSuffixes() {
        //With the tree map
        TextAnalyzer treeAnalyzer = new TextAnalyzer("treemap");
        String fileName = "812_notes.txt";

        final int MIN_COUNT = 100;
        final int MIN_PREFIXES = 5;
        final int MIN_PREFIX_OCCURENCES = 10;
        final int SUFFIX_LENGTH = 4;
        try {
            treeAnalyzer.findSuffixes(fileName, SUFFIX_LENGTH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Test the frequency count ordering
        Collection<IWordData> wordsOrderedByFrequency = treeAnalyzer.allWordsOrdedByFrequencyCount();
        Iterator<IWordData> it = wordsOrderedByFrequency.iterator();
        int count = 0;
        while(it.hasNext()) {
            IWordData word = it.next();
            if (word.getWordCount(MIN_PREFIX_OCCURENCES) >= MIN_PREFIXES && word.getFrequencyCount() >= MIN_COUNT) {
                String wordText = word.getText();
                System.out.println("'" + wordText + "' " + word.getFrequencyCount());
                count++;
                //word.printWords(MIN_PREFIX_OCCURENCES);
            }
        }

        System.out.println(count + " suffixes of length " + SUFFIX_LENGTH + " with count of "+MIN_COUNT+" or more.");
        //Get unique word count
        System.out.println("Unique words " + treeAnalyzer.getUniqueWordCount());
        //Get word count
        System.out.println("All words " + treeAnalyzer.getWordCount());
    }
    public static void analizeEfficiencyOfMCCs() {
        //With the tree map
        TextAnalyzer treeAnalyzer = new TextAnalyzer("treemap");
        String fileName = "812_notes.txt";
        //fileName = "aliceinwonderland.txt";
        /*
        String mccs[] = {
                "the", "ing", "and", "for", "you", "are", "ant", "not", "his", "but"
        };
        String mccs[] = {
                "tion", ".com", "with", "this", "from", "ting", "ment", "able",
        };
        String mccs[] = {
                "tion", "with", "this", "from", //"ting", "able", "ment", ".com",
                "the", "ing", "and", "ion",
                //"for", "you", "are", "ant", "not",

                "in", "er", "re", "th", "on", "or",
                "an", "le", "te", "es", "he", "at", "to", //"en", "co", "ro",
                //"ed", "ti", "st", "de", "al", "it", "se", "ar", "nt", "nd",
                //"ou", "om", "ma", "me", "li", "ne", "is", "il", "ve", "as",
                //"ra", "ta", "ll", "no", "ch", "ea", "et", "us", "ce", "ha",
                //"ec", "fo", "ic", "ot", "ge", "ac", "ri", "el", "la", "ct",
                //"ca"
        };
        String mccs[] = {
                "the","ing","and","ion","in", "er", "re", "th", "on", "or",
                "an", "le", "te", "es", "he", "at", "to", "en", "co", "ro",
                "ed", "ti", "st", "de", "al", "it", "se", "ar", "nt", "nd",
                "ou", "om", "ma", "me", "li", "ne", "is", "il", "ve", "as",
                "ra", "ta", "ll", "no", "ch", "ea", "et", "us", "ce", "ha",
                "ec", "fo", "ic", "ot", "ge", "ac", "ri", "el", "la", "ct",
                "ca"
        };
        String mccs[] = {
                "tion","with","this","from","able","ment",//"mail",".com",  // 1.02 speed increase for this line
                "the","ing","and","ion",//"com","for""ent","con","ent","all", // 1.05 speed increase for this line
                "in", "er", "re", "th", "on", "or", "an", "le", "te", "es",  // 1.08 speed increase for this line
                "he", "at", "to", "en", "co", "ro", "ed", "ti", "st", "de",  // 1.05 speed increase for this line
                "al", "it", "se", "ar", "ex", //"ng", "nt", "nd", "ou", "om", "ma",// 1.05 speed increase for this line
                "me", "li", "ne", "is", "il", "ve", "as", "ra", "ta", "ll"   // 1.03 speed increase for this line
                "no", "ch", "ea", "et", "us", "ce", "ha", "ec", "fo", "ic",  // 1.02 speed increase for this line
                //"ot", "ge", "ac", "ri", "el", "la", "ct", "ca"
        };
        */
        String mccs[] = { // final MCCs
                "the","ing","and","ion",
                "in", "er", "re", "th", "on", "or", "an", "le", "te", "es",
                "he", "at", "to", "en", "co", "ro", "ed", "ti", "st", "de",
                "al", "it", "se", "ar",
                "ex", "sh", "ch", "ck", "is", "ph"
        };

        try {
            treeAnalyzer.findNGrams(fileName, 2);
            treeAnalyzer.mccEfficiencies(fileName, mccs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mcc = "";
        String mccList = ""; //"\"the\", \"ing\", \"and\", \"ion\"";

        Collection<IWordData> wordsOrderedByFrequency = treeAnalyzer.allWordsOrdedByFrequencyCount();
        Iterator<IWordData> it = wordsOrderedByFrequency.iterator();

        int i = 0;
        while (true) {
            IWordData word = it.next();
            double count = (double)((word.getFrequencyCount() + 50)/100)/10.0;
            if (count >= 0.1) {
                String wordText = word.getText();
                // "  " is not spaces, but something similar
                if (!wordText.equals("io") && !wordText.equals("ng") && !wordText.equals("  ")) {
                    i++;
                    mcc += i + " '" + wordText + "' " + count + "\n";
                    // Generate the list of MCCs
                    mccList += ", \"" + wordText + "\"";
                }
            } else {
                break;
            }
        }
        //System.out.println(mcc);
        //System.out.println(mccList);

        //Get unique word count
        System.out.println("Unique words " + treeAnalyzer.getUniqueWordCount());
        //Get word count
        System.out.println("All words " + treeAnalyzer.getWordCount());
    }
}
