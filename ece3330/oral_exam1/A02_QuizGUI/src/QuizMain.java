public class QuizMain {
    private static Question[] questions = {
        new RadioButtonQuestion(
            "What is the solution? 5 + 3 * 4",
            new String[]{ "0", "32", "27", "17" },
            "17"
        ),
        new DropdownQuestion(
            "What is the solution? (25 / 5) * (9 - (3 * 3))",
            new String[]{ "0", "90", "25", "9" },
            "0"
        ),
        new ListQuestion(
            "Drag all the even numbers to the other side.",
            new String[]{ "1", "2", "3", "4", "35", "100", "-33", "0", "5", "24" },
            new String[]{ "2", "4", "100", "0", "24" }
        ),
        new CheckboxQuestion(
            "Which of the following are multiples of 7?",
            new String[]{ "11", "48", "56", "21", "33", "77", "127" },
            new String[]{ "56", "21", "77" }
        ),
        new DropdownQuestion(
            "What is the solution of this function for x = 3? y(x) = 8x + 5",
            new String[]{ "28", "43", "26", "29" },
            "29"
        )
    };

    // The main method.
    public static void main(String[] args) {
        QuizWindow quiz = new QuizWindow(questions);
    }
}
