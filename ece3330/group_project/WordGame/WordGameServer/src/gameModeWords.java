// gameModeWords.java

/**
 * This is a subclass of gameMode. This mode counts points for every word
 * */
public class gameModeWords extends gameMode{

    /* An array that holds all the ways to score points for the word mode*/
    scoringPoints[] scoringForWord = {
            new scoringPoints("Correct Word: 1 point", 1),
    };

    /** The constructor that sets the instance variables in the super class and class a method (initializeHashMap)
     * in the supper which will take the name of the textFile to fill the static hashMap with the correct language.*/
    public gameModeWords(String language, int numOfPlayers){
        super.gameType = "Words Mode";
        super.gameRules = "For every word given 1 point is added to the score" +
                "\nIf a word has been spelled incorrectly then 1 point will be deducted" +
                "\nIf a word has already been entered no point is given.";
        super.scoring = scoringForWord;
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
        addStats(scoringForWord[0], word, player);
    }

    /* Returns the array of scoringPoints */
    public scoringPoints[] getScoringPoints(){
        return scoringForWord;
    }

}
