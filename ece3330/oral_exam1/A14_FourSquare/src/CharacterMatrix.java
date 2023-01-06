import java.util.regex.Pattern;

/**
 * The CharacterMatrix class lets us easily interact with a (forced size) 5x5 matrix.
 */
public class CharacterMatrix {
    private char[][] grid;

    /**
     * Constructs an empty CharacterMatrix.
     */
    public CharacterMatrix() {
        initialize("");
    }

    /**
     * Constructs a CharacterMatrix encoded with a piece of text.
     * @param word The text to encode.
     */
    public CharacterMatrix(String word) {
        initialize(word);
    }

    /**
     * Initializes our CharacterMatrix with provided text (if any).
     * @param word The text to be encoded.
     */
    public void initialize(String word) {
        grid = new char[5][5];

        // First, clean our word of characters other than alphabetical ones. Using regex for this. https://stackoverflow.com/a/20096812
        Pattern alpha = Pattern.compile("[^A-Za-z]|[Qq]");
        word = alpha.matcher(word).replaceAll("").toUpperCase();

        // Encode the word inside our grid.
        for(int i = 0; i < word.length(); i++) {
            if(has(word.charAt(i))) continue; // No duplicate characters anywhere here.
            add(word.charAt(i));
        }

        // Place the rest of the letters in; we start at the end of our word.
        int letter = 65;
        for(int i = 0; i <= 25; i++) {
            char c = (char)letter++;
            if(c == 'Q') continue; // The Four-Square Cipher does not include Q in the square, so to fit the whole alphabet.
            if(has(c)) continue;   // No duplicates!
            add(c);
        }
    }

    /**
     * Adds a character to our matrix.
     * The matrix is added to from left to right, top to bottom; we don't need to put too much thought into where this goes.
     * @param c The character to add.
     * @throws ArrayIndexOutOfBoundsException Only throws if you add greater than 25 characters.
     */
    private void add(char c) throws ArrayIndexOutOfBoundsException {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == '\0') {
                    grid[i][j] = c;
                    return;
                }
            }
        }
    }

    /**
     * Finds a character within our matrix.
     * @param c The character to find.
     * @return A set of "coordinates" (y, x) that tell the developer where it is, or if it doesn't exist.
     */
    public int[] find(char c) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(c == grid[i][j]) return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Checks if the character exists in the matrix.
     * @param c The character to check.
     * @return A boolean whether it exists or not.
     */
    public boolean has(char c) {
        int[] coord = find(c);
        return coord[0] != -1;
    }

    /**
     * Gets a raw character from the matrix.
     * @param y The y coordinate (the specific char[]).
     * @param x The x coordinate (the specific char within the char[]).
     * @return The char at the given coordinates.
     * @throws ArrayIndexOutOfBoundsException Throws if you attempt to get a character out of bounds of the matrix.
     */
    public char get(int y, int x) throws ArrayIndexOutOfBoundsException {
        return grid[y][x];
    }

    /**
     * Displays our grid in text form.
     */
    public void display() {
        for (char[] chars : grid) {
            for (int j = 0; j < chars.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
    }
}
