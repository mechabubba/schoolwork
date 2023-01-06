import java.util.regex.Pattern;

/**
 * FourSquare is a class that represents a Four-Square cipher, allowing us to encode and decode text.
 */
public class FourSquare {
    private String keyword1;
    private String keyword2;
    final private CharacterMatrix[][] matrix;

    /**
     * Constructs a Four-Square cipher.
     * @param k1 The first keyword to encode in the top-right square.
     * @param k2 The second keyword to encode in the bottom-left square.
     */
    public FourSquare(String k1, String k2) {
        keyword1 = k1;
        keyword2 = k2;
        matrix = new CharacterMatrix[2][2];

        // Organized as you'd expect visually.
        matrix[0][0] = new CharacterMatrix();   // "Empty" CharacterMatrix which we search for our character in.
        matrix[0][1] = new CharacterMatrix(k1); // Encoded CharacterMatrix which we encrypt/decrypt our string in.
        matrix[1][0] = new CharacterMatrix(k2);
        matrix[1][1] = new CharacterMatrix();
    }

    /**
     * Resets the first keyword, and reinitializes the top-right CharacterMatrix.
     * @param word The word to set it as.
     */
    public void setKeyword1(String word) {
        keyword1 = word;
        matrix[0][1].initialize(keyword1);
    }

    /**
     * Resets the second keyword, and reinitializes the bottom-left CharacterMatrix.
     * @param word The word to set it as.
     */
    public void setKeyword2(String word) {
        keyword2 = word;
        matrix[1][0].initialize(keyword2);
    }

    /**
     * Gets a list of "digraphs" from our string; a list of two character sequences, which helps us encode our provided texts.
     * @param text The text to turn into digraphs.
     * @return An array of string digraphs.
     */
    private String[] getDigraphs(String text) {
        // First, clean our string of characters to only uppercase alphabetical letters. Using regex for this. https://stackoverflow.com/a/20096812
        Pattern alpha = Pattern.compile("[^A-Za-z]|[Qq]");
        text = alpha.matcher(text).replaceAll("").toUpperCase();

        // Next, form our array of digraphs.
        String[] digraphs = new String[(int)Math.floor(text.length() / 2)]; // not sure why math.floor returns a double here...
        int dg = 0;
        for(int i = 0; i < text.length(); i += 2) {
            if(i + 1 >= text.length()) break; // Break if the next character doesn't exist; this will cut the current character off.
            digraphs[dg] = text.substring(i, i + 2);
            dg++;
        }
        return digraphs;
    }

    /**
     * Encodes the provided input string.
     * @param input The text to encode.
     * @return An encoded string.
     */
    public String encode(String input) {
        String[] digraphs = getDigraphs(input);
        StringBuilder result = new StringBuilder();
        for (String digraph : digraphs) {
            int[] coord1 = matrix[0][0].find(digraph.charAt(0));   // Find the coordinates of the first letter in the first matrix.
            int[] coord2 = matrix[1][1].find(digraph.charAt(1));   // Find the coordinates of the second letter in the fourth matrix.
            result.append(matrix[0][1].get(coord1[0], coord2[1])); // Match the two coordinate systems to the second matrix.
            result.append(matrix[1][0].get(coord2[0], coord1[1])); // Match the two coordinate systems to the third matrix.
        }
        return result.toString();
    }

    /**
     * Decodes the provided input string.
     * @param input The text to decode.
     * @return A decoded string.
     */
    public String decode(String input) {
        String[] digraphs = getDigraphs(input);
        StringBuilder result = new StringBuilder();
        for(String digraph : digraphs) {
            int[] coord1 = matrix[0][1].find(digraph.charAt(0));
            int[] coord2 = matrix[1][0].find(digraph.charAt(1));
            result.append(matrix[0][0].get(coord1[0], coord2[1]));
            result.append(matrix[1][1].get(coord2[0], coord1[1]));
        }
        return result.toString();
    }
}
