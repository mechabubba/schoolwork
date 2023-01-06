import java.util.concurrent.*;
import java.util.Scanner;
import java.util.Random;

/**
 * This class is the driver of the root finder.
 */
public class MasterRootFinder {
    private static final int tupleMin = -1000;
    private static final int tupleMax = 1000;
    private static final Random rand = new Random(); // Random number generator.

    /**
     * The main method.
     * @param args Provided command line arguments.
     */
    public static void main(String[] args) {
        // UX stuff.
        System.out.println("Pick your poison;");
        System.out.println("1. Generate and solve 30 sets of randomly generated coefficients.");
        System.out.println("2. Generate and solve 3,000 sets of randomly generated coefficients.");
        System.out.print("> ");
        
        Scanner input = new Scanner(System.in);
        int poison = input.nextInt();
        int polys;

        switch(poison) {
            case 1: {
                polys = 30;
                break;
            }
            case 2: {
                polys = 3000;
                break;
            }
            default: {
                System.out.println("Invalid choice chosen! Terminating...");
                return;
            }
        }

        // Create the tuple buffer.
        CircularBuffer<ThreeTuple> tuplebuf = new CircularBuffer<>(10);

        // Create the slave threads.
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            SlaveRootFinder rootfinder = new SlaveRootFinder(i, polys, tuplebuf);
            service.execute(rootfinder);
        }
        service.shutdown(); // No new threads are to be instantiated, so when they end the program will shut down.

        // Start generating random sets of coefficients applicable toward our problem.
        // Then, insert them, one at a time, into a circular buffer which is shared with all the slaves.
        try {
            for (int i = 0; i < polys; i++) {
                ThreeTuple tup = new ThreeTuple(_randNum(), _randNum(), _randNum());
                if(tup.getA() == 0) {
                    // If A is zero, the slave thread will divide by zero and die. Let's avoid that and try again.
                    i--;
                    continue;
                }
                tuplebuf.put(tup);
            }
            tuplebuf.close(); // We are done putting data in, close the buffer.
        } catch(InterruptedException e) {
            throw new RuntimeException("Error occurred when attempting to put tuple;", e);
        }
    }

    /**
     * Generates a random int within [-1000, 1000].
     * <p>
     * The java.util.Random class is helpful in this endeavor as it allows us to get random numbers within *inclusive* bounds.
     * Assistance: <a href="https://stackoverflow.com/a/52250119">StackOverflow</a>
     * @return A random number suitable for our needs.
     */
    private static int _randNum() {
        return rand.nextInt((tupleMax - tupleMin) + 1) + tupleMin;
    }
}