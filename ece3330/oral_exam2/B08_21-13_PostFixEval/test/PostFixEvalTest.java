import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * These values were calculated online using this tool; https://raj457036.github.io/Simple-Tools/prefixAndPostfixEvaluator.html
 * For calculations, values must be delimited by a space.
 */
public class PostFixEvalTest {
    @Test
    public void pfTextbookExample() throws Exception {
        PostFixEvaluator pf = new PostFixEvaluator();
        int res = pf.evaluatePostfixExpression("6 2 + 5 * 8 4 / -");
        assertEquals(res, 38);
    }

    @Test
    public void pfLargeNumberExample() throws Exception {
        PostFixEvaluator pf = new PostFixEvaluator();
        int res = pf.evaluatePostfixExpression("103 86 * 56 + 1237 - 100 +");
        assertEquals(res, 7777);
    }

    @Test
    public void pfNegativeNumberExample() throws Exception {
        PostFixEvaluator pf = new PostFixEvaluator();
        int res = pf.evaluatePostfixExpression("4 -2 + 8 * 6 - 7 %");
        assertEquals(res, 3);
    }

    @Test
    public void pfBothExample() throws Exception {
        PostFixEvaluator pf = new PostFixEvaluator();
        int res = pf.evaluatePostfixExpression("153817 321 * -1 * 49375257 + 25 + 5 / 4 ^");
        assertEquals(res, 625);
    }

    /**
     * These two tests see if the right exception is thrown.
     * Unfortunately, the only way I could seem to do this was with lambdas, which (as of writing) we have not yet covered.
     * This StackOverflow answer showed me the way to do this; https://stackoverflow.com/a/40268447
     */
    @Test
    public void pfIntegerOverflowError() throws Exception {
        PostFixEvaluator pf = new PostFixEvaluator();
        assertThrows(NumberFormatException.class, () -> pf.evaluatePostfixExpression("6 123456789012345678901234567890123456789012345678901234567890 + 5 * 8 4 / -")); // Should throw at that massive number that definitely overflows the integer datatype.
    }

    @Test
    public void pfOperatorError() throws Exception {
        PostFixEvaluator pf = new PostFixEvaluator();
        assertThrows(OperatorException.class, () -> pf.evaluatePostfixExpression("6 2 + 5 $ 8 4 N -")); // Should throw at the '$'.
    }

    @Test
    public void pfDivideByZeroError() throws Exception {
        PostFixEvaluator pf = new PostFixEvaluator();
        assertThrows(ArithmaticException.class, () -> pf.evaluatePostfixExpression("5 0 / + 3 - 4")); // Should throw at the division by zero.
    }
}
