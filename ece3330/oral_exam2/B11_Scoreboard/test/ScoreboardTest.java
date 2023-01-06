import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    @Test
    public void testScoreChange() {
        Team hawkeyes = new Team("Hawkeyes");
        Team cyclones = new Team("Cyclones");
        Game hyveeCyHawk = new Football(hawkeyes, cyclones);

        ScoringMethod touchdown = hyveeCyHawk.getScoringMethods().get(0);
        hyveeCyHawk.addScore(hawkeyes, touchdown);
        hyveeCyHawk.addScore(cyclones, touchdown);
        assertEquals(hawkeyes.getScore(), cyclones.getScore());
    }
}
