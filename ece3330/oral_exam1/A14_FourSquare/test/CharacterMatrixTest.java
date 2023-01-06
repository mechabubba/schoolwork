import org.junit.jupiter.api.Test;

public class CharacterMatrixTest {
    @Test
    public void matrixTest() {
        CharacterMatrix mat = new CharacterMatrix();
        mat.display(); // Should print the alphabet in order (minus Q).
    }

    @Test
    public void matrixTestKeyword() {
        CharacterMatrix mat = new CharacterMatrix("EXAMPLE");
        mat.display(); // Should display as it does on the project page; note there should be no repeating characters anywhere in the matrix.
    }

    @Test
    public void matrixTestKeywordWithQ() {
        CharacterMatrix mat = new CharacterMatrix("QUERY");
        mat.display(); // Only thing to note is that the Q should not be there.
    }
}
