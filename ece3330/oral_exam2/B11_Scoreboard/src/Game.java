import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The Game class holds all the generic information corresponding to any "game". This includes period information, game state (in progress? finished? etc...), the teams playing, a history of the games plays, and so on.
 */
public class Game {
    private Team team1;
    private Team team2;
    private GameState state;

    private String periodName;
    private int periodAmount;
    private int periodNumber;
    private final ArrayList<ScoringMethod> scoringMethods;

    // A historical account of all plays during the game.
    private final LinkedList<ScoringHistoryEntry> history;

    /**
     * An enum representing the various states of a game.
     */
    public enum GameState {
        NOT_STARTED,
        IN_PROGRESS,
        FINISHED
    }

    /**
     * Constructs a game from two teams.
     * @param team1 The first "home" team.
     * @param team2 The second "away" team.
     */
    public Game(Team team1, Team team2) {
        this.history = new LinkedList<>();
        this.scoringMethods = new ArrayList<>();
        this.setTeam1(team1);
        this.setTeam2(team2);
        this.setState(GameState.NOT_STARTED);
    }

    /**
     * Gets the first "home" team.
     * @return The first team.
     */
    public Team getTeam1() {
        return this.team1;
    }

    /**
     * Gets the second "away" team.
     * @return The second team.
     */
    public Team getTeam2() {
        return this.team2;
    }

    /**
     * Sets the first "home" team.
     * @param team The first team.
     */
    public void setTeam1(Team team) {
        this.team1 = team;
    }

    /**
     * Sets the second "away" team.
     * @param team The second team.
     */
    public void setTeam2(Team team) {
        this.team2 = team;
    }

    /**
     * Gets the state of the game.
     * @return An enum representing the games state.
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the state of the game.
     * @param state An enum representing the games state.
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * Start the game; this just sets the game's state to <code>IN_PROGRESS</code>.
     */
    public void start() {
        this.setState(GameState.IN_PROGRESS);
    }

    /**
     * Gets the name of the games period.
     * @return The name of the games period.
     */
    public String getPeriodName() {
        return this.periodName;
    }

    /**
     * Sets the name of the games period.
     * @param name The name of the games period.
     */
    public void setPeriodName(String name) {
        this.periodName = name;
    }

    /**
     * Gets the amount of periods in one game.
     * @return The amount of periods in one game.
     */
    public int getPeriodAmount() {
        return this.periodAmount;
    }

    /**
     * Sets the amount of periods in one game.
     * @param amount The amount of periods in one game.
     */
    public void setPeriodAmount(int amount) {
        this.periodAmount = amount;
    }

    /**
     * Gets the games current period number.
     * @return The games current period number.
     */
    public int getPeriodNumber() {
        return this.periodNumber;
    }

    /**
     * Sets the games current period number.
     * @param num The games current period number.
     */
    public void setPeriodNumber(int num) {
        this.periodNumber = num;
    }

    /**
     * Modifies the score for a given team.
     * @param team The team to add the score to.
     * @param scoring The ScoringMethod that represents how the teams score should change.
     */
    public void addScore(Team team, ScoringMethod scoring) {
        team.setScore(team.getScore() + scoring.getModifier());
        history.add(new ScoringHistoryEntry(team.getName(), scoring, this.getPeriodNumber()));
    }

    /**
     * Adds a ScoringMethod to the list of scoring methods.
     * @param scoring The ScoringMethod to add.
     */
    public void addScoringMethod(ScoringMethod scoring) {
        scoringMethods.add(scoring);
    }

    /**
     * Gets the list of scoring methods.
     * @return The list of scoring methods.
     */
    public ArrayList<ScoringMethod> getScoringMethods() {
        return this.scoringMethods;
    }

    /**
     * Gets a list representing the games' history, or all the plays that occurred during the game.
     * @return A list representing the games' history.
     */
    public LinkedList<ScoringHistoryEntry> getHistory() {
        return this.history;
    }

    /**
     * Adds a ScoringHistoryEntry to the scoring history.
     * @param entry The entry to add to the history.
     */
    public void addHistoryEntry(ScoringHistoryEntry entry) {
        this.history.add(entry);
    }

    /**
     * Determines if the game is over; that is, determines if the period number is equal to or greater than the games period amount.
     * @return Whether the game is finished or not.
     */
    public boolean isFinished() {
        return this.getPeriodNumber() >= this.getPeriodAmount();
    }
}
