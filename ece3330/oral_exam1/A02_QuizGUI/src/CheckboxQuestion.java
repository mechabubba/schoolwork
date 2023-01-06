import javax.swing.*;
import java.util.ArrayList;

/**
 * CheckboxQuestion creates a question with multiple answers, chosen via a set of checkboxes.
 */
public class CheckboxQuestion extends MultipleAnswerQuestion {
    private JPanel panel;
    private boolean initialized = false;
    private ArrayList<JCheckBox> checkboxes;

    /**
     * Constructs a checkbox question.
     * @param prompt The question prompt.
     * @param answers The possible answers to said question.
     * @param correctValues The correct answers.
     */
    public CheckboxQuestion(String prompt, String[] answers, String[] correctValues) {
        super(prompt, answers, correctValues);
    }

    /**
     * Gets a JPanel associated with the question, to be rendered in QuizWindow's "render" function.
     * @return A JPanel of the question.
     */
    public JPanel getPanel() {
        if(initialized) return panel;
        this.panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // BoxLayout allows us to make this flow vertically.
        checkboxes = new ArrayList<JCheckBox>();
        for(String answer : this.getAnswers()) {
            JCheckBox button = new JCheckBox(answer);
            checkboxes.add(button);
            panel.add(button);
        }

        initialized = true;
        return panel;
    }

    /**
     * Gets an "Answer" containing the selected checkboxes.
     * @return An Answer.
     */
    public Answer getSelected() {
        ArrayList<String> values = new ArrayList<String>();
        for(JCheckBox checkbox : this.checkboxes) {
            if(checkbox.isSelected()) values.add(checkbox.getText());
        }
        return new Answer(values);
    }
}
