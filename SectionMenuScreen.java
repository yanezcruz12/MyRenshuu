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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Lead Author(s):
 * @author Cruz Yanez
 *
 * Other contributors:
 * None
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java:
 * Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * Chapter 8.2: Java's Inheritance Mechanism
 *   Covers: extends JFrame and implements ActionListener
 * Chapter 8.3: Abstract Classes, Interfaces, and Polymorphism
 *   Covers: implementing the ActionListener interface contract
 * Chapter 13.2: Java GUIs: From AWT to Swing
 *   Covers: why javax.swing classes like JFrame and JPanel are used
 * Chapter 13.3: The Swing Component Set
 *   Covers: JLabel, JPanel, and RoundedButton which extends JButton
 * Chapter 13.5: The Java Event Model
 *   Covers: addActionListener(this) and actionPerformed(ActionEvent e)
 * Chapter 13.6: Case Study: Designing a Basic GUI
 *   Covers: overall pattern of building a GUI screen with panels and listeners
 * Chapter 13.7: Containers and Layout Managers
 *   Covers: GridLayout(5, 1, 15, 15) — rows, columns, horizontal and vertical gap
 *
 * Other References:
 * Oracle. (n.d.). Class JFrame. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html
 *
 * Oracle. (n.d.). Class JPanel. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JPanel.html
 *
 * Oracle. (n.d.). Class JLabel. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JLabel.html
 *
 * Oracle. (n.d.). Class GridLayout. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/GridLayout.html
 *
 * Oracle. (n.d.). Interface ActionListener. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/ActionListener.html
 *
 * Version/date: 
 * 1.0 — April 9, 2026  — Class created, five practice mode buttons designed
 *                         sectionName field added to pass section through navigation
 * 1.1 — April 16, 2026 — All four practice screens connected to their buttons,
 *                         green color theme applied to distinguish from HomeScreen
 * 1.2 — April 23, 2026 — Back button existed but handler was missing
 *                         from actionPerformed.
 * 1.3 — April 24, 2026 — Back button handler added to actionPerformed
 *                         so it correctly returns to HomeScreen,
 *                         Javadoc comments and concepts added
 *
 * Responsibilities of class:
 * Displays a section menu screen that allows the user to choose
 * a practice mode for the Vocabulary study section.
 */
 
// Inheritance: SectionMenuScreen extends JFrame, inheriting all window
// behavior.
// Polymorphism: actionPerformed behaves differently here than in other screens.
// IS-A: SectionMenuScreen is a JFrame because it extends JFrame.
// IS-A: SectionMenuScreen is an ActionListener because it implements
// ActionListener.
public class SectionMenuScreen extends JFrame implements ActionListener
{
	private String sectionName;
	// HAS-A: SectionMenuScreen has a section name to track the selected
	// category.

	private RoundedButton flashcardsButton;
	private RoundedButton writingButton;
	private RoundedButton matchingButton;
	private RoundedButton fillBlankButton;
	private RoundedButton backButton;
	// HAS-MANY: SectionMenuScreen has multiple RoundedButton objects for menu
	// options.

	/**
	 * Creates the section menu screen for the selected section.
	 * This constructor builds the GUI, creates labels, buttons, and connects
	 * button actions to the ActionListener.
	 *
	 * @param sectionName the name of the study section
	 */
	public SectionMenuScreen(String sectionName)
	{
		this.sectionName = sectionName;

		// Set up the main window for the section menu.
		setTitle("マイ練習 - " + sectionName);
		setSize(950, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create colors used for the background and buttons.
		Color backgroundColor = new Color(245, 250, 248);
		Color buttonColor = new Color(215, 235, 225);

		// Create the main panel that holds all components vertically.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(backgroundColor);

		// Create the title label showing the selected section.
		JLabel titleLabel = new JLabel(sectionName + " Practice");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
		titleLabel.setForeground(new Color(40, 100, 90));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create a subtitle label explaining what to do next.
		JLabel subtitleLabel = new JLabel("Choose a practice mode");
		subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		subtitleLabel.setForeground(new Color(90, 90, 90));
		subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add labels to the main panel with spacing.
		mainPanel.add(Box.createVerticalStrut(40));
		mainPanel.add(titleLabel);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(subtitleLabel);
		mainPanel.add(Box.createVerticalStrut(30));

		// Create a button panel using GridLayout.
		// This arranges buttons vertically with equal spacing.
		JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 15, 15));
		buttonPanel.setBackground(backgroundColor);
		buttonPanel
				.setBorder(BorderFactory.createEmptyBorder(20, 180, 20, 180));

		// Create buttons for each practice mode and navigation.
		flashcardsButton = new RoundedButton("Flashcards（フラッシュカード）");
		writingButton = new RoundedButton("Writing Practice（書く練習）");
		matchingButton = new RoundedButton("Matching（マッチング）");
		fillBlankButton = new RoundedButton("Fill in the Blank（穴埋め）");
		backButton = new RoundedButton("Back（戻る）");

		// Store buttons in an array to apply consistent formatting.
		RoundedButton[] buttons = { flashcardsButton, writingButton,
				matchingButton, fillBlankButton, backButton };

		// Apply styling and event handling to each button.
		for (RoundedButton button : buttons)
		{
			button.setFont(new Font("SansSerif", Font.BOLD, 18));
			button.setBackground(buttonColor);
			button.setForeground(Color.BLACK);
			button.addActionListener(this); // register this screen as the event
											// handler
			buttonPanel.add(button);
		}

		// Add the button panel to the main layout.
		mainPanel.add(buttonPanel);

		// Add the main panel to the frame.
		add(mainPanel);

		// Center and display the window.
		setLocationRelativeTo(null); // centers the window on the screen
		setVisible(true); // make the window appear
	}

	/**
	 * Responds to button clicks on the section menu screen.
	 * Each button opens a different practice mode or shows a message
	 * if the feature is not yet implemented.
	 *
	 * @param e the ActionEvent triggered by the user
	 */
	// Note: sectionName is passed in from HomeScreen so each section can
	// open the correct practice screens. Vocabulary is fully implemented.
	// Grammar, Kanji, and Conjugations will load their own data files
	// when those sections are built in a future update.
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// if-else Statement: checks which button was clicked and opens the
		// correct screen.
		if (e.getSource() == flashcardsButton)
		{
			new FlashcardScreen(sectionName);
			dispose();
		}
		else if (e.getSource() == writingButton)
		{
			new WritingPracticeScreen(sectionName);
			dispose();
		}
		else if (e.getSource() == matchingButton)
		{
			new MatchingScreen(sectionName);
			dispose();
		}
		else if (e.getSource() == fillBlankButton)
		{
			new FillInBlankScreen(sectionName);
			dispose();
		}
		else if (e.getSource() == backButton)
		{
			new HomeScreen();
			dispose();
		}
	}
}