import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MultipleAnswerQuestion extends Question, and provides a more specific question view.
 * This is meant to describe questions with multiple possible solutions; implemented in this case is a list-based question and a "check all that apply" question.
 */
public abstract class MultipleAnswerQuestion extends Question {
    ArrayList<String> correctValues;

    /**
     * Constructs a multiple answer question.
     * @param prompt The question prompt.
     * @param answers The possible answers to said question.
     * @param correctValues The correct answers.
     */
    public MultipleAnswerQuestion(String prompt, String[] answers, String[] correctValues) {
        super(prompt, answers);
        this.correctValues = new ArrayList<String>(List.of(correctValues));
    }

    /**
     * Gets a "score" for the question, based on the Answer provided.
     * @param ans The user-provided answer. Taken in via the Answer class, meant for individual use in SingleAnswerQuestion and MultipleAnswerQuestion.
     * @return A float describing your score on this question (out of 1).
     */
    public float getCorrectness(Answer ans) {
        ArrayList<String> values = ans.getMultipleAnswers();
        if(values.size() == 0) return 0f; // If nothing was provided, the question was left unanswered.

        int correct = 0;
        for(String value : values) {
            if(correctValues.contains(value)) correct++;
        }
        return ((float) correct) / correctValues.size();
    }

    /**
     * Gets a JPanel associated with the question, to be rendered in QuizWindow's "render" function.
     * @return A JPanel of the question.
     */
    public abstract JPanel getPanel();

    /**
     * Gets an "Answer" containing (the) value(s) selected in each question.
     * @return An Answer.
     */
    public abstract Answer getSelected();
}
