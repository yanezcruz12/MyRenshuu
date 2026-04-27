import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
 *   Covers: extends JFrame and implements ActionListener
 * Chapter 8.3: Abstract Classes, Interfaces, and Polymorphism
 *   Covers: implementing the ActionListener interface contract
 * Chapter 13.2: Java GUIs: From AWT to Swing
 *   Covers: why javax.swing classes like JFrame and JPanel are used
 * Chapter 13.3: The Swing Component Set
 *   Covers: JLabel, JPanel, ImageIcon used to build the welcome screen
 * Chapter 13.5: The Java Event Model
 *   Covers: addActionListener(this) and actionPerformed(ActionEvent e)
 * Chapter 13.6: Case Study: Designing a Basic GUI
 *   Covers: overall pattern of building a GUI screen with panels and a button
 * Chapter 13.7: Containers and Layout Managers
 *   Covers: BorderLayout for top/center/bottom layout, BoxLayout for stacking labels
 *
 *Other References:
 * Oracle. (n.d.). Class JFrame. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html
 *
 * Oracle. (n.d.). Class ImageIcon. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/ImageIcon.html
 *
 * Oracle. (n.d.). Class Image. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Image.html
 *
 * Version/date:
 * 1.0 — April 16, 2026 — Initial version created
 * 1.1 — April 24, 2026 — Updated and added concept and Javadoc comments
 * 1.2 — April 25, 2026 — Citations added and updated
 * 
 * Responsibilities of class:
 * Displays the opening welcome screen for the マイ練習 application.
 * This class shows the title, subtitle, logo, tagline, and Start button.
 * When the user clicks Start, the program opens the HomeScreen.
 */

// Inheritance: WelcomeScreen extends JFrame, inheriting all window behavior.
// Subclass: WelcomeScreen is the child, JFrame is the superclass.
// IS-A: WelcomeScreen is a JFrame because it extends JFrame.
// IS-A: WelcomeScreen is an ActionListener because it implements
// ActionListener.
public class WelcomeScreen extends JFrame implements ActionListener
{
	private RoundedButton startButton;
	// HAS-A: WelcomeScreen has a RoundedButton that allows the user to start
	// the program.

	/**
	 * Creates the welcome screen and initialize its layout and components.
	 * This constructor sets up the JFrame, creates the title area, loads the
	 * logo,
	 * creates the Start button, and connects the button to the ActionListener.
	 */
	public WelcomeScreen()
	{
		// Set up the main window for the welcome screen.
		setTitle("マイ練習");
		setSize(950, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Use BorderLayout so the screen can have a top, center, and bottom
		// area.
		setLayout(new BorderLayout());

		// Set the background color for the frame content area.
		Color backgroundColor = new Color(247, 248, 252);
		getContentPane().setBackground(backgroundColor);

		// Create the top panel to hold the title, English name, and tagline.
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

		// Create the main Japanese title label.
		JLabel titleLabel = new JLabel("マイ練習", SwingConstants.CENTER);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 42));
		titleLabel.setForeground(new Color(36, 62, 110));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the English subtitle label.
		JLabel subtitleLabel = new JLabel("MyRenshuu", SwingConstants.CENTER);
		subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		subtitleLabel.setForeground(new Color(80, 80, 80));
		subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the Japanese tagline label.
		JLabel taglineLabel = new JLabel("日本語で練習しましょう", SwingConstants.CENTER);
		taglineLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		taglineLabel.setForeground(new Color(100, 100, 100));
		taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add the title labels to the top panel with small spacing.
		topPanel.add(titleLabel);
		topPanel.add(Box.createVerticalStrut(8));
		topPanel.add(subtitleLabel);
		topPanel.add(Box.createVerticalStrut(8));
		topPanel.add(taglineLabel);

		// Create the center panel to hold the logo image.
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		// Create an empty label that will display the logo if the image loads.
		JLabel logoLabel = new JLabel();
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Load the logo image from the images folder.
		ImageIcon logoIcon = new ImageIcon("images/myrenshuu_logo.png");

		// If the logo file is found, scale the image and place it in the label.
		if (logoIcon.getIconWidth() > 0)
		{
			Image scaledImage = logoIcon.getImage().getScaledInstance(340, 340,
					Image.SCALE_SMOOTH);
			logoLabel.setIcon(new ImageIcon(scaledImage));
		}
		// If the image cannot be found, display placeholder text instead.
		else
		{
			logoLabel.setText("Logo goes here");
			logoLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
			logoLabel.setForeground(new Color(120, 120, 120));
		}

		// Add the logo to the center panel with vertical spacing.
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(logoLabel);
		centerPanel.add(Box.createVerticalGlue());

		// Create the Start button and connect it to the ActionListener.
		startButton = new RoundedButton("Start");
		startButton.setFont(new Font("SansSerif", Font.BOLD, 18));
		startButton.setPreferredSize(new Dimension(220, 50));
		startButton.setBackground(new Color(214, 228, 249));
		startButton.setForeground(Color.BLACK);
		startButton.addActionListener(this);

		// Create the bottom panel to hold the Start button.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 40, 10));
		bottomPanel.add(startButton);

		// Add the top, center, and bottom panels to the JFrame.
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		// Center the window on the screen and make it visible.
		setLocationRelativeTo(null); // center the window on the screen
		setVisible(true); // make the window appear
	}

	/**
	 * Responds to button clicks on the welcome screen.
	 * If the Start button is clicked, this method opens the HomeScreen
	 * and closes the welcome screen.
	 *
	 * @param e the ActionEvent fired by the button the user clicked,
	 *          containing the source component for identification
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// If the Start button is clicked, open the home screen.
		if (e.getSource() == startButton)
		{
			new HomeScreen();
			dispose();
		}
	}
}