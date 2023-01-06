// randomLetters.java

import java.io.*;
import java.util.HashMap;

/**
 * This class creates an array of char that holds random letters.
 * This class has to methods for creating an array of char that holds random letters. The first method is when a file
 * is given and the second is if no file is given.
 */

public class randomLetters {

    /* Instance Variables */
    final static char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    final static char[] vowels = {'A', 'E', 'I', 'O', 'U', 'Y'};
    public HashMap<Integer, String> wordsFromFile = new HashMap<>(); // Holds all the words from the textfile
    public int numOfChar = 4; // Number of letters in the string
    char[] randomWord;
    boolean generateScramble = false;

    /**
     * This method sets the randomWord if given a textFile. The method readInFile is called, if the path is not
     * correct then the setRandomWord() method that creates a generic four-letter word is called. If the path is
     * correct then Math.random is used to randomly generate a number to decide which word from the file is used.
     */
    public void setRandomWord(String textFile) {
        readInFile(textFile);

        if (generateScramble)
            setRandomWord();
        else {
            int whichWord = (int) (Math.random() * wordsFromFile.size());
            String word = wordsFromFile.get(whichWord);
            numOfChar = word.length();

            randomWord = new char[numOfChar];

            for (int i = 0; i < numOfChar; i++) {
                randomWord[i] = word.charAt(i);
            }
        }
    }

    /**
     * This method uses a try-catch block to verify that the textFile is correct. If correct the file fills
     * a hashMap with the strings from the file.
     */
    private void readInFile(String textFile) {
        try {
            BufferedReader input = new BufferedReader(new FileReader(textFile));

            String word;
            int i = 0;
            while ((word = input.readLine()) != null) {
                wordsFromFile.put(i, word.toUpperCase());
                i++;
            }

            input.close();
        } catch (Exception e) {
            generateScramble = true;
            System.out.println("Incorrect path has been entered.\nA four letter scramble has been generated.");
        }
    }

    /**
     * This method is used for testing the array to see if it was filled correctly.
     */
    public String printOutTheArray() {
        StringBuilder printArray = new StringBuilder();

        for (char temp : randomWord) {
            printArray.append(temp).append("\n");
        }

        return printArray.toString();
    }

    /**
     * This method creates an array of char that is filled by randomly selecting a char form alphabet and
     * one char from vowels to ensure that there is a vowel and then returns the array.
     */
    public void setRandomWord() {
        randomWord = new char[numOfChar];

        for (int i = 0; i < numOfChar - 1; i++) {
            int newKey = (int) (Math.random() * 25);
            randomWord[i] = alphabet[newKey];
        }

        randomWord[numOfChar - 1] = vowels[(int) (Math.random() * 5)];
    }


    /* Setters and Getters for numOfCar*/
    public int getNumOfChar() {
        return numOfChar;
    }

    public void setNumOfChar(int numOfChar) {
        this.numOfChar = numOfChar;
    }

    public char[] getRandomWord() {
        return randomWord;
    }
}

