import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * ListQuestion creates a question with two lists, chosen via moving a set of answers from the first list to the second list.
 */
public class ListQuestion extends MultipleAnswerQuestion {
    private JPanel panel;
    private boolean initialized = false;
    private DefaultListModel resultListModel;

    /**
     * Constructs a list question.
     * @param prompt The question prompt.
     * @param answers The possible answers to said question.
     * @param correctValues The correct answers.
     */
    public ListQuestion(String prompt, String[] answers, String[] correctValues) {
        super(prompt, answers, correctValues);
    }

    /**
     * Gets a JPanel associated with the question, to be rendered in QuizWindow's "render" function.
     * @return A JPanel of the question.
     */
    public JPanel getPanel() {
        if(initialized) return panel;
        this.panel = new JPanel();

        // Create two ListModels for each list.
        DefaultListModel lm1 = new DefaultListModel(); // This page was utilized to learn about ListModel's; http://www.seasite.niu.edu/cs580java/JList_Basics.htm
        DefaultListModel lm2 = new DefaultListModel();
        this.resultListModel = lm2;

        // Add initial answer list to list 1.
        for(String answer : this.getAnswers()) {
            lm1.addElement(answer);
        }

        // Create two JList's with the ListModel as the constructor.
        JList list1 = new JList(lm1);
        JList list2 = new JList(lm2);
        list1.setPreferredSize(new Dimension(40, 180));
        list2.setPreferredSize(new Dimension(40, 180));

        // Create two buttons that will allow us to move these lists back and forth from eachother.
        JButton moveLeft = new JButton("<");
        JButton moveRight = new JButton(">");
        moveLeft.setPreferredSize(new Dimension(40, 20)); // Source: https://stackoverflow.com/a/2537024
        moveRight.setPreferredSize(new Dimension(40, 20));

        // Add the action listener for these buttons.
        moveLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list2.isSelectionEmpty()) return;
                int i = list2.getSelectedIndex();
                lm1.addElement(lm2.getElementAt(i));
                lm2.removeElementAt(i);
            }
        });

        moveRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.isSelectionEmpty()) return;
                int i = list1.getSelectedIndex();
                lm2.addElement(lm1.getElementAt(i));
                lm1.removeElementAt(i);
            }
        });

        // Using GridBagLayout here.
        // The problem with GridLayout is that each cell has uniformly sized elements for each member of the grid;
        // GridBagLayout allows you to use constraints to make things look nice and pretty.
        // Based off of the following oracle doc; https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 40;
        c.gridy = 0;
        c.gridx = 0;
        panel.add(list1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        //c.ipadx = 50;
        c.gridy = 0;
        c.gridx = 1;
        panel.add(list2, c);

        c.gridy = 1;
        c.gridx = 0;
        panel.add(moveLeft, c);

        c.gridx = 1;
        panel.add(moveRight, c);

        initialized = true;
        return panel;
    }

    /**
     * Gets an "Answer" containing values within the second list.
     * @return An Answer.
     */
    public Answer getSelected() {
        ArrayList<String> values = new ArrayList<String>();
        for(int i = 0; i < this.resultListModel.getSize(); i++) {
            values.add((String) this.resultListModel.get(i));
        }
        return new Answer(values);
    }
}
