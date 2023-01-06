/**
 * This class extends Game to represent a Baseball game.
 */
public class Baseball extends Game {
    // A list of scoring methods in baseball.
    private static final ScoringMethod[] scoring = {
        new ScoringMethod("run", 1),
        new ScoringMethod("two runs", 2),
        new ScoringMethod("three runs", 3),
        new ScoringMethod("grand slam", 4) // Same as above, but I'd say its distinct enough to warrant its own scoring method...
    };

    /**
     * Constructs a baseball game.
     * @param home The first "home" team.
     * @param away The second "away" team.
     */
    public Baseball(Team home, Team away) {
        super(home, away);
        this.setPeriodName("inning");
        this.setPeriodAmount(9);
        for(ScoringMethod method : scoring) {
            this.addScoringMethod(method);
        }
    }
}
