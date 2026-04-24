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
 * 
 * @author yanez; student ID
 * @author Full name; student ID
 *         <<Add additional lead authors here>>
 *
 *         Other Contributors:
 *         Full name; student ID or contact information if not in class
 *         <<Add additional contributors (mentors, tutors, friends) here, with
 *         contact information>>
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         <<Add more references here>>
 *
 *         Version:
 */
/**
 * Represents the main home screen of the マイ練習 application.
 * This screen allows the user to choose a study section.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class HomeScreen extends JFrame implements ActionListener
{
	private RoundedButton vocabularyButton;
	private RoundedButton grammarButton;
	private RoundedButton kanjiButton;
	private RoundedButton conjugationButton;
	private RoundedButton scoresButton;
	private RoundedButton exitButton;

	/**
	 * Creates the home screen and initializes its buttons and layout.
	 */
	public HomeScreen()
	{
		setTitle("マイ練習 - Home");
		setSize(950, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Color backgroundColor = new Color(250, 250, 245);
		Color buttonColor = new Color(222, 236, 250);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(backgroundColor);

		JLabel titleLabel = new JLabel("Choose a Study Section");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
		titleLabel.setForeground(new Color(45, 75, 120));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel subtitleLabel = new JLabel("語彙 ・ 文法 ・ 漢字 ・ 活用");
		subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		subtitleLabel.setForeground(new Color(95, 95, 95));
		subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainPanel.add(Box.createVerticalStrut(40));
		mainPanel.add(titleLabel);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(subtitleLabel);
		mainPanel.add(Box.createVerticalStrut(30));

		JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 15, 15));
		buttonPanel.setBackground(backgroundColor);
		buttonPanel
				.setBorder(BorderFactory.createEmptyBorder(20, 180, 20, 180));

		vocabularyButton = new RoundedButton("Vocabulary／語彙");
		grammarButton = new RoundedButton("Grammar／文化");
		kanjiButton = new RoundedButton("Kanji／漢字");
		conjugationButton = new RoundedButton("Conjugations／活用");
		scoresButton = new RoundedButton("View Scores／成績");
		exitButton = new RoundedButton("Exit／終了");

		RoundedButton[] buttons = { vocabularyButton, grammarButton,
				kanjiButton, conjugationButton, scoresButton, exitButton };

		for (RoundedButton button : buttons)
		{
			button.setFont(new Font("SansSerif", Font.BOLD, 18));
			button.setBackground(buttonColor);
			button.setForeground(Color.BLACK);
			button.addActionListener(this);
			buttonPanel.add(button);
		}

		mainPanel.add(buttonPanel);

		add(mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Responds to button clicks on the home screen.
	 *
	 * @param e the ActionEvent triggered by the user
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == vocabularyButton)
		{
			new SectionMenuScreen("Vocabulary");
			dispose();
		}
		else if (e.getSource() == grammarButton)
		{
			JOptionPane.showMessageDialog(this, "Grammar section coming next.");
		}
		else if (e.getSource() == kanjiButton)
		{
			JOptionPane.showMessageDialog(this, "Kanji section coming next.");
		}
		else if (e.getSource() == conjugationButton)
		{
			JOptionPane.showMessageDialog(this,
					"Conjugations section coming next.");
		}
		else if (e.getSource() == scoresButton)
		{
			JOptionPane.showMessageDialog(this, "Score screen coming next.");
		}
		else if (e.getSource() == exitButton)
		{
			System.exit(0);
		}
	}
}
