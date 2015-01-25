import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextAnalyzer implements ITextAnalyzer {

    private Map<String, IWordData> words;

    //Specify what kind of map you want to use in the constructor
    public TextAnalyzer(String mapType) {
        if (mapType.equalsIgnoreCase("hashmap")) {
            words = new HashMap();
        } else if (mapType.equalsIgnoreCase("treemap")) {
            words = new TreeMap();
        } else {
            throw new IllegalArgumentException("Incorrect parameter for the constructor. Expecting \"hash\" or \"tree\"");
        }
    }

    @Override
    public Collection<IWordData> allWordsOrdedByFrequencyCount() {
        //Put all of the values in the map (which are IWordData objects) into a list
        List<IWordData> sortedWords = new LinkedList<>(words.values());
        //Sort the list based on the comparator below. This will sort them in descending order
        Collections.sort(sortedWords, new Comparator<IWordData>() {

            @Override
            public int compare(IWordData o1, IWordData o2) {
                long frequency1 = o1.getFrequencyCount();
                long frequency2 = o2.getFrequencyCount();
                //Descending order
                return (int) (frequency2 - frequency1);
            }
        });
        //Return the sorted list
        return sortedWords;
    }

    @Override
    public Collection<IWordData> allWordsOrderByText() {
        //Put all of the keys (words) in the map into a list
        List<IWordData> sortedText = new LinkedList<>(words.values());

        Collections.sort(sortedText, new Comparator<IWordData>() {
            public int compare(IWordData o1, IWordData o2) {
                return o1.getText().compareTo(o2.getText());
            }
        });
        //Return the sorted list
        return sortedText;
    }

    //Method to remove any special characters that don't form a word from a string
    private String removeSpecialCharacters(String s) {
        return s.replaceAll("[\\!\\@\\[\\]\\.\\,\\:\\;\"\'\\-\\?\\#\\$\\(\\)\\*]", "");
    }

    @Override
    public void analyzeText(String filename) throws FileNotFoundException, IOException {
        //Read the file specified into the scanner
        Scanner inp = new Scanner(new FileReader("txt/" + filename));
        while (inp.hasNext()) {
            //Store each word in the chunk variable and make sure to remove any special characters
            String chunk = removeSpecialCharacters(inp.next());
            //String chunk = inp.next();
            //Do this condition first since we'll be putting more words into the map than updating the frequency count
            if (!words.containsKey(chunk)) {
                //Insert each chunk into the map as the key. The value is the frequency (1 by default) and the text itself
                words.put(chunk, new WordData(1, chunk));
            } else {
                //If the map already contains the key, index into it and increment the count value
                WordData key = (WordData) words.get(chunk);
                key.updateFrequencyCount();
            }
        }
    }

    public void findSuffixes(String filename, int suffixLength) throws FileNotFoundException, IOException {
        //Read the file specified into the scanner
        Scanner inp = new Scanner(new FileReader("txt/" + filename));
        while (inp.hasNext()) {
            //Store each word in the chunk variable and make sure to remove any special characters
            String chunk = removeSpecialCharacters(inp.next()).toLowerCase();
            int lenght = chunk.length();

            // Look only at long words and the ones that don't have numbers in them
            if (lenght >= suffixLength + 3 && !chunk.matches(".*\\d+.*")) {
                String suffix = chunk.substring(lenght - suffixLength, lenght);

                //Do this condition first since we'll be putting more words into the map than updating the frequency count
                if (!words.containsKey(suffix)) {
                    //Insert each chunk into the map as the key. The value is the frequency (1 by default) and the text itself
                    words.put(suffix, new WordData(1, suffix));
                } else {
                    //If the map already contains the key, index into it and increment the count value
                    String prefix = chunk.substring(0, lenght - suffixLength);
                    WordData key = (WordData) words.get(suffix);
                    key.updateFrequencyCount();

                    //Add original word to the  suffix
                    if (!key.words.containsKey(prefix)) {
                        key.words.put(prefix, new WordData(1, prefix));
                    } else {
                        WordData suffixKey = (WordData) key.words.get(prefix);
                        suffixKey.updateFrequencyCount();
                    }
                }
            }
        }
    }
    public void findNGrams(String filename, int nGramLenght) throws FileNotFoundException, IOException {
        long chords = 0;
        Scanner inp = new Scanner(new FileReader("txt/" + filename)).useDelimiter("\n");
        while (inp.hasNext()) {
            String line = inp.next();
            // Find all character frequencies
            int length = line.length();
            chords += length;
            // Find all n-bigrams
            for (int i = 0; i < length - (nGramLenght - 1); i++) {
                String chunk = line.substring(i, i + nGramLenght).toLowerCase();
                if (!(chunk.contains(" ") || chunk.contains("-"))) {
                    //Do this condition first since we'll be putting more words into the map than updating the frequency count
                    if (!words.containsKey(chunk)) {
                        //Insert each chunk into the map as the key. The value is the frequency (1 by default) and the text itself
                        words.put(chunk, new WordData(1, chunk));
                    } else {
                        //If the map already contains the key, index into it and increment the count value
                        WordData key = (WordData) words.get(chunk);
                        key.updateFrequencyCount();
                    }
                }
            }
        }
        //System.out.println("chords " + chords);
    }

    public void mccEfficiencies(String filename, String mccs[]) throws FileNotFoundException, IOException {
        //Read the file specified into the scanner
        long letters = 0;
        long chords = 0;
        Scanner inp = new Scanner(new FileReader("txt/" + filename)).useDelimiter("\n");
        while (inp.hasNext()) {
            //Store each word in the chunk variable and make sure to remove any special characters
            //String chunk = removeSpecialCharacters(inp.next());

            String line = inp.next();
            //line = "\"the\", \"ing\", \"and\", \"ion\", \"in\",";
            //line = "tztz"; // "thethe"  "thth";

            String newLine = line;
            for (String mcc: mccs) {
                newLine = newLine.replaceAll(mcc, "*");
            }
            letters += line.length();
            chords += newLine.length();
        }
        System.out.println("For " + filename + ":");
        System.out.print(mccs.length + " MCCs are used, ");
        //System.out.println(chords + "chord are typed to output " + letters + " letters.");
        double speedIncrease = (double)(letters)/chords;
        System.out.print(((int)(speedIncrease * 100))/100.0 + " times speed increase. ");
        System.out.println("Saved chords: " + (letters - chords) +
                " (" + (int)(((double)(letters - chords))/letters * 100.0) + "%)");

        int exampleSpeed = 40;
        System.out.println("Typing with " + mccs.length + " MCCs will increase speed from " +
                exampleSpeed + " wpm to " +  (int)(exampleSpeed * speedIncrease) + " wpm.");

        System.out.println();
    }

    @Override
    public IWordData findWord(String word) {
        //Check if the map contains the word (which is the key)
        if (words.containsKey(word)) {
            //If it does, then return the IWordData object associated with it
            return words.get(word);
        } else {
            //If the key doesn't exist, return null
            return null;
        }
    }

    @Override
    public long getUniqueWordCount() {
        //Get the number of keys in the map. This is the unique word count
        return words.keySet().size();
    }

    @Override
    public long getWordCount() {
        //Iterate over all the IWordData objects in the map and add up the frequency count to get the total word count
        long totalWordCount = 0;
        for (Map.Entry<String, IWordData> entry : words.entrySet()) {
            //For each IWordData in the map, call it's getFrequencyCount() method and add it back into totalWordCount
            totalWordCount += entry.getValue().getFrequencyCount();
        }
        return totalWordCount;
    }
}
