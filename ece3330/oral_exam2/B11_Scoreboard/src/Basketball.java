/**
 * This class extends Game to represent a Basketball game.
 */
public class Basketball extends Game {
    // A list of scoring methods in basketball.
    private static final ScoringMethod[] scoring = {
        new ScoringMethod("two-pointer", 2),
        new ScoringMethod("three-pointer", 3),
        new ScoringMethod("free throw", 1)
    };

    /**
     * Constructs a basketball game.
     * @param home The first "home" team.
     * @param away The second "away" team.
     */
    public Basketball(Team home, Team away) {
        super(home, away);
        this.setPeriodName("half");
        this.setPeriodAmount(2);
        for(ScoringMethod method : scoring) {
            this.addScoringMethod(method);
        }
    }
}
