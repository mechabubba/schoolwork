import java.util.Stack;

/**
 * This class does the evaluation of a postfix equation.
 */
public class PostFixEvaluator {
    private final Stack<Integer> stack;

    /**
     * Constructs a PostFixEvaluator and instantiates the stack.
     */
    public PostFixEvaluator() {
        stack = new Stack<>();
    }

    /**
     * Evaluates a postfix equation.
     * @param input The postfix equation to evaluate
     * @return The integer result.
     * @throws Exception Throws under numerous circumstances; namely, symbolizing the input and making the calculation.
     */
    public int evaluatePostfixExpression(String input) throws Exception {
        // Clear the stack, in case garbage is in there from earlier.
        stack.clear();

        // Create a list of elements of the equation.
        ElementList elements = new ElementList(input);

        // Go through the elements one by one.
        while(elements.hasNext()) {
            Symbol sym = elements.getNext();
            if(sym instanceof NumberSymbol) {
                NumberSymbol number = (NumberSymbol)sym;
                stack.push(number.get());
            } else if(sym instanceof OperatorSymbol) {
                OperatorSymbol operator = (OperatorSymbol)sym;
                if(operator.get() == Symbol.OpType.END) {
                    break;
                }

                int x, y;
                if(operator.get() == Symbol.OpType.SUBTRACTION
                    || operator.get() == Symbol.OpType.DIVISION
                    || operator.get() == Symbol.OpType.MODULATION) {
                    // Subtraction and division operators have their values reversed.
                    x = stack.pop();
                    y = stack.pop();
                } else {
                    y = stack.pop();
                    x = stack.pop();
                }

                // Calculate, and push onto stack.
                try {
                    int result = calculate(x, operator.get(), y);
                    stack.push(result);
                } catch(ArithmaticException e) {
                    throw new ArithmaticException(String.format("Error occurred calculating %s %s %s.", y, operator.get().name(), x), e);
                }
            }
        }

        // The top of the stack is the solution of the postfix equation, so pop once and return that value.
        return stack.pop();
    }

    /**
     * Calculates a number based on the x, y, and provided operator. Note that since we're dealing with ints, this will truncate the resulting answer.
     * @param x Right side of equation.
     * @param op The operator.
     * @param y Left side of equation.
     * @return The answer to our calculation.
     * @throws ArithmaticException Thrown only if it tries to do something it shouldn't, such as divide by zero.
     */
    public int calculate(int x, Symbol.OpType op, int y) throws ArithmaticException {
        switch(op) {
            case ADDITION: return y + x;
            case SUBTRACTION: return y - x;
            case MULTIPLICATION: return y * x;
            case DIVISION: {
                if(x == 0) {
                    throw new ArithmaticException("Attempted to divide by zero.");
                }
                return y / x;
            }
            case EXPONENTIATION: return (int) Math.pow(x, y);
            case MODULATION: return y % x;
            default: {
                throw new ArithmaticException("Invalid operation provided."); // As the operator types are forced via an enum, this should never happen.
            }
        }
    }
}
