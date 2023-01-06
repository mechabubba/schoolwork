import java.util.ArrayList;

/**
 * The Answer class provides a uniform method of passing a users selected answers around to SingleAnswerQuestion and MultipleAnswerQuestion.
 */
public class Answer {
    private String singleAnswer;
    private ArrayList<String> multipleAnswers;

    /**
     * Constructor called when nothing is provided (typically means you didn't answer a question).
     */
    public Answer() {
        this.singleAnswer = "";
        this.multipleAnswers = new ArrayList<String>();
    }

    /**
     * Constructor called in single answer questions.
     * @param answer Your answer.
     */
    public Answer(String answer) {
        this();
        this.singleAnswer = answer;
    }

    /**
     * Constructor called in multi answer questions.
     * @param answers Your answers.
     */
    public Answer(ArrayList<String> answers) {
        this();
        this.multipleAnswers = answers;
    }

    /**
     * Gets multiple answers in the case of a multi answer question. Will return an empty ArrayList in single answer questions.
     * @return An arraylist of your answers.
     */
    public ArrayList<String> getMultipleAnswers() {
        return multipleAnswers;
    }

    /**
     * Gets a single answer in the case of a single answer question. Will return an empty string in multi answer questions.
     * @return A string of your answers.
     */
    public String getSingleAnswer() {
        return singleAnswer;
    }
}
