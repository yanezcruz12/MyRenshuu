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
 *   Covers: JLabel, JList, JScrollPane, JPanel used to build the matching layout
 * Chapter 13.5: The Java Event Model
 *   Covers: addActionListener(this) and actionPerformed(ActionEvent e)
 * Chapter 13.6: Case Study: Designing a Basic GUI
 *   Covers: overall pattern of building a GUI screen with panels and buttons
 * Chapter 13.7: Containers and Layout Managers
 *   Covers: GridLayout(1, 2, 30, 0) placing two list panels side by side
 * Chapter 10.7: From the Java Library: JOptionPane
 *   Covers: showMessageDialog used when vocabulary file cannot be loaded
 * Chapter 16.6: From the Java Library: The Java Collections Framework
 *   Covers: Collections.shuffle() used to randomize prompts and answers independently
 *
 * Other References:
 * Oracle. (n.d.). Class JFrame. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html
 *
 * Oracle. (n.d.). Class JList. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JList.html
 *
 * Oracle. (n.d.). Class DefaultListModel. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/DefaultListModel.html
 *
 * Oracle. (n.d.). Class ArrayList. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
 *
 * Oracle. (n.d.). Class Collections. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html
 *
 * Oracle. (n.d.). Class Math. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Math.html
 *   Covers: Math.min(5, shuffledItems.size()) to safely cap the round size
 *
 * Kenny Yip Coding. (2024, July 29). Code Memory Card Game in Java [Video]. YouTube.
 * Retrieved from https://www.youtube.com/watch?v=FxDirbh3tXc
 *
 * Version/date:
 * 1.0 — April 11, 2026  — Class created, two-list layout designed,
                        JList and DefaultListModel as well
 * 1.1 — April 12, 2026 — createRoundItems() added using Collections.shuffle()
                        and Math.min() to safely select 5 random items,
                        updateLists() added to independently shuffle prompts
                        and answers so they do not line up
 * 1.2 — April 23, 2026 — checkMatch() completed using && to verify both
                        prompt and answer match simultaneously,
                        Match, New Round, and Back buttons connected
                        to ActionListener
 * 1.3 — April 25, 2026 — Added clearSelection() after incorrect match attempt
 *                      Javadoc comments and concepts updated
 *                      
 *
 * Responsibilities of class:
 * Displays a matching practice screen for the マイ練習 application.
 * This class loads vocabulary items, presents a shuffled set of Japanese
 * prompts and English answers, and checks whether the selected pair is correct.
 */

// Inheritance: MatchingScreen extends JFrame, receiving all window behavior.
// Subclass: MatchingScreen is the child class, JFrame is the superclass.
// Polymorphism: actionPerformed behaves differently here than in other screens.
// IS-A: MatchingScreen is a JFrame because it extends JFrame.
// IS-A: MatchingScreen is an ActionListener because it implements
// ActionListener.
public class MatchingScreen extends JFrame implements ActionListener
{
	private String sectionName;
	// HAS-A: MatchingScreen has a section name to know which study section is
	// being used.

	// Instance Variable: items belongs to this object and holds all vocabulary
	// loaded from file.
	// Private: items cannot be accessed or changed from outside this class.
	private ArrayList<StudyItem> items;
	// HAS-MANY: MatchingScreen has many StudyItem objects loaded from the data
	// file.

	// Instance Variable: roundItems stores only the 5 objects chosen for this
	// round.
	private ArrayList<StudyItem> roundItems;
	// HAS-MANY: MatchingScreen has a smaller group of StudyItem objects for the
	// current round.

	private JList<String> promptList;
	private JList<String> answerList;
	// HAS-MANY: MatchingScreen has two JList objects to display the prompts and
	// answers.

	private DefaultListModel<String> promptModel;
	private DefaultListModel<String> answerModel;
	// HAS-MANY: MatchingScreen has two DefaultListModel objects to manage the
	// list data.

	private JLabel feedbackLabel;
	// HAS-A: MatchingScreen has a JLabel to display match feedback to the user.

	private RoundedButton matchButton;
	private RoundedButton newRoundButton;
	private RoundedButton backButton;
	// HAS-MANY: MatchingScreen has many RoundedButton objects for user actions.

	/**
	 * Creates and displays the matching practice screen for the selected
	 * section.
	 * This constructor initializes both ArrayLists, loads vocabulary data from
	 * disk,
	 * builds the two-column JList layout, creates the Match, New Round, and
	 * Back
	 * buttons, connects each button to this ActionListener, and makes the
	 * window visible.
	 *
	 * @param sectionName the name of the study section, such as Vocabulary
	 */
	public MatchingScreen(String sectionName)
	{
		// Store the section name and initialize both ArrayLists as empty.
		this.sectionName = sectionName;
		this.items = new ArrayList<>();
		this.roundItems = new ArrayList<>();

		// Configure the window title, size, and close behavior.
		setTitle("マイ練習 - " + sectionName + " Matching");
		setSize(950, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Load all vocabulary from the file, then pick the first round subset.
		loadData();
		createRoundItems();

		// Define the background color for the screen and the button fill color.
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

		// Create the data models first, then link each JList to its model.
		// Changes to the model automatically update the visible list.
		promptModel = new DefaultListModel<>();
		answerModel = new DefaultListModel<>();

		promptList = new JList<>(promptModel);
		answerList = new JList<>(answerModel);

		promptList.setFont(new Font("Serif", Font.BOLD, 24));
		answerList.setFont(new Font("SansSerif", Font.PLAIN, 22));

		// Wrap each JList in a JScrollPane so the user can scroll if the list
		// is long.
		JScrollPane promptScrollPane = new JScrollPane(promptList);
		JScrollPane answerScrollPane = new JScrollPane(answerList);

		JPanel listsPanel = new JPanel(new GridLayout(1, 2, 30, 0));
		listsPanel.setBackground(backgroundColor);
		listsPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

		// Build one labeled panel for each list using the helper method.
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

		// Store all buttons in an array so the same style and listener can be
		// applied to each one using a single loop instead of repeating code.
		RoundedButton[] buttons = { matchButton, newRoundButton, backButton };
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		for (RoundedButton button : buttons)
		{
			button.setFont(new Font("SansSerif", Font.BOLD, 18));
			button.setBackground(buttonColor);
			button.addActionListener(this); // registers this screen as the event listener
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
	 * Creates a labeled JPanel that wraps one scrollable JList for the matching
	 * screen.
	 * This helper method is called twice — once for the Japanese prompt list on
	 * the left
	 * and once for the English answer list on the right — keeping the layout
	 * code DRY.
	 *
	 * @param title      the heading text displayed above the list, such as
	 *                   Japanese or English
	 * @param scrollPane the JScrollPane that wraps the JList to be displayed
	 * @return a JPanel containing the title label stacked above the scroll pane
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
	 * Loads vocabulary data from the text file into the items ArrayList.
	 * Calls DataLoader.loadVocabulary and handles the FileNotFoundException
	 * by showing a dialog instead of allowing the program to crash.
	 * Only loads data when the selected section is Vocabulary.
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
	 * Selects a randomly shuffled subset of up to five StudyItems for the
	 * current round.
	 * This method copies the full items list, shuffles the copy using
	 * Collections.shuffle,
	 * and picks the first five from that shuffled copy. Using Math.min ensures
	 * the program
	 * does not crash if the vocabulary file contains fewer than five items.
	 */
	private void createRoundItems()
	{
		// Method: clear() is a built-in method that removes all elements from
		// the list.
		// Clear any items from the previous round before building a new one.
		roundItems.clear();

		// Copy the full items list so the original is never modified.
		ArrayList<StudyItem> shuffledItems = new ArrayList<>(items);

		// Method: Collections.shuffle() is a built-in method that randomly
		// reorders the list.
		// Randomly reorder the copy so each round is different.
		Collections.shuffle(shuffledItems);

		// Variable: numberOfItems stores the safe upper limit for this round.
		// if Statement: Math.min acts like a condition — pick 5 or the actual
		// size, whichever is smaller.
		// Use Math.min to avoid an IndexOutOfBoundsException if the file
		// has fewer than five vocabulary items.
		int numberOfItems = Math.min(5, shuffledItems.size());

		// for Loop: repeats a set number of times using a counter variable i.
		// Take only the first 'numberOfItems' from the shuffled copy.
		for (int i = 0; i < numberOfItems; i++)
		{
			roundItems.add(shuffledItems.get(i));
		}
	}

	/**
	 * Clears both DefaultListModels and repopulates them with independently
	 * shuffled
	 * prompts and answers from the current roundItems. Shuffling the two lists
	 * separately
	 * ensures that prompts and answers are not aligned with each other, which
	 * is what
	 * creates the matching challenge for the user.
	 */
	private void updateLists()
	{
		// Clear both list models so the previous round's items are removed.
		promptModel.clear();
		answerModel.clear();

		// Collect prompts and answers into separate lists for independent
		// shuffling.
		ArrayList<String> prompts = new ArrayList<>();
		ArrayList<String> answers = new ArrayList<>();

		for (StudyItem item : roundItems)
		{
			prompts.add(item.getPrompt());
			answers.add(item.getAnswer());
		}

		// Shuffle each list independently so prompts and answers are not
		// aligned.
		// This is what creates the matching challenge for the user.
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
	 * Checks whether the item selected in the prompt list matches the item
	 * selected
	 * in the answer list. If both selections belong to the same StudyItem in
	 * roundItems,
	 * the pair is correct and both entries are removed from their respective
	 * models.
	 * If either list has nothing selected, a warning message is shown instead.
	 */
	private void checkMatch()
	{
		// Variable: selectedPrompt and selectedAnswer store what the user
		// clicked.
		// Get whatever the user has currently selected in each list.
		String selectedPrompt = promptList.getSelectedValue();
		String selectedAnswer = answerList.getSelectedValue();

		// if Statement: checks whether the user forgot to select from one of
		// the lists.
		// If either list has nothing selected, remind the user and exit early.
		if (selectedPrompt == null || selectedAnswer == null)
		{
			feedbackLabel.setText("Please select one item from each list.");
			feedbackLabel.setForeground(new Color(170, 100, 40));
			return;
		}

		// for Loop: loops through every StudyItem in roundItems to find a match.
		// if Statement: both conditions must be true at the same time using &&.
		// Search roundItems for a StudyItem whose prompt AND answer both match
		// what the userr selected. Both must match at the same time to be correct.
		for (StudyItem item : roundItems)
		{
			if (item.getPrompt().equals(selectedPrompt)
					&& item.getAnswer().equals(selectedAnswer))
			{
				// Correct pair found — show success feedback.
				feedbackLabel.setText("Correct! 正解です！");
				feedbackLabel.setForeground(new Color(40, 130, 70));

				// Remove the matched pair from both visible lists.
				promptModel.removeElement(selectedPrompt);
				answerModel.removeElement(selectedAnswer);

				// Check if the round is complete (all pairs matched).
				if (promptModel.isEmpty())
				{
					feedbackLabel.setText("Great job! Round complete.");
				}

				return; // exit the loop once the correct pair is found
			}
		}

		// No matching pair was found — show incorrect feedback.
		feedbackLabel.setText("Incorrect. Try another pair.");
		feedbackLabel.setForeground(new Color(170, 60, 60));

		// Clear both selections so the user starts fresh for the next attempt.
		promptList.clearSelection();
		answerList.clearSelection();
	}

	/**
	 * Responds to button clicks on the matching screen.
	 * Calls checkMatch when Match is clicked, starts a new round when New Round
	 * is clicked, and returns to the section menu when Back is clicked.
	 *
	 * @param e the ActionEvent fired by the button the user clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Match button — check whether the selected pair is correct.
		if (e.getSource() == matchButton)
		{
			checkMatch();
		}
		// New Round button — pick a fresh random subset and redisplay both
		// lists.
		else if (e.getSource() == newRoundButton)
		{
			createRoundItems();
			updateLists();
		}
		// Back button — open the section menu and close this screen.
		else if (e.getSource() == backButton)
		{
			new SectionMenuScreen(sectionName);
			dispose(); // release this window's resources
		}
	}
}