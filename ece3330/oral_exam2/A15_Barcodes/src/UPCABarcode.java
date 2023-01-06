import java.util.Arrays;
import java.util.List;

/**
 * This class includes methods to encode and decode UPC-A values to barcodes, and vice versa.
 */
public class UPCABarcode {
    // A list of strings (of length 4) representing the lengths four different bar sequences, alternating in color.
    private static final List<String> numeralBinary = Arrays.asList(new String[]{
        "3211", "2221", "2122", "1411", "1132", "1231", "1114", "1312", "1213", "3112"
    });

    /**
     * Encodes a UPC-A barcode from the given UPC.
     * @param input The UPC to generate the barcode from.
     * @return A string representing the barcode of the UPC.
     */
    public static String encode(String input) {
        // We start out already in possession of the 11-digit UPC.
        // Calculate the checksum digit of the input code, throw it onto the end of the input.
        int checksum = calculateChecksum(input);
        input += String.valueOf(checksum);

        // Format the code.
        StringBuilder builder = new StringBuilder();
        builder.append("101"); // Begin barcode with start code "101".
        boolean polarity = false; // A "polarity" bool that helps determine the color bar we're drawing; black (true, "1") or white (false, "0").
        for(int i = 0; i < input.length(); i++) {
            int num = Integer.parseInt(input.substring(i, i + 1));

            // Get the UPC numeral sequence.
            String sequence = numeralBinary.get(num);
            for(int j = 0; j < sequence.length(); j++) {
                int barlen = Integer.parseInt(sequence.substring(j, j + 1));

                // This loop actually appends the data to the string builder.
                for(int k = 0; k < barlen; k++) {
                    builder.append(polarity ? "1" : "0");
                }

                // As we've just drawn a bar, swap polarity to change the value being put into the string.
                polarity = !polarity;
            }

            // Handle adding the midpoint code, if we've hit the middle of the sequence.
            if(i == 5) {
                builder.append("01010");
                polarity = !polarity;; // Increase by 1, as we change bar color after this sequence.
            }
        }
        builder.append("101"); // End barcode with end code "101".

        return builder.toString(); // Done. :)
    }

    /**
     * Decodes a UPC-A barcode and returns the UPC it represents.
     * @param input A string of binary 0's and 1's representing an encoded UPC-A barcode.
     * @return A string representing the UPC of the barcode.
     * @throws Exception Throws if any issues occur during the decoding process.
     */
    public static String decode(String input) throws Exception {
        // Test potential length of the string.
        if(input.length() != (3 + 3 + (12 * 7) + 5)) { // Start + end codes, midpoint code, and 12 groups of 7 (11 for characters, 1 for checksum).
            throw new Exception(String.format("Invalid barcode provided; length of input is \"%d\", should be %d.", input.length(), 95));
        }

        // Chop off the start and end codes of the input.
        input = input.substring(3, input.length() - 3);

        // Create some variables to help us through the decoding process.
        StringBuilder builder = new StringBuilder();
        int index = 0; // Our raw index location in the string.
        int barGroup = 0; // The bar group index we're currently parsing.
        int providedChecksum = 0; // The checksum obtained whilst parsing the string.

        // Look through the rest of the string groups of 7 at a time.
        // Each segment of bars is seven bars long.
        while(barGroup < 12) {
            String group = input.substring(index, index + 7);

            // Find the "numeral string" of this group.
            // This will always be four characters long, indicating four changes of "polarity" within this group.
            StringBuilder numeralValue = new StringBuilder();
            int value = 0;
            char key = group.charAt(0);
            for(int i = 0; i < group.length(); i++) {
                // If this next key doesn't match the previous, it means we changed polarity; represent this in the string key and append it to the string builder.
                // Otherwise, increase the count and continue.
                char next = group.charAt(i);
                if(next != key) {
                    numeralValue.append(value);
                    value = 1;
                    key = next;
                } else {
                    value++;
                }
            }
            numeralValue.append(value);

            int numeralIndex = numeralBinary.indexOf(numeralValue.toString()); // The number this value represents; the index of the "numeral string" list above.
            if(barGroup == 11) {
                // Checksum group; do not append this to the final UPC.
                providedChecksum = numeralIndex;
            } else {
                builder.append(numeralIndex);
            }

            if(barGroup == 5) {
                // Ignore the midpoint code.
                index += 5;
            }

            // Update the index by 7 and the bar group by 1, and continue.
            index += 7;
            barGroup++;
        }

        // At this point, we've successfully decoded our UPC.
        // Finally, we do one more check to see if the generated UPC matches the provided checksum.
        String upc = builder.toString();
        int generatedChecksum = calculateChecksum(upc);
        if(providedChecksum != generatedChecksum) {
            throw new Exception(String.format("Invalid barcode provided; checksum failed, got %d instead of the provided %d.", generatedChecksum, providedChecksum));
        }
        return upc;
    }

    /**
     * Calculates a checksum from a provided UPC.
     * @param input The UPC.
     * @return A checksum representing this UPC.
     */
    private static int calculateChecksum(String input) {
        int odds = 0, evens = 0;
        for(int i = 0; i < input.length(); i++) {
            int num = Integer.parseInt(input.substring(i, i + 1));
            // We take the sum of even/odd-indexed digits counting from 1.
            if((i + 1) % 2 == 0) {
                evens += num;
            } else {
                odds += num;
            }
        }

        // Calculate the rest of the values that play into the checksum.
        int total = (3 * odds) + evens;
        int checksum = 10 - (total % 10);
        return checksum;
    }
}