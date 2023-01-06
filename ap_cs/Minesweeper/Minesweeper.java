import javax.swing.SwingUtilities;

public class Minesweeper {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { MSSetup setup = new MSSetup(); }
        });
    }
}
