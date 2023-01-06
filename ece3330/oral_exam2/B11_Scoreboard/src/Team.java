/**
 * The team class represents one of the teams in a game. This class controls the name *and score* of a team.
 */
public class Team {
    private String name;
    private int score;

    /**
     * Constructs a team.
     * @param name The teams name.
     */
    public Team(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Gets the teams name.
     * @return The teams name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the teams name.
     * @param name The teams name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the teams score.
     * @return The teams score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the teams score.
     * @param score The teams new score.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
