import javax.swing.*;

/**
 * SingleAnswerQuestion extends Question, and provides a more specific question view.
 * This is meant to describe questions with a single possible solution; implemented in this case is a generic multiple choice question and a dropdown question.
 */
public abstract class SingleAnswerQuestion extends Question {
    private String correctValue; // The correct value of the question.

    /**
     * Constructs a single answer question.
     * @param prompt The question prompt.
     * @param answers The possible answers to said question.
     * @param correctValue The correct answer.
     */
    public SingleAnswerQuestion(String prompt, String[] answers, String correctValue) {
        super(prompt, answers);
        this.correctValue = correctValue;
    }

    /**
     * Gets a "score" for the question, based on the Answer provided.
     * @param ans The user-provided answer. Taken in via the Answer class, meant for individual use in SingleAnswerQuestion and MultipleAnswerQuestion.
     * @return A float describing your score on this question (out of 1).
     */
    public float getCorrectness(Answer ans) {
        String value = ans.getSingleAnswer();

        if(value.equals(correctValue)) return 1f;
        return 0f;
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
