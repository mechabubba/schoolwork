/**
 * This class represents an entry in the games' history, including information that would be useful to see in a replay.
 */
public class ScoringHistoryEntry {
    private final String teamName;
    private final ScoringMethod scoringMethod;
    private final int period;

    /**
     * Constructs a scoring history entry.
     * @param teamName The teams name.
     * @param scoringMethod The scoring method which the team used.
     * @param period The period in which this happened.
     */
    public ScoringHistoryEntry(String teamName, ScoringMethod scoringMethod, int period) {
        this.teamName = teamName;
        this.scoringMethod = scoringMethod;
        this.period = period;
    }

    /**
     * Gets the teams name.
     * @return The teams name.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Gets the scoring method the team used.
     * @return The scoring method.
     */
    public ScoringMethod getScoringMethod() {
        return scoringMethod;
    }

    /**
     * Gets the period in which the event happened.
     * @return The period.
     */
    public int getPeriod() {
        return period;
    }
}