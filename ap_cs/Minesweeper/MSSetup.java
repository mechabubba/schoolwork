import java.awt.*;    // the Container class is in here apparently
import java.awt.event.*; // events like button clicking, etc
import javax.swing.*; // heres where the JFrame components come from

public class MSSetup extends JFrame {
    private static final String title = "Minesweeper";
    
    public MSSetup() {
        super(title);
        init();
    }
    
    public void init() {
        /* you can directly add things to the window; no need to put it
           in a content pane or anything. */
        JLabel lengthL = new JLabel("Length:");
        JLabel widthL = new JLabel("Width:");
        JLabel minesL = new JLabel("Mines:");
        
        lengthL.setBorder(BorderFactory.createEmptyBorder(0,8,0,0)); // top, left, bottom, right
        widthL.setBorder(BorderFactory.createEmptyBorder(0,8,0,0));  // for all labels, move em a bit
        minesL.setBorder(BorderFactory.createEmptyBorder(0,8,0,0));  // away from the side of window
        
        SpinnerModel lengthModel = new SpinnerNumberModel(15, 1, 100, 1); // initial, min, max, step
        SpinnerModel widthModel = new SpinnerNumberModel(15, 1, 100, 1);  // apparently you need one each? memory stuff i guess
        SpinnerModel mineModel = new SpinnerNumberModel(30, 1, 10000, 1); 
        
        JSpinner lengthS = new JSpinner(lengthModel); // JTextField lengthF = new JTextField(2);
        JSpinner widthS = new JSpinner(widthModel);
        JSpinner minesS = new JSpinner(mineModel);
        
        JButton sweep = new JButton("Sweep!");
        
        add(lengthL);
        add(lengthS);
        add(widthL);
        add(widthS);
        add(minesL);
        add(minesS);
        add(sweep);
        
        // the GridLayout layout helps organize our JComponents in the window.
        GridLayout layout = new GridLayout(4, 2); // 4 rows, 2 columns, gap of 4 pixels in between
        setLayout(layout);
        
        // the frame is the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // time to display our stuff
        setSize(100, 300); // a default resolution of 100, 150
        
        /* this method makes it so our jcomponents are evenly spaced within our window,
           ignoring the resolution. */
        pack();
        setResizable(false);
        setVisible(true);
        
        sweep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int lengthV = (int)lengthS.getValue();
                int widthV = (int)widthS.getValue();
                int minesV = (int)minesS.getValue();
                
                if(minesV > (lengthV * widthV)) minesV = lengthV * widthV; // cant have more mines than spaces :p
                
                int[] values = new int[]{lengthV, widthV, minesV};
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        MSGame game = new MSGame(values);
                    }
                });
            }
        });
    }
}
