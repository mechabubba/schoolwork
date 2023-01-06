// testRandomLetters.java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testRandomLetters {

    @Test
    void randomLettersCheckLength() {
        randomLetters word = new randomLetters();
        word.setRandomWord();
        assertEquals(word.getRandomWord().length, word.getNumOfChar());
    }

    /** Tests if the array length is the same as the numOfChar */
    @Test
    void numOfCarCheckLength() {
        randomLetters word = new randomLetters();
        word.setNumOfChar(7);
        word.setRandomWord();
        assertEquals(word.getRandomWord().length, word.getNumOfChar());
    }

    /** Tests if the array length is the same as the numOfChar */
    @Test
    void testFile()  {
        randomLetters word = new randomLetters();
        word.setRandomWord("scrambleWord.txt");
        assertEquals(word.printOutTheArray(), "r\n" +
                "t\n" +
                "u\n" +
                "v\n");
    }


    /** Tests if a four-letter word is generated when the textFile is incorrect. */
    @Test
    void printWord() {
        randomLetters word = new randomLetters();
        word.setRandomWord("scrambleWord123.txt");
        assertEquals(word.getRandomWord().length, 4);
    }
}
