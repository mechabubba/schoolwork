/**
 * This class extends Game to represent a Soccer game.
 */
public class Soccer extends Game {
    // A list of scoring methods in soccer.
    private static final ScoringMethod[] scoring = {
        new ScoringMethod("goal", 1)
    };

    /**
     * Constructs a soccer game.
     * @param home The first "home" team.
     * @param away The second "away" team.
     */
    public Soccer(Team home, Team away) {
        super(home, away);
        this.setPeriodName("half");
        this.setPeriodAmount(2);
        for(ScoringMethod method : scoring) {
            this.addScoringMethod(method);
        }
    }
}
