/**
 * The slave root finder does the work in actually finding the roots. This is run as a thread from the master root finder.
 */
public class SlaveRootFinder implements Runnable {
    private final int id;
    private final int polynomials;
    private final boolean doLog;
    private final CircularBuffer<ThreeTuple> tuplebuf;
    private final CircularBuffer<ComplexNumber[]> rootbuf;

    /**
     * Constructs a slave root finder.
     * @param id The ID of this thread - simply used to identify it among the rest.
     * @param polynomials The amount of polynomials we will be calculating.
     * @param tuplebuf The circular buffer in which these polynomials will be held.
     */
    public SlaveRootFinder(int id, int polynomials, CircularBuffer<ThreeTuple> tuplebuf) {
        this.id = id;
        this.polynomials = polynomials;
        this.doLog = this.polynomials <= 100; // Only write individual logs if we're dealing with under 100 polynomials.
        this.rootbuf = new CircularBuffer<>(polynomials);
        this.tuplebuf = tuplebuf;
    }

    /**
     * The run method lets the slave do our calculations in its own thread.
     */
    @Override
    public void run() {
        // Run until the tuple buffer empties.
        do {
            try {
                ThreeTuple tup = this.tuplebuf.get();
                if(tup == null) {
                    // We should never get a null value, *unless* the buffer is closed and empty.
                    // If otherwise, the thread would remain wait()-ing.
                    continue;
                }
                ComplexNumber[] root = solve(tup);
                this.rootbuf.put(root);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error occurred when attempting to get tuple or put root;", e);
            }
        } while(!this.tuplebuf.isEmpty() || !this.tuplebuf.isClosed());

        // Generate some stats.
        int polys = 0;
        int imaginary = 0;
        int real = 0;
        while(!this.rootbuf.isEmpty()) {
            ComplexNumber[] num;
            try {
                num = this.rootbuf.get();
                polys++;
                if (num[0].getImaginary() == 0) {
                    imaginary++;
                } else {
                    real++;
                }
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        log(String.format("Finished. Dealt with %d polynomials, %d had real roots and %d had imaginary.", polys, real, imaginary));
    }

    /**
     * Solves the polynomial tuples for our roots.
     * @param tup The tuple we're trying to solve.
     * @return An array of complex numbers, each representing a root of the equation.
     */
    public ComplexNumber[] solve(ThreeTuple tup) {
        ComplexNumber[] num = new ComplexNumber[2];
        int a = tup.getA();
        int b = tup.getB();
        int c = tup.getC();

        int discriminant = ((int)Math.pow(b, 2)) - (4 * a * c);
        if(discriminant < 0) {
            // Negative discriminant, so there are imaginary roots.
            // Just take the negative of the discriminant to make it positive, and set it to the imaginary portion of the complex number.
            num[0] = new ComplexNumber(-(double)b / (2 * a), Math.sqrt(-discriminant) / (2 * a));
            num[1] = new ComplexNumber(-(double)b / (2 * a), -Math.sqrt(-discriminant) / (2 * a));
        } else {
            // Real roots.
            num[0] = new ComplexNumber(((-(int)b + Math.sqrt(discriminant)) / (2 * a)));
            num[1] = new ComplexNumber(((-(int)b - Math.sqrt(discriminant)) / (2 * a)));
        }

        if(doLog) {
            log(String.format("Polynomial: %s\t\tRoots: (%s, %s)", tup.getPolynomial(), num[0], num[1]));
        }

        return num;
    }

    /**
     * Helper log function.
     * @param log What to print.
     */
    private void log(String log) {
        System.out.printf("[Thread %d] %s\n", this.id, log);
    }
}
