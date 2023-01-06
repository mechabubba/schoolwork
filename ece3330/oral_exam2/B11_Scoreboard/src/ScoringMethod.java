/**
 * This class represents a scoring method, which is used to modify a teams score.
 */
public class ScoringMethod {
    private final String name;
    private final int modifier;

    /**
     * Constructs a scoring method.
     * @param name The name of the scoring method.
     * @param modifier What to modify the teams points by when applied.
     */
    public ScoringMethod(String name, int modifier) {
        this.name = name;
        this.modifier = modifier;
    }

    /**
     * Gets the name of the scoring method.
     * @return The scoring methods name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the modifier of the scoring method.
     * @return The scoring methods point modifier.
     */
    public int getModifier() {
        return this.modifier;
    }
}
