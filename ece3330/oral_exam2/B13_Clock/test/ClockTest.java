import javax.swing.*;

public class ClockTest {
    // Small anonymous class to create a test window class for me to use.
    // This should start a clock in a single window in Iowa City, at the default time.
    private static class ClockTestWindow extends JFrame {
        public ClockTestWindow() {
            super("Clock Test");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(200, 200);
            setResizable(false);

            ClockPanelOptions options = new ClockPanelOptions("Iowa City, Iowa", "GMT-5");
            ClockPanel clock = new ClockPanel(options);
            add(clock);

            setVisible(true);
        }
    }

    // This should display a clock at the default time.
    public static void main(String[] args) {
        ClockTestWindow test = new ClockTestWindow();
    }
}