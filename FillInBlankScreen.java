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
 * @author Cruz Yanez
 *
 * Other contributors:
 * None
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * Chapter 8.2: Java's Inheritance Mechanism
 * Chapter 8.3: Abstract Classes, Interfaces, and Polymorphism
 * Chapter 13.2: Java GUIs: From AWT to Swing
 * Chapter 13.3: The Swing Component Set
 * Chapter 13.5: The Java Event Model
 * Chapter 13.6: Case Study: Designing a Basic GUI
 * Chapter 13.7: Containers and Layout Managers
 * Chapter 10.7: From the Java Library: JOptionPane
 * Chapter 5.2: Boolean Data and Operators
 *   Covers: ! operator used in if (!items.isEmpty())
 * Chapter 11.2: Streams and Files
 * Chapter 11.3: Case Study: Reading and Writing Text Files
 *   Covers: FileNotFoundException handling in loadData()
 *
 *Other References
 * Oracle. (n.d.). Class JFrame. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html
 *
 * Oracle. (n.d.). Class ArrayList. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
 *
 * Oracle. (n.d.). Class FileNotFoundException. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileNotFoundException.html
 *
 * Oracle. (n.d.). Class JOptionPane. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JOptionPane.html
 *
 * Oracle. (n.d.). Class String. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html
 *   Covers: .trim(), .toLowerCase(), .equals() used in checkAnswer()
 *
 * Version/date:
 * 1.0 — April 9, 2026  — Class created, basic window and layout designed
 * 1.1 — April 16, 2026 — loadData() added, vocabulary_fill_blank.txt format decided
 *                         updateQuestion() written to display clue and blank sentence
 * 1.2 — April 23, 2026 — checkAnswer() completed, Submit and Next buttons wired up
 *                         Back button navigation added, submitted for checkpoint
 * 1.3 — April 25, 2026 — Fixed getAnswer() bug in checkAnswer() due to 
 *                         getPrompt() returning the full sentence instead of the word
 *                         Added toLowerCase() so capitalization does not count against use
 *
 * Responsibilities of class:
 * Displays a fill-in-the-blank practice screen for the マイ練習 application.
 * This class loads vocabulary items, shows a clue and blank sentence,
 * accepts user input, checks the answer, and allows the user to move
 * between questions.
 */
 
// Inheritance: FillInBlankScreen extends JFrame, receiving all window behavior.
// Subclass: FillInBlankScreen is the child, JFrame is the superclass.
// Override: actionPerformed is provided here to fulfill the ActionListener
// contract.
// IS-A: FillInBlankScreen is a JFrame because it extends JFrame.
// IS-A: FillInBlankScreen is an ActionListener because it implements
// ActionListener.
public class FillInBlankScreen extends JFrame implements ActionListener
{
    private String sectionName;
    // HAS-A: FillInBlankScreen has a section name to know which study section is being used.
 
    private ArrayList<StudyItem> items;
    // HAS-MANY: FillInBlankScreen has many StudyItem objects stored in an ArrayList.
 
    // Instance Variable: currentIndex belongs to this object and tracks which question is showing.
    // Variable: starts at 0 and increases each time Next is clicked.
    private int currentIndex;
    // HAS-A: FillInBlankScreen has a current index to track the current question.
 
    private JLabel titleLabel;
    private JLabel clueLabel;
    private JLabel blankLabel;
    private JLabel feedbackLabel;
    // HAS-MANY: FillInBlankScreen has many JLabel objects to display text on the screen.
 
    private JTextField answerField;
    // HAS-A: FillInBlankScreen has a JTextField where the user types an answer.
 
    private RoundedButton submitButton;
    private RoundedButton nextButton;
    private RoundedButton backButton;
    // HAS-MANY: FillInBlankScreen has many RoundedButton objects for user actions.
 
    /**
     * Creates the fill-in-the-blank screen for the selected section.
     * This constructor sets up the window, loads the data, creates the labels,
     * text field, buttons, panels, and connects the buttons to the ActionListener.
     *
     * @param sectionName the name of the section to study
     */
    public FillInBlankScreen(String sectionName)
    {
        this.sectionName = sectionName;
        this.items = new ArrayList<>();
        this.currentIndex = 0;
 
        // Set up the main window for the fill-in-the-blank screen.
        setTitle("マイ練習 - " + sectionName + " Fill in the Blank");
        setSize(950, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
 
        // Load the vocabulary data before the screen displays the first question.
        loadData();
 
        // Create color objects used for the screen background and buttons.
        Color backgroundColor = new Color(252, 251, 247);
        Color buttonColor = new Color(230, 238, 250);
 
        // Create the main panel that holds all screen components vertically.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
 
        // Create the title label at the top of the screen.
        // This label shows the selected section name and tells the user
        // they are in Fill in the Blank mode.
        titleLabel = new JLabel(sectionName + " Fill in the Blank");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(44, 74, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // Create the clue label.
        // This label will later display the English translation or clue
        // for the current vocabulary item.
        clueLabel = new JLabel("");
        clueLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        clueLabel.setForeground(new Color(70, 70, 70));
        clueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // Create the blank label.
        // This label displays the sentence or blank area connected to the question.
        blankLabel = new JLabel("______");
        blankLabel.setFont(new Font("Serif", Font.BOLD, 18));
        blankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // Create the answer text field.
        // This is where the user types the missing Japanese vocabulary word.
        answerField = new JTextField();
        answerField.setFont(new Font("Serif", Font.PLAIN, 24));
        answerField.setMaximumSize(new java.awt.Dimension(400, 45));
        answerField.setHorizontalAlignment(JTextField.CENTER);
 
        // Create the feedback label.
        // This label tells the user what to do first, then later updates
        // to show whether the answer was correct or incorrect.
        feedbackLabel = new JLabel("Type the Japanese vocabulary word.");
        feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        feedbackLabel.setForeground(new Color(90, 90, 90));
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // Create the card panel that groups the clue, blank, answer field,
        // and feedback label into one white practice card.
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 2),
                BorderFactory.createEmptyBorder(45, 60, 45, 60)));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setMaximumSize(new java.awt.Dimension(600, 330));
 
        // Add the question components to the card panel with spacing
        // so the screen is easier to read.
        cardPanel.add(Box.createVerticalGlue());
        cardPanel.add(clueLabel);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(blankLabel);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(answerField);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(feedbackLabel);
        cardPanel.add(Box.createVerticalGlue());
 
        // Create the navigation and action buttons for this screen.
        submitButton = new RoundedButton("Submit（答える）");
        nextButton = new RoundedButton("Next（次へ）");
        backButton = new RoundedButton("Back（戻る）");
 
        // Store the buttons in an array so the same formatting and
        // ActionListener can be applied to each button using one loop.
        RoundedButton[] buttons = { submitButton, nextButton, backButton };
 
        // Create a button panel to hold the Submit, Next, and Back buttons.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
 
        // Apply the same font, background color, and event listener to each button.
        for (RoundedButton button : buttons)
        {
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            button.setBackground(buttonColor);
            button.addActionListener(this); // register this screen as the event handler
            buttonPanel.add(button);
        }
 
        // Add the title, card, and button panel to the main screen layout.
        mainPanel.add(Box.createVerticalStrut(35));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(cardPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(buttonPanel);
 
        // Add the main panel to the JFrame.
        add(mainPanel);
 
        // Display the first question after the GUI components are created.
        updateQuestion();
 
        // Center the window on the screen and make it visible.
        setLocationRelativeTo(null); // center the window on the screen
        setVisible(true);           // make the window appear
    }
 
    /**
     * Loads fill-in-the-blank vocabulary data from a text file.
     * This method calls DataLoader.loadFillBlank and handles the error if
     * the file cannot be found.
     */
    private void loadData()
    {
        try
        {
            // Only load fill-in-the-blank data when the selected section is Vocabulary.
            if (sectionName.equals("Vocabulary"))
            {
                items = DataLoader.loadFillBlank("vocabulary_fill_blank.txt");
            }
        }
        catch (FileNotFoundException e)
        {
            // Show a message box instead of letting the program crash if the file is missing.
            JOptionPane.showMessageDialog(this,
                    "Could not load vocabulary data file.");
        }
    }
 
    /**
     * Updates the screen with the current fill-in-the-blank item.
     * This method gets the current StudyItem from the ArrayList and displays
     * the clue, blank sentence, and default feedback message.
     */
    private void updateQuestion()
    {
        // if Statement: guards against accessing an empty list which would cause a crash.
        // If the file did not load any items, show an error message on the screen.
        if (items.isEmpty())
        {
            clueLabel.setText("No items found.");
            blankLabel.setText("______");
            feedbackLabel.setText("Check your vocabulary.txt file.");
            return;
        }
 
        // Get the current StudyItem based on the current index.
        StudyItem currentItem = items.get(currentIndex);
 
        // Display the clue and blank prompt for the current question.
        clueLabel.setText(currentItem.getExtra());
        blankLabel.setText(currentItem.getPrompt());
 
        // Clear the answer field and reset the feedback message for the next question.
        answerField.setText("");
        feedbackLabel.setText("Type the missing Japanese word.");
        feedbackLabel.setForeground(new Color(90, 90, 90));
 
        // Place the cursor in the answer field so the user can start typing.
        answerField.requestFocusInWindow();
    }
 
    /**
     * Checks the user's typed answer.
     * This method compares the text entered by the user with the correct
     * answer stored in the current StudyItem object.
     */
    private void checkAnswer()
    {
        // Do not check an answer if no study items were loaded.
        if (items.isEmpty())
        {
            return;
        }
 
        // Get the current StudyItem so the answer can be checked.
        StudyItem currentItem = items.get(currentIndex);
 
        // Get the user's answer and the correct answer without extra spaces.
        // Note: in fill-in-the-blank the missing word is stored in getAnswer()
        // not getPrompt() — getPrompt() holds the full sentence with the blank.
        // Variable: userAnswer and correctAnswer are local variables — only alive inside checkAnswer().
        // String: .trim() removes spaces, .toLowerCase() makes comparison case-insensitive.
        // Convert both to lowercase so capitalization does not count against the user.
        String userAnswer = answerField.getText().trim().toLowerCase();
        String correctAnswer = currentItem.getAnswer().trim().toLowerCase();
 
        // if-else Statement: chooses between correct and incorrect feedback paths.
        // String: .equals() compares the actual characters — never use == for Strings.
        // Compare the user's answer with the correct answer and update feedback.
        if (userAnswer.equals(correctAnswer))
        {
            feedbackLabel.setText("Correct! 正解です！");
            feedbackLabel.setForeground(new Color(40, 130, 70));
        }
        else
        {
            feedbackLabel.setText("Incorrect. Answer: " + correctAnswer);
            feedbackLabel.setForeground(new Color(170, 60, 60));
        }
    }
 
    /**
     * Responds to button clicks on the fill-in-the-blank screen.
     * If Submit is clicked, the answer is checked. If Next is clicked, the
     * next question is shown. If Back is clicked, the user returns to the
     * section menu.
     *
     * @param e the ActionEvent fired by the button the user clicked,
     *             containing the source component for identification
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // If the Submit button is clicked, check the user's answer.
        if (e.getSource() == submitButton)
        {
            checkAnswer();
        }
        // If the Next button is clicked, move to the next question.
        else if (e.getSource() == nextButton)
        {
            if (!items.isEmpty())
            {
                // Variable: currentIndex is updated each time Next is clicked.
                // while Loop equivalent: % wraps the counter back to 0 at the end.
                currentIndex = (currentIndex + 1) % items.size(); // wrap back to 0 at end
                updateQuestion();
            }
        }
        // If the Back button is clicked, return to the section menu screen.
        else if (e.getSource() == backButton)
        {
            new SectionMenuScreen(sectionName); // open the section menu
            dispose(); // close and release resources for this screen
        }
    }
}