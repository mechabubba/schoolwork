import java.util.Scanner;

/**
 * Demonstration class to decode a binary-formatted UPC-A barcode into a UPC.
 */
public class UPCABarcodeDecode {
    /**
     * The main method.
     * @param args Provided command line arguments.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a UPC barcode;");
        System.out.print("> ");
        String given = input.nextLine();

        String result = null;
        try {
            result = UPCABarcode.decode(given);
        } catch(Exception e) {
            System.out.println("Attempted to decode a UPC barcode, but failed!");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Resulting UPC;");
        System.out.println(result);
    }
}
