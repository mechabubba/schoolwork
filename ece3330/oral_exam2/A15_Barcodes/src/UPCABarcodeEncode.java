import java.util.Scanner;

/**
 * Demonstration class to encode a UPC into a UPC-A barcode.
 */
public class UPCABarcodeEncode {
    /**
     * The main method.
     * @param args Provided command line arguments.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter an 11-digit UPC code;");
        System.out.print("> ");
        String given = input.nextLine();

        String result = UPCABarcode.encode(given);

        System.out.println("Resulting UPC barcode;");
        System.out.println(result);
    }
}
