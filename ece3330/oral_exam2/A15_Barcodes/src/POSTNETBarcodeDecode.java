import java.util.Scanner;

/**
 * Demonstration class to decode a binary-formatted POSTNET barcode into a zip code.
 */
public class POSTNETBarcodeDecode {
    /**
     * The main method.
     * @param args Provided command line arguments.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a barcode (in *binary* format!)");
        System.out.print("> ");
        String given = input.nextLine();

        ZipCode result = null;
        try {
            result = POSTNETBarcode.decode(given);
        } catch(Exception e) {
            System.out.println("Attempted to create a zipcode based on barcode, but failed!");
            System.out.println(e);
            System.exit(1);
        }

        System.out.println("Resulting zipcode;");
        System.out.println(result.toString());
    }
}
