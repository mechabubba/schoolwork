import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a list of elements from the postfix equation.
 * <p>
 * Each element is either a number symbol or an operator symbol, and are retrieved one at a time through the <code>getNext()</code> method.
 */
public class ElementList {
    private final ArrayList<String> strings;
    private final ArrayList<Symbol> symbols;
    private int el;

    /**
     * Constructs an element list, one element at a time.
     * @param input The postfix equation. Note that it is required for elements in the equation to be delimited by a space.
     * @throws Exception Can throw in two specific situations; if it encounters a symbol it doesn't know what to do with, or if it is unable to parse a number for some reason.
     */
    public ElementList(String input) throws Exception {
        this.strings = new ArrayList<>(List.of(input.split(" "))); // Split string by spaces.
        this.symbols = new ArrayList<>();
        this.el = 0;

        Pattern numeric = Pattern.compile("(-?\\d+)"); // Match numeric sequences only.
        for(int i = 0; i < this.strings.size(); i++) {
            String val = this.strings.get(i);
            try {
                Matcher matcher = numeric.matcher(val);
                boolean found = matcher.find();
                Symbol sym;
                if(found) {
                    // It's a number; attempt to parse it as such.
                    sym = new NumberSymbol(val);
                } else {
                    // It's not a number; attempt to parse it as an operator.
                    sym = new OperatorSymbol(val);
                }
                this.symbols.add(sym);
            } catch(NumberFormatException e) {
                // We were dealing with a number, and there was an overflow. Pass down the exception.
                // In this case, I rethrow the error as it includes information of where it threw (and there's no constructor for the class that includes a second "Throwable" parameter).
                throw e;
            } catch(OperatorException e) {
                // We were dealing with an operator, and there was some sort of general error.
                throw new OperatorException(String.format("Operator error whilst dealing with element %s (%s) of postfix evaluation.", i, strings.get(i)), e);
            }
        }
        this.symbols.add(new OperatorSymbol(")")); // The list may contain one end statement before this; if it doesn't, add an extra at the end just incase.
    }

    /**
     * Gets the next value in the equation.
     * @return The next value in the equation.
     */
    public Symbol getNext() {
        return symbols.get(el++);
    }

    /**
     * Determines if another element exists in the list.
     * @return Whether another element exists in the list.
     */
    public boolean hasNext() {
        return !(el >= symbols.size());
    }
}
