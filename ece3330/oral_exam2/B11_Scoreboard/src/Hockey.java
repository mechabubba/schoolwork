/**
 * This class extends Game to represent a Hockey game.
 */
public class Hockey extends Game {
    // A list of scoring methods in hockey.
    private static final ScoringMethod[] scoring = {
        new ScoringMethod("goal", 1)
    };

    /**
     * Constructs a hockey game.
     * @param home The first "home" team.
     * @param away The second "away" team.
     */
    public Hockey(Team home, Team away) {
        super(home, away);
        this.setPeriodName("period");
        this.setPeriodAmount(3);
        for(ScoringMethod method : scoring) {
            this.addScoringMethod(method);
        }
    }
}
