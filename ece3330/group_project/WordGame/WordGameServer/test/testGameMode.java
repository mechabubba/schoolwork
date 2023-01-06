// testGameMode.java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testGameMode {
    gameMode game;

    @Test
    void whichGameToPlay() {
        game = new gameModeLetters("FRENCH", 1);
        assertEquals(game.getGameType(), "Letters Mode");
        assertEquals(game.getGameRules(), "");
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.getGameType(), "Words Mode");
        assertEquals(game.getGameRules(), "");
    }

    /** Test to check which language is being used*/
    @ParameterizedTest // A method that allows the ability to run a test multiple times
    @CsvSource({"FRENCH", "FRENCH", "ENGLISH", "ENGLISH", "ITALIAN", "ITALIAN"})
    void whichLanguage(String input) {
        game = new gameModeLetters(input, 1);
        assertEquals(game.getGameLanguage(), input);
        game = new gameModeLetters(input, 1);
        assertEquals(game.getGameLanguage(), input);
    }

    @Test
    void wordMapInitialized(){
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.getHashMapSize(), new CreateHashMap("words_alpha.txt").getTextFile().size());
    }

    /** Test to make sure correct words are found */
    @ParameterizedTest // A method that allows the ability to run a test multiple times
    @CsvSource({"I", "Hi", "hurt", "nor", "FOR", "Love"})
    void testingFindWordCorrect(String input){
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.findWord(input, 1), "Word Found");
    }

    /** Test to make sure correct words are found in the French setting */
    @ParameterizedTest // A method that allows the ability to run a test multiple times
    @CsvSource({"Bien", "a", "comment", "oui", "tu", "adore"})
    void testingFindWordCorrectFrench(String input){
        game = new gameModeWords("FRENCH", 1);
        assertEquals(game.findWord(input, 1), "Word Found");
    }

    /** Test to make sure correct words are found in the Italian setting */
    @ParameterizedTest // A method that allows the ability to run a test multiple times
    @CsvSource({"amore", "amica", "io", "un", "tu", "me"})
    void testingFindWordCorrectItalian(String input){
        game = new gameModeWords("ITALIAN", 1);
        assertEquals(game.findWord(input, 1), "Word Found");
    }

    /** Test to make sure incorrect words are found */
    @ParameterizedTest // A method that allows the ability to run a test multiple times
    @CsvSource({"j", "r", "ofn", "wrrnlihg"})
    void testingFindWordIncorrect(String input){
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.findWord(input, 1), "Incorrect Word\n -1 points");
    }

    /** Test to make sure incorrect words are found in the French setting */
    @ParameterizedTest // A method that allows the ability to run a test multiple times
    @CsvSource({"j", "ran", "ofn", "wrrnlihg"})
    void testingFindWordIncorrectFrench(String input){
        game = new gameModeWords("FRENCH", 1);
        assertEquals(game.findWord(input, 1), "Incorrect Word\n -1 points");
    }
    /** Test to make sure incorrect words are found in the Italian setting */
    @ParameterizedTest // A method that allows the ability to run a test multiple times
    @CsvSource({"j", "ran", "friend", "often", "wrrnlihg"})
    void testingFindWordIncorrectItalian(String input){
        game = new gameModeWords("ITALIAN", 1);
        assertEquals(game.findWord(input, 1), "Incorrect Word\n -1 points");
    }

    /** Test to make sure words already used are found */
    @Test
    void testingFindWordAgain(){
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.findWord("i", 1), "Word Found");
        assertEquals(game.findWord("I",1 ), "Already Used");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.findWord("HI", 1), "Already Used");
    }

    /** Test to gameModeWords points are correct */
    @Test
    void testModeWordsPoints(){
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.findWord("input", 1), "Word Found");
        assertEquals(game.findWord("I", 1), "Word Found");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.getPlayerPoints(1), 3);
    }

    /** Test to gameModeLetters points are correctly given */
    @Test
    void testModeLettersPoints(){
        game = new gameModeLetters("ENGLISH", 1);
        assertEquals(game.findWord("into", 1), "Word Found");
        assertEquals(game.findWord("I", 1), "Word Found");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.getPlayerPoints(1), 7);
    }

    /** Test to gameModeLetters points are correctly given for the French setting */
    @Test
    void testModeLettersPointsFrench(){
        game = new gameModeLetters("FRENCH", 1);
        assertEquals(game.findWord("oui", 1), "Word Found");
        assertEquals(game.findWord("je", 1), "Word Found");
        assertEquals(game.findWord("nous", 1), "Word Found");
        assertEquals(game.getPlayerPoints(1), 9);
    }

    /** Test the printing of gameModeLetters */
    @Test
    void testModeLettersPrint(){
        game = new gameModeLetters("ENGLISH", 1);
        assertEquals(game.findWord("into", 1), "Word Found");
        assertEquals(game.findWord("I", 1), "Word Found");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.printGameStats(),
                "Player:1\n" +
                        "HI         Two-Letter   Word: 2 points\n" +
                        "INTO       Four-Letter  Word: 4 points\n" +
                        "I          One-Letter   Word: 1 point\n\n");
    }

    /** Test the printing of gameModeWords */
    @Test
    void testModeWordsPrint(){
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.findWord("input", 1), "Word Found");
        assertEquals(game.findWord("I", 1), "Word Found");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.printGameStats(),
                "Player:1\n" +
                "HI         Correct Word: 1 point\n" +
                "INPUT      Correct Word: 1 point\n" +
                "I          Correct Word: 1 point\n\n") ;
    }

    /** Test to see if points are deducted if a word is incorrect */
    @Test
    void testingIncorrectWordPoint(){
        game = new gameModeWords("ENGLISH", 1);
        assertEquals(game.findWord("r", 1), "Incorrect Word\n -1 points");
        assertEquals(game.findWord("vbw", 1), "Incorrect Word\n -1 points");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.findWord("HI", 1), "Already Used");
      //  assertEquals(game.getTotalPoints(), -1);
        assertEquals(game.printGameStats(),
                "Player:1\n" +
                "HI         Correct Word: 1 point\n" +
                "R          Incorrect Spelling: -1\n" +
                "VBW        Incorrect Spelling: -1\n\n") ;
    }

    /** Test to see if points are deducted if a word is incorrect for letters and checks the print.  */
    @Test
    void testingIncorrectWordPointLetters(){
        game = new gameModeLetters("ENGLISH", 1);
        assertEquals(game.findWord("r",1), "Incorrect Word\n -1 points");
        assertEquals(game.findWord("vbw", 1), "Incorrect Word\n -1 points");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.findWord("HI", 1), "Already Used");
        assertEquals(game.getPlayerPoints(1), 0);
        assertEquals(game.printGameStats(), "Player:1\n" +
                "HI         Two-Letter   Word: 2 points\n" +
                "R          Incorrect Spelling: -1\n" +
                "VBW        Incorrect Spelling: -1\n\n") ;
    }

    /** Test the printing of gameModeLetters for multiple players */
    @Test
    void testModeLettersPrintAllPlayers(){
        game = new gameModeLetters("ENGLISH", 2);
        assertEquals(game.findWord("into", 1), "Word Found");
        assertEquals(game.findWord("I", 2), "Word Found");
        assertEquals(game.findWord("hi", 1), "Word Found");
        assertEquals(game.printGameStats(),
                "Player:1\n" +
                        "HI         Two-Letter   Word: 2 points\n" +
                        "INTO       Four-Letter  Word: 4 points\n" +
                        "\n" +
                        "Player:2\n" +
                        "I          One-Letter   Word: 1 point\n\n");
    }

    /** Test the printing of gameModeLetters for multiple players */
//    @Test
//    void testPrintAllPlayersNull(){
//        game = new gameModeLetters("ENGLISH", 3);
//        assertEquals(game.findWord("into", 1), "Word Found");
//        assertEquals(game.findWord("I", 3), "Word Found");
//        assertEquals(game.findWord("hi", 1), "Word Found");
//        assertEquals(game.printGameStats(),
//                "Player:1\n" +
//                        "HI         Two-Letter   Word: 2 point(s)\n" +
//                        "INTO       Four-Letter  Word: 4 point(s)\n" +
//                        "\n" +
//                        "Player:2\n" +
//                        "I          One-Letter   Word: 1 point(s)\n\n");
//    }

    /** Test the printing of gameModeLetters for one player */
    @Test
    void testModeLettersPrintOnePlayer(){
        game = new gameModeLetters("ENGLISH", 2);
        game.findWord("into", 1);
        game.findWord("I", 2);
        game.findWord("hi", 1);
        assertEquals(game.getPlayerStats(2), "Player:2\n" +
                "I          One-Letter   Word: 1 point\n");

    }

    /** Test the printing if for one player if no input was given */
    @Test
    void testPrintOnePlayerNull(){
        game = new gameModeLetters("ENGLISH", 3);
        game.findWord("into", 1);
        game.findWord("I", 2);
        game.findWord("hi", 1);
        assertEquals(game.getPlayerStats(3), "No input given");
        assertEquals(game.getPlayerPoints(3), -999);
    }
}
