import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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
 * Represents a matching practice screen for one study section.
 * This screen asks the user to match a Japanese prompt with its meaning.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class MatchingScreen extends JFrame implements ActionListener
{
    private String sectionName;
    private ArrayList<StudyItem> items;
    private ArrayList<StudyItem> roundItems;

    private JList<String> promptList;
    private JList<String> answerList;

    private DefaultListModel<String> promptModel;
    private DefaultListModel<String> answerModel;

    private JLabel feedbackLabel;

    private RoundedButton matchButton;
    private RoundedButton newRoundButton;
    private RoundedButton backButton;

    /**
     * Creates a matching screen for the selected section.
     *
     * @param sectionName the name of the section to study
     */
    public MatchingScreen(String sectionName)
    {
        this.sectionName = sectionName;
        this.items = new ArrayList<>();
        this.roundItems = new ArrayList<>();

        setTitle("マイ練習 - " + sectionName + " Matching");
        setSize(950, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadData();
        createRoundItems();

        Color backgroundColor = new Color(252, 251, 247);
        Color buttonColor = new Color(230, 238, 250);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);

        JLabel titleLabel = new JLabel(sectionName + " Matching");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(44, 74, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel(
                "Select one Japanese word and one English meaning.");
        instructionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        instructionLabel.setForeground(new Color(90, 90, 90));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        promptModel = new DefaultListModel<>();
        answerModel = new DefaultListModel<>();

        promptList = new JList<>(promptModel);
        answerList = new JList<>(answerModel);

        promptList.setFont(new Font("Serif", Font.BOLD, 24));
        answerList.setFont(new Font("SansSerif", Font.PLAIN, 22));

        JScrollPane promptScrollPane = new JScrollPane(promptList);
        JScrollPane answerScrollPane = new JScrollPane(answerList);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        listsPanel.setBackground(backgroundColor);
        listsPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JPanel leftPanel = createListPanel("Japanese", promptScrollPane);
        JPanel rightPanel = createListPanel("English", answerScrollPane);

        listsPanel.add(leftPanel);
        listsPanel.add(rightPanel);

        feedbackLabel = new JLabel("Choose a pair to check.");
        feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        feedbackLabel.setForeground(new Color(90, 90, 90));
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        matchButton = new RoundedButton("Match（合わせる）");
        newRoundButton = new RoundedButton("New Round（新しい問題）");
        backButton = new RoundedButton("Back（戻る）");

        RoundedButton[] buttons = { matchButton, newRoundButton, backButton };
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        for (RoundedButton button : buttons)
        {
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            button.setBackground(buttonColor);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(instructionLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(listsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(feedbackLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        updateLists();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Creates a labeled panel for one matching list.
     *
     * @param title the title displayed above the list
     * @param scrollPane the scroll pane containing the list
     * @return a JPanel containing a label and list
     */
    private JPanel createListPanel(String title, JScrollPane scrollPane)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(252, 251, 247));

        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(scrollPane);

        return panel;
    }

    /**
     * Loads vocabulary data for the matching screen.
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
     * Selects a small shuffled group of items for the current round.
     */
    private void createRoundItems()
    {
        roundItems.clear();

        ArrayList<StudyItem> shuffledItems = new ArrayList<>(items);
        Collections.shuffle(shuffledItems);

        int numberOfItems = Math.min(5, shuffledItems.size());

        for (int i = 0; i < numberOfItems; i++)
        {
            roundItems.add(shuffledItems.get(i));
        }
    }

    /**
     * Updates both matching lists with shuffled prompts and answers.
     */
    private void updateLists()
    {
        promptModel.clear();
        answerModel.clear();

        ArrayList<String> prompts = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();

        for (StudyItem item : roundItems)
        {
            prompts.add(item.getPrompt());
            answers.add(item.getAnswer());
        }

        Collections.shuffle(prompts);
        Collections.shuffle(answers);

        for (String prompt : prompts)
        {
            promptModel.addElement(prompt);
        }

        for (String answer : answers)
        {
            answerModel.addElement(answer);
        }

        feedbackLabel.setText("Choose a pair to check.");
        feedbackLabel.setForeground(new Color(90, 90, 90));
    }

    /**
     * Checks whether the selected prompt and answer are a correct pair.
     */
    private void checkMatch()
    {
        String selectedPrompt = promptList.getSelectedValue();
        String selectedAnswer = answerList.getSelectedValue();

        if (selectedPrompt == null || selectedAnswer == null)
        {
            feedbackLabel.setText("Please select one item from each list.");
            feedbackLabel.setForeground(new Color(170, 100, 40));
            return;
        }

        for (StudyItem item : roundItems)
        {
            if (item.getPrompt().equals(selectedPrompt)
                    && item.getAnswer().equals(selectedAnswer))
            {
                feedbackLabel.setText("Correct! 正解です！");
                feedbackLabel.setForeground(new Color(40, 130, 70));

                promptModel.removeElement(selectedPrompt);
                answerModel.removeElement(selectedAnswer);

                if (promptModel.isEmpty())
                {
                    feedbackLabel.setText("Great job! Round complete.");
                }

                return;
            }
        }

        feedbackLabel.setText("Incorrect. Try another pair.");
        feedbackLabel.setForeground(new Color(170, 60, 60));
    }

    /**
     * Responds to button clicks on the matching screen.
     *
     * @param e the ActionEvent triggered by the user
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == matchButton)
        {
            checkMatch();
        }
        else if (e.getSource() == newRoundButton)
        {
            createRoundItems();
            updateLists();
        }
        else if (e.getSource() == backButton)
        {
            new SectionMenuScreen(sectionName);
            dispose();
        }
    }
}
