import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MSGame extends JFrame {
    private static final String title = "Minesweeper";
    
    /* color config */
    private final Color[] numColors = {
        /* 0 */ new Color(255, 255, 255, 0), // transparent
        /* 1 */ new Color(0, 0, 255),
        /* 2 */ new Color(0, 127, 0),
        /* 3 */ new Color(255, 0, 0),
        /* 4 */ new Color(0, 0, 127),
        /* 5 */ new Color(127, 0, 0),
        /* 6 */ new Color(0, 127, 127),
        /* 7 */ new Color(0, 0, 0),
        /* 8 */ new Color(127, 127, 127),
        /* F */ new Color(0, 0, 0)
    };
    private final Color cbDark = new Color(245, 245, 245);
    private final Color cbLight = new Color(255, 255, 255);
    
    private int l;
    private int w;
    private int m;
    
    public MSGame(int[] val) throws ArrayIndexOutOfBoundsException {
        this(val[0], val[1], val[2]);
    }
    
    public MSGame(int length, int width, int mines) throws ArrayIndexOutOfBoundsException {
        super(title);
        l = length;
        w = width;
        m = mines;
        
        init();
    }
    
    public void init() {
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        
        MinesweeperBoard game = new MinesweeperBoard(l, w, m);
        JPanel[][] tiles = new JPanel[l][w];
        
        for(int i = 0; i < l; i++) {
            for(int j = 0; j < w; j++) {
                tiles[i][j] = new JPanel();
                tiles[i][j].putClientProperty("i", i);
                tiles[i][j].putClientProperty("j", j);
                
                // checkerboard pattern
                if(i % 2 == 0) {
                    if(j % 2 != 0) { tiles[i][j].setBackground(cbLight); } else tiles[i][j].setBackground(cbDark);
                } else {
                    if(j % 2 == 0) { tiles[i][j].setBackground(cbLight); } else tiles[i][j].setBackground(cbDark);
                }
                
                // text formatting
                JLabel label = new JLabel(Character.toString(game.getDisplayValue(j, i)));
                label.setFont(new Font("Monospaced", Font.PLAIN, 14));
                
                tiles[i][j].add(label);
                panel.add(tiles[i][j]);
                tiles[i][j].addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent e) {
                        JPanel source = (JPanel)e.getSource();
                        
                        int i = (int)source.getClientProperty("i");
                        int j = (int)source.getClientProperty("j");
                        
                        if(SwingUtilities.isRightMouseButton(e)) {
                            game.update(i, j, true);
                            
                            label.setText("F");
                            label.setForeground(numColors[3]);
                        } else {
                            game.update(i, j);
                            
                            char value = game.getDisplayValue(j, i);
                            label.setText(Character.toString(value));
                            
                            int num = Character.getNumericValue(value);
                            if(num > 0) {
                                label.setForeground(numColors[num]);
                            }
                        }
                    } 
                    // the others aren't used
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                    public void mousePressed(MouseEvent e) {}
                    public void mouseReleased(MouseEvent e) {}
                });
            }
        }
        
        SpringUtilities.makeGrid(panel,
            l,  // rows
            w,  // columns
            0,  // initial x
            0,  // initial y
            0,  // x padding
            0); // y padding
        add(panel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }
}