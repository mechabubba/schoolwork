import java.util.Arrays;
import java.util.List;

/**
 * This class includes methods to encode and decode zip codes to POSTNET barcodes, and vice versa.
 */
public class POSTNETBarcode {
    // A list of binary strings representing their index as their value.
    private static final List<String> numeralBinary = Arrays.asList(new String[]{
        "11000", "00011", "00101", "00110", "01001", "01010", "01100", "10001", "10010", "10100"
    });


    /**
     * Encodes a POSTNET barcode from the provided zip code.
     * @param zipcode The zip code to generate the barcode from.
     * @return A string representing the barcode of the zip code in binary format. The 0's and 1's are half and full bars, respectively.
     */
    public static String encode(ZipCode zipcode) {
        // Sum up the values in the string, find the checksum, and add it on.
        StringBuilder combined = new StringBuilder();
        combined.append(zipcode.getZipCode());
        combined.append(zipcode.getBcode());
        combined.append(zipcode.getDeliveryCode());

        // Find the checksum.
        int sum = 0;
        for(int i = 0; i < combined.length(); i++) {
            sum += Integer.parseInt(combined.substring(i, i + 1));
        }
        int check = (-(sum % 10) + 10) % 10; // Get amount needed to get the next value divisible by 10. The parenthesis yields a value [1, 10], so we take the remainder again to get the proper checksum.
        combined.append(String.valueOf(check)); // Add it onto the end of the combined string.
        String result = combined.toString();

        // Build our binary barcode.
        StringBuilder barcode = new StringBuilder("1"); // Append a "frame bar" to the left side.
        for(int i = 0; i < result.length(); i++) {
            int num = Integer.parseInt(result.substring(i, i + 1));
            barcode.append(numeralBinary.get(num));
        }
        barcode.append("1"); // Another frame bar for the right side.

        return barcode.toString(); // Done. :)
    }

    /**
     * Decodes a POSTNET barcode, and returns a *new* ZipCode which it represents.
     * @param input A string of binary 0's and 1's representing an encoded POSTNET barcode.
     * @return A zip code corresponding to said barcode.
     * @throws Exception Throws if any issues occur during the decoding process.
     */
    public static ZipCode decode(String input) throws Exception {
        // Any barcode with a length not divisible by 5 (minus the frame bars) is invalid.
        if((input.length() - 2) % 5 != 0) {
            throw new Exception("Invalid barcode provided; incorrect size.");
        }

        // Get the binary numerals.
        StringBuilder builder = new StringBuilder();
        int sum = 0;
        for(int i = 1; i < input.length() - 1; i += 5) {
            String numeral = input.substring(i, i + 5);
            int index = numeralBinary.indexOf(numeral);
            if(index < 0) {
                throw new Exception(String.format("Invalid barcode provided; garbage numeral \"%s\" found starting at index %d.", numeral, i));
            }
            builder.append(index);
            sum += index;
        }

        // If the sum of all the numerals (including the checksum) isn't divisible by 10, then we have an invalid checksum.
        if(sum % 10 != 0) {
            throw new Exception(String.format("Invalid barcode provided; checksum failed, got %d instead of 0.", sum % 10));
        }

        // Attempt to build a zipcode based on the resulting string.
        String result = builder.toString();
        if(result.length() == 6) {
            // Encoded a zipcode.
            return new ZipCode(result.substring(0, 5), "", "");
        } else if(result.length() == 10) {
            // Encoded a zipcode and a B code.
            return new ZipCode(result.substring(0, 5), result.substring(5, 9), "");
        } else if(result.length() == 12) {
            // Encoded a zipcode, B code, and delivery code.
            return new ZipCode(result.substring(0, 5), result.substring(5, 9), result.substring(9, 11));
        } else {
            throw new Exception("Invalid barcode provided; length does not match valid zip code.");
        }
    }
}