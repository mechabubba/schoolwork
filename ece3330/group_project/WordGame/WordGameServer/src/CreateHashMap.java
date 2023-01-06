// CreateHashMap
// french textFile from 51413resu originally

import java.io.*;
import java.util.HashMap;

/**
 * This class reads in the txt file and fills the hashMap with the string and number of letters in it
 * */
public class CreateHashMap {
    private final HashMap<String, Integer> textFile = new HashMap<>();
    private static boolean upDateDone = false;

    public CreateHashMap(String languageFileName){
        try{
            BufferedReader input = new BufferedReader(new FileReader(languageFileName));

            /* While loop that gets the word and puts it in the hash map and gets the number of letters and puts it
             * as the key value */
            String word;
            int i = 0;
            while((word = input.readLine()) != null){
                textFile.put(word.toUpperCase(), i);
                i++;
            }

            input.close();
        } catch(Exception e) {
            System.out.println("Incorrect path has been entered.");
        }
    }

    public HashMap<String, Integer> getTextFile() {
        return textFile;
    }
}
