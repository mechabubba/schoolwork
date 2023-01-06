import java.util.Scanner;

/**
 * Demonstration class to encode a zip code into a POSTNET barcode.
 */
public class POSTNETBarcodeEncode {
    /**
     * The main method.
     * @param args Provided command line arguments.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a zip code (in XXXXX-YYYY-ZZ format!)");
        System.out.print("> ");
        String given = input.nextLine();

        ZipCode zipcode = null;
        try {
            zipcode = new ZipCode(given);
        } catch(Exception e) {
            System.out.println("Attempted to create a zipcode based on input, but failed!");
            System.out.println(e);
            System.exit(1);
        }

        String result = POSTNETBarcode.encode(zipcode);
        System.out.println("Resulting barcode;");
        System.out.println(result);
        System.out.println(getVisualBarcode(result));
    }

    /**
     * Gets a visual representation of the barcode, or what's typically seen on packaging as the full and half bars that represent your zip code.
     * @param input The binary-formatter barcode.
     * @return A visual representation of that barcode.
     */
    public static String getVisualBarcode(String input) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            builder.append(input.charAt(i) == '1' ? "|" : ".");
        }
        return builder.toString();
    }

}
