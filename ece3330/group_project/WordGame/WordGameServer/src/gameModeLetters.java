// gameModeLetters.java

/**
 * This is a subclass of gameMode. This mode counts points for every letter in word
 * */
public class gameModeLetters extends gameMode{

    /* An array that holds all the ways to score points for the letters mode*/
    scoringPoints[] scoringForLetters = {
            new scoringPoints("One-Letter   Word: 1 point", 1),
            new scoringPoints("Two-Letter   Word: 2 points", 2),
            new scoringPoints("Three-Letter Word: 3 points", 3),
            new scoringPoints("Four-Letter  Word: 4 points", 4),
            new scoringPoints("Five-Letter  Word: 5 points", 5),
            new scoringPoints("Six-Letter   Word: 6 points", 6),
            new scoringPoints("Seven-Letter Word: 7 points", 7),
            new scoringPoints("Eight-Letter Word: 8 points", 8),
            new scoringPoints("Nine-Letter  Word: 9 points", 9),
            new scoringPoints("Ten-Letter   Word: 10 points", 10),
    };

    /** The constructor that sets the instance variables in the super class and class a method (initializeHashMap)
     * in the supper which will take the name of the textFile to fill the static hashMap with the correct language.*/
    public gameModeLetters(String language, int numPlayers) {
        super.gameType = "Letters Mode";
        super.gameRules = "For every letter given 1 point is added to the score" +
                "\nIf a word has been spelled incorrectly then 1 point will be deducted" +
                "\nIf a word has already been entered no point is given.";
        super.scoring = scoringForLetters;
        super.gameLanguage = language;
        super.numOfPlayers = numOfPlayers;

        if(language.compareTo("ENGLISH") == 0){
            initializeHashMap(new CreateHashMap("words_alpha.txt").getTextFile());
        } else if(language.compareTo("ITALIAN") == 0) {
            initializeHashMap(new CreateHashMap("words_italian.txt").getTextFile());
        } else {
            initializeHashMap(new CreateHashMap("words_french.txt").getTextFile());
        }
    }

    /** This method sends the word and the correct scoringPoints to the addStats */
    public void findScoringPoints(String word, int player){
        addStats(scoringForLetters[word.length() -1], word, player);
    }

    /* Returns the array of scoringPoints */
    public scoringPoints[] getScoringPoints(){
        return scoringForLetters;
    }
}
