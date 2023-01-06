import javax.swing.*;

/**
 * DropdownQuestion creates a question with one answer, chosen via a dropdown.
 */
public class DropdownQuestion extends SingleAnswerQuestion {
    private JPanel panel;
    private boolean initialized = false;
    private JComboBox combo;

    /**
     * Constructs a dropdown question.
     * @param prompt The question prompt.
     * @param answers The possible answers to said question.
     * @param correctAnswer The correct answer.
     */
    public DropdownQuestion(String prompt, String[] answers, String correctAnswer) {
        super(prompt, answers, correctAnswer);
    }

    /**
     * Gets a JPanel associated with the question, to be rendered in QuizWindow's "render" function.
     * @return A JPanel of the question.
     */
    public JPanel getPanel() {
        if(initialized) return panel;
        this.panel = new JPanel();
        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // BoxLayout allows us to make this flow vertically.

        JComboBox box = new JComboBox(this.getAnswers().toArray(new String[]{}));
        this.combo = box;
        panel.add(box);

        initialized = true;
        return panel;
    }

    /**
     * Gets an "Answer" containing the selected dropdown value.
     * @return An Answer.
     */
    public Answer getSelected() {
        return new Answer((String) combo.getSelectedItem());
    }
}
