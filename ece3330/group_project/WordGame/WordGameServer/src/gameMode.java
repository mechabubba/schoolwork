// gameMode.java

import java.util.Map.Entry;
import java.util.HashMap;
public abstract class gameMode {

    /* Instance Variables */
    String gameType, gameRules, gameLanguage;
    int numOfPlayers;
    public scoringPoints[] scoring;

    /* HashMap that holds all the game stats. The key tracks the player and the HashMap holds the word that the
    * player entered and the points achieved from the word. */
    private HashMap<Integer, HashMap> statsForGame = new HashMap<>();

    /* HashMap that can read the text file in */
    public static HashMap<String, Integer> wordsMap;

    public void initializeHashMap(HashMap<String, Integer> textFile){
        wordsMap = (HashMap<String, Integer>) textFile.clone();

        int i = 1;
        while(i <= numOfPlayers){
            HashMap<String, scoringPoints> statsForNewPlayer = new HashMap<>();
            statsForGame.put(i,statsForNewPlayer);
            i++;
        }
    }

    /**
     * Returns the game stats hashmap.
     * @return The game stats hashmap.
     */
    public HashMap<Integer, HashMap> getGameStats() {
        return this.statsForGame;
    }

    /** This method checks if the word is correct and returns a string stating if the word is found or not. If the
     * word is found the findScoringPoints method is called. */
    public String findWord(String word, int player){
        word = word.toUpperCase();
        HashMap statsForPlayer = statsForGame.computeIfAbsent(player, k -> new HashMap<String, scoringPoints>());

        if(statsForPlayer.containsKey(word)){
            return "Already Used";
        } else if(wordsMap.containsKey(word)){
            findScoringPoints(word, player);
            return "Word Found";
        }else {
            statsForPlayer.put(word, new scoringPoints("Incorrect Spelling: -1", -1));
            statsForGame.put(player, statsForGame.get(player));
            return "Incorrect Word\n -1 points";
        }
    }

    /** Abstract method that every subclasses needs.*/
    public abstract void findScoringPoints(String word, int player);

    /** This method adds the word and the points for the word into the statsForRound and updates the
     * total points. */
    public void addStats(scoringPoints word, String inputWord, int player){
        HashMap<String, scoringPoints> statsForPlayer = statsForGame.get(player);
        statsForPlayer.put(inputWord, word);
    }

    /** This method uses stringBuilder and printPlayerStats to get all the game stats and returns it.*/
    public String printGameStats(){
        StringBuilder gameResults = new StringBuilder();

        for(Entry<Integer, HashMap> entry: statsForGame.entrySet()) {
            String string2 = String.format("%s", getPlayerStats(entry.getKey()));
            gameResults.append(string2).append("\n");
        }

        return gameResults.toString();
    }

    /** This method uses stringBuilder to get the results for one player and then returns a string with all the
     * information. */
    private String printPlayerStats(HashMap<String, scoringPoints> stats){
        StringBuilder playerResults = new StringBuilder();

        for(Entry<String, scoringPoints> entry: stats.entrySet()) {
            String string1 = String.format("%-10s", entry.getKey());
            String string2 = String.format("%s", entry.getValue().getNameOfMethod());
            playerResults.append(string1).append(" ").append(string2).append("\n");
        }

        return playerResults.toString();
    }

    /* Getters and Setters section */
    public String getGameRules() {
        return gameRules;
    }

    public String getGameType() {
        return gameType;
    }

    public String getGameLanguage(){ return gameLanguage; }

    public int getHashMapSize(){ return wordsMap.size();}

    /** Given the player this method returns that individual players stats. */
    public String getPlayerStats(int player){
        if(statsForGame.get(player) == null){
            return "No input given";
        }
        return "Player:" + player + "\n" + printPlayerStats(statsForGame.get(player));
    }

    public int getPlayerPoints(int player){
        try{
            HashMap<String, scoringPoints> stats = statsForGame.get(player);
            int totalPoints = 0;

            for(Entry<String, scoringPoints> entry: stats.entrySet()) {
                totalPoints += entry.getValue().getPoint();
            }
            return totalPoints;
        } catch(NullPointerException e){
            return -999;
        }
    }
}
