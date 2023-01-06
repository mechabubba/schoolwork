import javax.swing.*;
import java.util.ArrayList;

/**
 * RadioButtonQuestion creates a question with one answer, chosen via a generic multiple choice question.
 */
public class RadioButtonQuestion extends SingleAnswerQuestion {
    private JPanel panel;
    private boolean initialized = false;
    private ArrayList<JRadioButton> buttons;

    /**
     * Constructs a radio button question.
     * @param prompt The question prompt.
     * @param answers The possible answers to said question.
     * @param correctAnswer The correct answer.
     */
    public RadioButtonQuestion(String prompt, String[] answers, String correctAnswer) {
        super(prompt, answers, correctAnswer);
    }

    /**
     * Gets a JPanel associated with the question, to be rendered in QuizWindow's "render" function.
     * @return A JPanel of the question.
     */
    public JPanel getPanel() {
        if(initialized) return panel;
        this.panel = new JPanel();

        buttons = new ArrayList<JRadioButton>();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // BoxLayout allows us to make this flow vertically.
        // ButtonGroup's help with keeping only one button active at a time.
        // https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
        ButtonGroup group = new ButtonGroup();
        for(String answer : this.getAnswers()) {
            JRadioButton button = new JRadioButton(answer);
            group.add(button); // Add the button to the ButtonGroup (allowing only one to be pressed at a time).
            panel.add(button); // Add the button to the panel (allowing it to be placed in the window).
            buttons.add(button); // Add the button to our ArrayList (allowing the getResult() function to find whats selected).
        }

        initialized = true;
        return panel;
    }

    /**
     * Gets an "Answer" containing the selected button value.
     * @return An Answer.
     */
    public Answer getSelected() {
        for(JRadioButton button : buttons) {
            if(button.isSelected()) {
                return new Answer(button.getText());
            }
        }
        return new Answer();
    }
}
