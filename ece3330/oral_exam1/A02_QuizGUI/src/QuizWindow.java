import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The window for our quiz.
 */
public class QuizWindow extends JFrame {
    private ArrayList<Question> questions;

    /**
     * Constructs a quiz window based on an array of provided questions.
     * @param questions The questions to be displayed in this quiz.
     */
    public QuizWindow(Question[] questions) {
        super("Elementary Math Quiz");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        //setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        this.questions = new ArrayList<Question>();
        for(Question question : questions) {
            this.questions.add(question);
        }

        this.render(); // Form our window.
        setVisible(true);
    }

    /**
     * Renders the window and displays it.
     */
    public void render() {
        JLabel note = new JLabel("Note: You can get between one and zero points on each question.");
        add(note);
        for(Question question : questions) {
            // Create a panel in which we place our question and answers.
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Using BoxLayout to organize it vertically.

            JLabel questionLabel = new JLabel(question.getPrompt());
            panel.add(questionLabel);
            panel.add(question.getPanel()); // Render the answer panel accordingly.

            // Add our panel to the frame.
            panel.setAlignmentY(Component.BOTTOM_ALIGNMENT); // Align panel to bottom of parent.
            add(panel); // Throw 'er in.
        }

        // No cheating allowed, you will be in big trouble.
        JLabel ac_label = new JLabel("By checking this box, I verify that I did not cheat on this assignment.");
        JCheckBox ac_checkbox = new JCheckBox();
        JButton ac_button = new JButton("Submit Work");
        ac_button.setEnabled(false);

        // Only enable the button when the checkbox is enabled.
        ac_checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ac_checkbox.isSelected()) {
                    ac_button.setEnabled(true);
                } else {
                    ac_button.setEnabled(false);
                }
            }
        });

        // When the button is pressed,
        ac_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizResultsWindow results = new QuizResultsWindow(questions);
            }
        });

        // Add all of these.
        add(ac_label);
        add(ac_checkbox);
        add(ac_button);
    }
}
