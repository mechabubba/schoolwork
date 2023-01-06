/**
 * This class extends Game to represent a Football game.
 */
public class Football extends Game {
    // A list of scoring methods in football.
    private static final ScoringMethod[] scoring = {
        new ScoringMethod("touchdown", 6),
        new ScoringMethod("field goal", 3),
        new ScoringMethod("extra-point", 1),
        new ScoringMethod("two-point conversion", 2),
        new ScoringMethod("safety", 2)
    };

    /**
     * Constructs a football game.
     * @param home The first "home" team.
     * @param away The second "away" team.
     */
    public Football(Team home, Team away) {
        super(home, away);
        this.setPeriodName("quarter");
        this.setPeriodAmount(4);
        for(ScoringMethod method : scoring) {
            this.addScoringMethod(method);
        }
    }
}
