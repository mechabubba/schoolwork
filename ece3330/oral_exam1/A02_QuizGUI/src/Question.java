import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Question class provides an abstract view of a quiz question.
 * This is extended by SingleAnswerQuestion and MultipleAnswerQuestion, which are extended by specific types of questions that can be provided and rendered to the window.
 */
public abstract class Question {
    final private String prompt; // The question prompt. No changing this once the questions created!
    private ArrayList<String> answers; // A list of answers in the form of strings.

    /**
     * Constructs a base question.
     * @param prompt The question prompt.
     * @param answers The possible answers to said question.
     */
    public Question(String prompt, String[] answers) {
        this.prompt = prompt;
        this.answers = new ArrayList<String>(List.of(answers));
    }

    /**
     * Gets the question prompt.
     * @return The question prompt.
     */
    public String getPrompt() {
        return this.prompt;
    }

    /**
     * Gets the questions possible answers.
     * @return The questions possible answers.
     */
    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    /**
     * Gets a "score" for each question, based on the Answer provided.
     * @param answer The user-provided answer. Taken in via the Answer class, meant for individual use in SingleAnswerQuestion and MultipleAnswerQuestion.
     * @return A float describing your score on the question (out of 1).
     */
    public abstract float getCorrectness(Answer answer);

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
