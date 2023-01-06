/**
 * Settings representation for the word game.
 */
public class WordGameSettings {
    private String language;
    private int time;
    private String gamemode;

    /**
     * Constructs a set of settings.
     */
    public WordGameSettings() {
        this("ENGLISH", 60, "WORD");
    }

    public WordGameSettings(String language, int time, String gamemode) {
        this.language = language;
        this.time = time;
        this.gamemode = gamemode;
    }

    /**
     * Gets the language used in the game.
     * @return The language used in the game.
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Sets the language used in the game.
     * @param language The language used in the game.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the time for the game.
     * @return The time for the game.
     */
    public int getTime() {
        return this.time;
    }

    /**
     * Sets the time for the game.
     * @param time The time for the game.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Gets the gamemode. Should be "WORD" or "LETTER".
     * @return The gamemode.
     */
    public String getGamemode() {
        return this.gamemode;
    }

    /**
     * Sets the gamemode. Should be either "WORD" or "LETTER".
     * @param gamemode The gamemode.
     */
    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }
}
