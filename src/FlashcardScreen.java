import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * Represents a flashcard practice screen for one study section.
 * This screen displays one study item at a time and allows the user
 * to flip to the answer and move to the next card.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class FlashcardScreen extends JFrame implements ActionListener
{
    private String sectionName;
    private ArrayList<StudyItem> items;
    private int currentIndex;
    private boolean showingAnswer;

    private JLabel sectionLabel;
    private JLabel cardLabel;
    private JLabel extraLabel;

    private RoundedButton flipButton;
    private RoundedButton nextButton;
    private RoundedButton backButton;

    /**
     * Creates a flashcard screen for the selected section.
     *
     * @param sectionName the name of the section to study
     */
    public FlashcardScreen(String sectionName)
    {
        this.sectionName = sectionName;
        this.items = new ArrayList<>();
        this.currentIndex = 0;
        this.showingAnswer = false;

        setTitle("マイ練習 - " + sectionName + " Flashcards");
        setSize(950, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadData();

        Color backgroundColor = new Color(252, 251, 247);
        Color buttonColor = new Color(230, 238, 250);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);

        sectionLabel = new JLabel(sectionName + " Flashcards");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        sectionLabel.setForeground(new Color(44, 74, 120));
        sectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLabel = new JLabel("", JLabel.CENTER);
        cardLabel.setFont(new Font("Serif", Font.BOLD, 34));
        cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        extraLabel = new JLabel(" ");
        extraLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        extraLabel.setForeground(new Color(100, 100, 100));
        extraLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 2),
                BorderFactory.createEmptyBorder(60, 60, 60, 60)));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setMaximumSize(new java.awt.Dimension(500, 250));

        cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        extraLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(Box.createVerticalGlue());
        cardPanel.add(cardLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(extraLabel);
        cardPanel.add(Box.createVerticalGlue());

        flipButton = new RoundedButton("Flip（めくる）");
        nextButton = new RoundedButton("Next（次へ）");
        backButton = new RoundedButton("Back（戻る）");

        RoundedButton[] buttons = { flipButton, nextButton, backButton };
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
        mainPanel.add(sectionLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(cardPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        updateCard();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Loads the data for the flashcard section.
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
     * Updates the flashcard display based on the current item.
     */
    private void updateCard()
    {
        if (items.isEmpty())
        {
            cardLabel.setText("No study items found.");
            extraLabel.setText(" ");
            return;
        }

        StudyItem currentItem = items.get(currentIndex);

        if (!showingAnswer)
        {
            cardLabel.setText(currentItem.getPrompt());
            extraLabel.setText(" ");
        }
        else
        {
            cardLabel.setText(currentItem.getAnswer());

            if (currentItem.getExtra() != null && !currentItem.getExtra().isEmpty())
            {
                extraLabel.setText(currentItem.getExtra());
            }
            else
            {
                extraLabel.setText(" ");
            }
        }
    }

    /**
     * Responds to button clicks on the flashcard screen.
     *
     * @param e the ActionEvent triggered by the user
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == flipButton)
        {
            showingAnswer = !showingAnswer;
            updateCard();
        }
        else if (e.getSource() == nextButton)
        {
            if (!items.isEmpty())
            {
                currentIndex = (currentIndex + 1) % items.size();
                showingAnswer = false;
                updateCard();
            }
        }
        else if (e.getSource() == backButton)
        {
            new SectionMenuScreen(sectionName);
            dispose();
        }
    }
}
