import javax.swing.*;
import java.util.ArrayList;

/**
 * The quiz results window displays your score and how you did on each individual question.
 */
public class QuizResultsWindow extends JFrame {
    private ArrayList<Question> questions;

    /**
     * Constructs a quiz result window based on the qustions ArrayList from the quiz window class.
     * @param questions The questions to display the results of.
     */
    public QuizResultsWindow(ArrayList<Question> questions) {
        super("Quiz Results");
        this.questions = questions;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 200);

        float[] scores = getGrades();
        float total = 0;
        for(float score : scores) {
            total += score;
        }
        JLabel result = new JLabel("You got: " + ((total / questions.size()) * 100) + "%");
        add(result);

        for(int i = 0; i < scores.length; i++) {
            add(new JLabel("Question " + (i + 1) + ": " + scores[i] + " point(s)"));
        }

        setVisible(true);
    }

    /**
     * Gets the grades for each question in an array as a float.
     * @return An array of floats corresponding to each grade.
     */
    public float[] getGrades() {
        float score = 0;
        float[] scores = new float[this.questions.size()];
        for(int i = 0; i < scores.length; i++) {
            try {
                Question q = this.questions.get(i);
                scores[i] = q.getCorrectness(q.getSelected());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return scores;
    }
}
