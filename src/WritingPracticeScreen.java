import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
* Lead Author(s):
* @author yanez; student ID
* @author Full name; student ID
* <<Add additional lead authors here>>
*
* Other Contributors:
* Full name; student ID or contact information if not in class
* <<Add additional contributors (mentors, tutors, friends) here, with contact information>>
*
* References:
* Morelli, R., & Walde, R. (2016).
* Java, Java, Java: Object-Oriented Problem Solving
* https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
*
* <<Add more references here>>
*
* Version:
*/

/**
 * Represents a writing practice screen for one study section.
 * This screen asks the user to type the English meaning of a prompt.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class WritingPracticeScreen extends JFrame implements ActionListener
{
    private String sectionName;
    private ArrayList<StudyItem> items;
    private int currentIndex;

    private JLabel titleLabel;
    private JLabel promptLabel;
    private JLabel feedbackLabel;
    private JTextField answerField;

    private RoundedButton submitButton;
    private RoundedButton nextButton;
    private RoundedButton backButton;

    /**
     * Creates a writing practice screen for the selected section.
     *
     * @param sectionName the name of the section to study
     */
    public WritingPracticeScreen(String sectionName)
    {
        this.sectionName = sectionName;
        this.items = new ArrayList<>();
        this.currentIndex = 0;

        setTitle("マイ練習 - " + sectionName + " Writing Practice");
        setSize(950, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadData();

        Color backgroundColor = new Color(252, 251, 247);
        Color buttonColor = new Color(230, 238, 250);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);

        titleLabel = new JLabel(sectionName + " Writing Practice");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(44, 74, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        promptLabel = new JLabel("");
        promptLabel.setFont(new Font("Serif", Font.BOLD, 42));
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        answerField = new JTextField();
        answerField.setFont(new Font("SansSerif", Font.PLAIN, 22));
        answerField.setMaximumSize(new java.awt.Dimension(400, 45));
        answerField.setHorizontalAlignment(JTextField.CENTER);

        feedbackLabel = new JLabel("Type the English meaning.");
        feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        feedbackLabel.setForeground(new Color(90, 90, 90));
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 2),
                BorderFactory.createEmptyBorder(45, 60, 45, 60)));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setMaximumSize(new java.awt.Dimension(550, 300));

        cardPanel.add(Box.createVerticalGlue());
        cardPanel.add(promptLabel);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(answerField);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(feedbackLabel);
        cardPanel.add(Box.createVerticalGlue());

        submitButton = new RoundedButton("Submit（答える）");
        nextButton = new RoundedButton("Next（次へ）");
        backButton = new RoundedButton("Back（戻る）");

        RoundedButton[] buttons = { submitButton, nextButton, backButton };
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        for (RoundedButton button : buttons)
        {
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            button.setBackground(buttonColor);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        mainPanel.add(Box.createVerticalStrut(35));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(cardPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        updateQuestion();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Loads vocabulary data for the writing practice screen.
     */
    private void loadData()
    {
        try
        {
            if (sectionName.equals("Vocabulary"))
            {
                items = DataLoader.loadVocabulary("vocabulary.txt");
            }
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(this,
                    "Could not load vocabulary data file.");
        }
    }

    /**
     * Updates the screen to show the current study prompt.
     */
    private void updateQuestion()
    {
        if (items.isEmpty())
        {
            promptLabel.setText("No items found.");
            feedbackLabel.setText("Check your vocabulary.txt file.");
            return;
        }

        StudyItem currentItem = items.get(currentIndex);
        promptLabel.setText(currentItem.getPrompt());
        answerField.setText("");
        feedbackLabel.setText("Type the English meaning.");
        answerField.requestFocusInWindow();
    }

    /**
     * Checks the user's typed answer against the current answer.
     */
    private void checkAnswer()
    {
        if (items.isEmpty())
        {
            return;
        }

        StudyItem currentItem = items.get(currentIndex);

        String userAnswer = answerField.getText().trim().toLowerCase();
        String correctAnswer = currentItem.getAnswer().trim().toLowerCase();

        if (userAnswer.equals(correctAnswer))
        {
            feedbackLabel.setText("Correct! 正解です！");
            feedbackLabel.setForeground(new Color(40, 130, 70));
        }
        else
        {
            feedbackLabel.setText("Incorrect. Answer: "
                    + currentItem.getAnswer());
            feedbackLabel.setForeground(new Color(170, 60, 60));
        }
    }

    /**
     * Responds to button clicks on the writing practice screen.
     *
     * @param e the ActionEvent triggered by the user
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submitButton)
        {
            checkAnswer();
        }
        else if (e.getSource() == nextButton)
        {
            if (!items.isEmpty())
            {
                currentIndex = (currentIndex + 1) % items.size();
                updateQuestion();
            }
        }
        else if (e.getSource() == backButton)
        {
            new SectionMenuScreen(sectionName);
            dispose();
        }
    }
}
