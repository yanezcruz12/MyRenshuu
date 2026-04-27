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
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * Chapter 8.2: Java's Inheritance Mechanism
 * Chapter 8.3: Abstract Classes, Interfaces, and Polymorphism
 * Chapter 13.2: Java GUIs: From AWT to Swing
 * Chapter 13.3: The Swing Component Set
 * Chapter 13.5: The Java Event Model
 * Chapter 13.6: Case Study: Designing a Basic GUI
 * Chapter 13.7: Containers and Layout Managers
 *
 * Other References:
 * Oracle. (n.d.). Class JFrame. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html
 *
 * Oracle. (n.d.). Class JOptionPane. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JOptionPane.html
 *
 * Version/date:
 * 1.0 — April 16, 2026 — Initial version created
 * 1.1 — April 19, 2026 — All six section buttons added
 * 1.2 — April 25, 2026 — Restored all buttons after accidental removal, 
 *                        citations and concept comments updated
 *
 * Responsibilities of class:
 * Displays the main home screen for the マイ練習 application.
 * This class allows the user to choose a study section, view future
 * features, or exit the program.
 */

// Inheritance: HomeScreen extends JFrame, inheriting all window behavior for
// free.
// Subclass: HomeScreen is the child class, JFrame is the superclass.
// IS-A: HomeScreen is a JFrame because it extends JFrame.
// IS-A: HomeScreen is an ActionListener because it implements ActionListener.
public class HomeScreen extends JFrame implements ActionListener
{
	private RoundedButton vocabularyButton;
	private RoundedButton grammarButton;
	private RoundedButton kanjiButton;
	private RoundedButton conjugationButton;
	private RoundedButton scoresButton;
	private RoundedButton exitButton;
	// HAS-MANY: HomeScreen has many RoundedButton objects for the menu choices.

	/**
	 * Creates the home screen and initialize its buttons and layout.
	 * This constructor sets up the window, creates the title and subtitle,
	 * creates the study section buttons, and connects each button to the
	 * ActionListener.
	 */
	public HomeScreen()
	{
		// Set up the main window for the home screen.
		setTitle("マイ練習 - Home");
		setSize(950, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create color objects used for the screen background and buttons.
		Color backgroundColor = new Color(250, 250, 245);
		Color buttonColor = new Color(222, 236, 250);

		// Create the main panel that holds all home screen components
		// vertically.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(backgroundColor);

		// Create the main title label for the home screen.
		// This label tells the user to choose a study section.
		JLabel titleLabel = new JLabel("Choose a Study Section");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
		titleLabel.setForeground(new Color(45, 75, 120));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the subtitle label.
		// This label displays the available Japanese study categories.
		JLabel subtitleLabel = new JLabel("語彙 ・ 文法 ・ 漢字 ・ 活用");
		subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		subtitleLabel.setForeground(new Color(95, 95, 95));
		subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add the title and subtitle to the main panel with spacing.
		mainPanel.add(Box.createVerticalStrut(40));
		mainPanel.add(titleLabel);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(subtitleLabel);
		mainPanel.add(Box.createVerticalStrut(30));

		// Create the button panel using GridLayout.
		// GridLayout places the six menu buttons into one vertical column
		// with equal spacing between each button.
		JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 15, 15));
		buttonPanel.setBackground(backgroundColor);
		buttonPanel
				.setBorder(BorderFactory.createEmptyBorder(20, 180, 20, 180));

		// Create each menu button for the home screen.
		vocabularyButton = new RoundedButton("Vocabulary／語彙");
		grammarButton = new RoundedButton("Grammar／文法");
		kanjiButton = new RoundedButton("Kanji／漢字");
		conjugationButton = new RoundedButton("Conjugations／活用");
		scoresButton = new RoundedButton("View Scores／成績");
		exitButton = new RoundedButton("Exit／終了");

		// Store all buttons in an array so the same formatting and
		// ActionListener can be applied to each button with one loop.
		RoundedButton[] buttons = { vocabularyButton, grammarButton,
				kanjiButton, conjugationButton, scoresButton, exitButton };

		// Apply the same font, colors, event listener, and panel placement
		// to every home screen button.
		for (RoundedButton button : buttons)
		{
			button.setFont(new Font("SansSerif", Font.BOLD, 18));
			button.setBackground(buttonColor);
			button.setForeground(Color.BLACK);
			button.addActionListener(this); // register this screen as the event handler
			buttonPanel.add(button);
		}

		// Add the completed button panel to the main screen layout.
		mainPanel.add(buttonPanel);

		// Add the main panel to the JFrame.
		add(mainPanel);

		// Center the window on the screen and make it visible.
		setLocationRelativeTo(null); // center the window on the screen
		setVisible(true); // make the window appear
	}

	/**
	 * Responds to button clicks on the home screen.
	 * If Vocabulary is clicked, the program opens the section menu.
	 * If Exit is clicked, the program closes.
	 *
	 * @param e the ActionEvent fired by the button the user clicked,
	 *          containing the source component for identification
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// if-else Statement: checks which button was clicked and chooses the
		// right action.
		// Object: new SectionMenuScreen() creates a new screen object from its
		// class blueprint.
		if (e.getSource() == vocabularyButton)
		{
			new SectionMenuScreen("Vocabulary");
			dispose();
		}
		else if (e.getSource() == grammarButton)
		{
			new SectionMenuScreen("Grammar");
			dispose();
		}
		else if (e.getSource() == kanjiButton)
		{
			new SectionMenuScreen("Kanji");
			dispose();
		}
		else if (e.getSource() == conjugationButton)
		{
			new SectionMenuScreen("Conjugations");
			dispose();
		}
		else if (e.getSource() == scoresButton)
		{
			JOptionPane.showMessageDialog(this,
					"Score screen coming in a future update.");
		}
		else if (e.getSource() == exitButton)
		{
			System.exit(0);
		}
	}
}