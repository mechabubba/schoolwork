/**
 * This class represents a complex number; a number with real and imaginary parts.
 */
public class ComplexNumber {
    private final double real;
    private final double imag;

    /**
     * Create a complex number with only a "real" portion; this is just complex with an imaginary value of 0.
     * @param real The real portion of the complex number.
     */
    public ComplexNumber(double real) {
        this.real = real;
        this.imag = 0;
    }

    /**
     * Create a complex number.
     * @param real The real portion of the complex number.
     * @param imag The imaginary portion of the complex number.
     */
    public ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    /**
     * Gets the real portion of the complex number.
     * @return The real portion of the complex number.
     */
    public double getReal() {
        return real;
    }

    /**
     * Gets the imaginary portion of the complex number.
     * @return The imaginary portion of the complex number.
     */
    public double getImaginary() {
        return imag;
    }

    /**
     * Stringifies the complex number into something nice looking.
     * @return A string representing the complex number.
     */
    public String toString() {
        if(this.imag == 0) {
            return String.format("%f", this.real);
        } else {
            return String.format("%f %s %fi", this.real, this.imag < 0 ? "-" : "+", Math.abs(this.imag));
        }
    }
}