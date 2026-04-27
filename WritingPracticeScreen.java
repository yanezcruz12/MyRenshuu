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
 *   Covers: extends JFrame and implements ActionListener
 * Chapter 8.3: Abstract Classes, Interfaces, and Polymorphism
 *   Covers: implementing the ActionListener interface contract
 * Chapter 13.2: Java GUIs: From AWT to Swing
 *   Covers: why javax.swing classes like JFrame and JPanel are used
 * Chapter 13.3: The Swing Component Set
 *   Covers: JLabel, JTextField, JPanel used to build the practice card
 * Chapter 13.5: The Java Event Model
 *   Covers: addActionListener(this) and actionPerformed(ActionEvent e)
 * Chapter 13.6: Case Study: Designing a Basic GUI
 *   Covers: overall pattern of building a GUI screen with panels and buttons
 * Chapter 13.7: Containers and Layout Managers
 *   Covers: BoxLayout stacking components vertically inside the card panel
 * Chapter 10.7: From the Java Library: JOptionPane
 *   Covers: showMessageDialog used when vocabulary file cannot be loaded
 * Chapter 11.2: Streams and Files
 *   Covers: FileNotFoundException thrown by DataLoader.loadVocabulary()
 *
 * Other References:
 * Oracle. (n.d.). Class JFrame. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html
 *
 * Oracle. (n.d.). Class JOptionPane. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JOptionPane.html
 *
 * Oracle. (n.d.). Class ArrayList. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
 *
 * Oracle. (n.d.). Class String. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html
 *   Covers: .trim(), .toLowerCase(), .equals() used in checkAnswer()
 *
 * Version/date:
 * 1.0 — April 10, 2026  — Class created with basic window and layout designed
 * 1.1 — April 16, 2026 — loadData() and updateQuestion() added, prompt display and text field wired up.
 * 1.2 — April 23, 2026 — checkAnswer() completed with toLowerCase() comparison, Submit, Next, and Back buttons wired up.
 * 1.3 — April 24, 2026 — Javadoc improvements and concept comments added
 *
 * Responsibilities of class:
 * Displays a writing practice screen for the マイ練習 application.
 * This class loads vocabulary items, shows one prompt at a time,
 * accepts typed user input, checks the answer, and lets the user move
 * to the next question or return to the section menu.
 */

// Inheritance: WritingPracticeScreen extends JFrame, receiving all window
// behavior.
// Override: actionPerformed is the custom version for this screen's buttons.
// IS-A: WritingPracticeScreen is a JFrame because it extends JFrame.
// IS-A: WritingPracticeScreen is an ActionListener because it implements
// ActionListener.
public class WritingPracticeScreen extends JFrame implements ActionListener
{
	private String sectionName;
	// HAS-A: WritingPracticeScreen has a section name to know which study
	// section is being used.

	private ArrayList<StudyItem> items;
	// HAS-MANY: WritingPracticeScreen has many StudyItem objects stored in an
	// ArrayList.

	private int currentIndex;
	// HAS-A: WritingPracticeScreen has a current index to track the current
	// question.

	private JLabel titleLabel;
	private JLabel promptLabel;
	private JLabel feedbackLabel;
	// HAS-MANY: WritingPracticeScreen has many JLabel objects to display title,
	// prompt, and feedback.

	private JTextField answerField;
	// HAS-A: WritingPracticeScreen has a JTextField where the user types an
	// answer.

	private RoundedButton submitButton;
	private RoundedButton nextButton;
	private RoundedButton backButton;
	// HAS-MANY: WritingPracticeScreen has many RoundedButton objects for user
	// actions.

	/**
	 * Creates the writing practice screen for the selected section.
	 * This constructor sets up the window, loads vocabulary data, creates
	 * labels, the answer text field, buttons, and connects the buttons to
	 * the ActionListener.
	 *
	 * @param sectionName the name of the section to study
	 */
	public WritingPracticeScreen(String sectionName)
	{
		this.sectionName = sectionName;
		this.items = new ArrayList<>();
		this.currentIndex = 0;

		// Set up the main window for the writing practice screen.
		setTitle("マイ練習 - " + sectionName + " Writing Practice");
		setSize(950, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Load vocabulary data before showing the first practice question.
		loadData();

		// Create color objects used for the background and buttons.
		Color backgroundColor = new Color(252, 251, 247);
		Color buttonColor = new Color(230, 238, 250);

		// Create the main panel that holds all screen components vertically.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(backgroundColor);

		// Create the title label at the top of the screen.
		// This label shows the selected section and tells the user
		// they are in Writing Practice mode.
		titleLabel = new JLabel(sectionName + " Writing Practice");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		titleLabel.setForeground(new Color(44, 74, 120));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the prompt label.
		// This label displays the current vocabulary prompt/question.
		promptLabel = new JLabel("");
		promptLabel.setFont(new Font("Serif", Font.BOLD, 42));
		promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the answer text field.
		// This is where the user types the English meaning.
		answerField = new JTextField();
		answerField.setFont(new Font("SansSerif", Font.PLAIN, 22));
		answerField.setMaximumSize(new java.awt.Dimension(400, 45));
		answerField.setHorizontalAlignment(JTextField.CENTER);

		// Create the feedback label.
		// This label gives instructions first, then displays correct/incorrect
		// feedback.
		feedbackLabel = new JLabel("Type the English meaning.");
		feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		feedbackLabel.setForeground(new Color(90, 90, 90));
		feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the card panel that groups the prompt, answer field,
		// and feedback label into one white practice card.
		JPanel cardPanel = new JPanel();
		cardPanel.setBackground(Color.WHITE);
		cardPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(190, 190, 190), 2),
				BorderFactory.createEmptyBorder(45, 60, 45, 60)));
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardPanel.setMaximumSize(new java.awt.Dimension(550, 300));

		// Add the practice components to the card panel with spacing
		// so the prompt, answer field, and feedback are easy to read.
		cardPanel.add(Box.createVerticalGlue());
		cardPanel.add(promptLabel);
		cardPanel.add(Box.createVerticalStrut(25));
		cardPanel.add(answerField);
		cardPanel.add(Box.createVerticalStrut(20));
		cardPanel.add(feedbackLabel);
		cardPanel.add(Box.createVerticalGlue());

		// Create the buttons for submitting, moving to the next question,
		// and returning to the section menu.
		submitButton = new RoundedButton("Submit（答える）");
		nextButton = new RoundedButton("Next（次へ）");
		backButton = new RoundedButton("Back（戻る）");

		// Store the buttons in an array so they can be styled and connected
		// to the ActionListener using one loop.
		RoundedButton[] buttons = { submitButton, nextButton, backButton };

		// Create a button panel to hold the Submit, Next, and Back buttons.
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		// Apply the same font, background color, and event listener to each
		// button.
		for (RoundedButton button : buttons)
		{
			button.setFont(new Font("SansSerif", Font.BOLD, 18));
			button.setBackground(buttonColor);
			button.addActionListener(this); // register this screen as the event
											// handler
			buttonPanel.add(button);
		}

		// Add the title, practice card, and buttons to the main screen layout.
		mainPanel.add(Box.createVerticalStrut(35));
		mainPanel.add(titleLabel);
		mainPanel.add(Box.createVerticalStrut(40));
		mainPanel.add(cardPanel);
		mainPanel.add(Box.createVerticalStrut(30));
		mainPanel.add(buttonPanel);

		// Add the main panel to the JFrame.
		add(mainPanel);

		// Show the first question after the GUI has been created.
		updateQuestion();

		// Center the window on the screen and make it visible.
		setLocationRelativeTo(null); // center the window on the screen
		setVisible(true); // make the window appear
	}

	/**
	 * Loads vocabulary data from the text file into the items ArrayList.
	 * Calls DataLoader.loadVocabulary and handles the FileNotFoundException
	 * by showing a JOptionPane dialog instead of letting the program crash.
	 * Only loads data when the selected section name is Vocabulary.
	 */
	private void loadData()
	{
		try
		{
			// Only load vocabulary data when the selected section is
			// Vocabulary.
			if (sectionName.equals("Vocabulary"))
			{
				items = DataLoader.loadVocabulary("vocabulary.txt");
			}
		}
		catch (FileNotFoundException e)
		{
			// Show a message box instead of letting the program crash if the
			// file is missing.
			JOptionPane.showMessageDialog(this,
					"Could not load vocabulary data file.");
		}
	}

	/**
	 * Updates the screen to show the current study prompt.
	 * This method gets the current StudyItem from the ArrayList, displays
	 * its prompt, clears the answer field, and resets the feedback message.
	 */
	private void updateQuestion()
	{
		// If no study items were loaded, show an error message on the screen.
		if (items.isEmpty())
		{
			promptLabel.setText("No items found.");
			feedbackLabel.setText("Check your vocabulary.txt file.");
			return;
		}

		// Get the current StudyItem based on the current index.
		StudyItem currentItem = items.get(currentIndex);

		// Display the current prompt and prepare the text field for a new
		// answer.
		promptLabel.setText(currentItem.getPrompt());
		answerField.setText("");
		feedbackLabel.setText("Type the English meaning.");

		// Place the cursor in the answer field so the user can start typing.
		answerField.requestFocusInWindow();
	}

	/**
	 * Checks the user's typed answer against the current answer.
	 * This method compares the user's input with the answer stored in
	 * the current StudyItem. Both answers are changed to lowercase before
	 * comparison so capitalization does not affect the result.
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
		// Convert both to lowercase so the comparison is not case-sensitive.
		// Variable: userAnswer and correctAnswer are local — only alive inside
		// checkAnswer().
		// String: .toLowerCase() makes the comparison case-insensitive.
		String userAnswer = answerField.getText().trim().toLowerCase();
		String correctAnswer = currentItem.getAnswer().trim().toLowerCase();

		// Compare the user's answer with the correct answer and update
		// feedback.
		// if-else Statement: two possible paths — correct or incorrect
		// feedback.
		// String: .equals() compares text content, not memory location.
		if (userAnswer.equals(correctAnswer))
		{
			feedbackLabel.setText("Correct! 正解です！");
			feedbackLabel.setForeground(new Color(40, 130, 70));
		}
		else
		{
			feedbackLabel
					.setText("Incorrect. Answer: " + currentItem.getAnswer());
			feedbackLabel.setForeground(new Color(170, 60, 60));
		}
	}

	/**
	 * Responds to button clicks on the writing practice screen.
	 * If Submit is clicked, the answer is checked. If Next is clicked,
	 * the next question is shown. If Back is clicked, the user returns
	 * to the section menu.
	 *
	 * @param e the ActionEvent fired by the button the user clicked,
	 *          containing the source component for identification
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// If Submit is clicked, check the user's typed answer.
		if (e.getSource() == submitButton)
		{
			checkAnswer();
		}
		// If Next is clicked, move to the next question.
		else if (e.getSource() == nextButton)
		{
			if (!items.isEmpty())
			{
				currentIndex = (currentIndex + 1) % items.size(); // wrap back to 0 at end
																	
				updateQuestion();
			}
		}
		// If Back is clicked, return to the section menu screen.
		else if (e.getSource() == backButton)
		{
			new SectionMenuScreen(sectionName); // open the section menu
			dispose(); // close and release resources for this screen
		}
	}
}