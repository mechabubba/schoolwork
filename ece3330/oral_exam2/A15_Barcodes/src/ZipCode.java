import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents all formats of a zip code.
 */
public class ZipCode {
    // A pattern to match valid zip codes. The pattern attempts to match strings to the following groups;
    // Group 1: ZIP (5 digits)
    // Group 2: "B code" (4 digits)
    // Group 3: "Delivery code" (2 digits)
    // If they don't exist, they end up as null - these null values are set to empty strings in the constructor, if they exist.
    private static final Pattern validZip = Pattern.compile("([0-9]{5})-?([0-9]{4})?-?([0-9]{2})?");

    private final String zipCode; // The zipcode itself.
    private String Bcode; // The +4 "B" code.
    private String deliveryCode; // The specific delivery code.

    /**
     * Creates a zip code.
     * @param input The string representation of the zipcode.
     * @throws Exception
     */
    public ZipCode(String input) throws Exception {
        // Match the input string to the regex pattern.
        Matcher matcher = validZip.matcher(input);

        // Get the matched groups from said pattern.
        if(matcher.matches()) {
            this.zipCode = matcher.group(1);
            this.Bcode = matcher.group(2);
            this.deliveryCode = matcher.group(3);
            if (this.zipCode == null || (this.deliveryCode != null && this.Bcode == null)) {
                // Invalid combination of zipcode elements.
                // We don't need to check every combination of groupings as the zipcode group is mandatory and won't pass at all if it's not included.
                throw new Exception("Invalid zip code provided.");
            }
        } else {
            throw new Exception("Invalid zip code; no matches found.");
        }

        // Manually reset null values to empty strings.
        if(this.Bcode == null) this.Bcode = "";
        if(this.deliveryCode == null) this.deliveryCode = "";
    }

    /**
     * Creates a zip code. This constructor skips all checks as its assumed that the values provided are accurate.
     * @param zipCode The actual zip code.
     * @param Bcode The four digit "B" code.
     * @param deliveryCode The two digit delivery code.
     */
    public ZipCode(String zipCode, String Bcode, String deliveryCode) {
        this.zipCode = zipCode;
        this.Bcode = Bcode;
        this.deliveryCode = deliveryCode;
    }

    /**
     * Gets the zip code.
     * @return The zip code.
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * Gets the "B" code of the zip code; if it doesn't exist, this will return an empty string.
     * @return The zip codes "B" code.
     */
    public String getBcode() {
        return this.Bcode;
    }

    /**
     * Gets the delivery code of the zip code; if it doesn't exist, this will return an empty string.
     * @return The delivery code of the zip code.
     */
    public String getDeliveryCode() {
        return this.deliveryCode;
    }

    /**
     * Gets a string representation of the zip code.
     * @return A string representation of the zip code.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.zipCode);
        if(!this.Bcode.isEmpty()) {
            result.append("-");
            result.append(this.Bcode);
            if(!this.deliveryCode.isEmpty()) {
                result.append("-");
                result.append(this.deliveryCode);
            }
        }
        return result.toString();
    }
}
