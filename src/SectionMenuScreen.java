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
 * Represents a section menu screen for one study category.
 * This screen allows the user to choose a practice mode.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class SectionMenuScreen extends JFrame implements ActionListener
{
	private String sectionName;

	private RoundedButton flashcardsButton;
	private RoundedButton writingButton;
	private RoundedButton matchingButton;
	private RoundedButton fillBlankButton;
	private RoundedButton backButton;

	/**
	 * Creates a section menu screen for the selected section.
	 *
	 * @param sectionName the name of the study section
	 */
	public SectionMenuScreen(String sectionName)
	{
		this.sectionName = sectionName;

		setTitle("マイ練習 - " + sectionName);
		setSize(950, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Color backgroundColor = new Color(245, 250, 248);
		Color buttonColor = new Color(215, 235, 225);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(backgroundColor);

		JLabel titleLabel = new JLabel(sectionName + " Practice");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
		titleLabel.setForeground(new Color(40, 100, 90));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel subtitleLabel = new JLabel("Choose a practice mode");
		subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
		subtitleLabel.setForeground(new Color(90, 90, 90));
		subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainPanel.add(Box.createVerticalStrut(40));
		mainPanel.add(titleLabel);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(subtitleLabel);
		mainPanel.add(Box.createVerticalStrut(30));

		JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 15, 15));
		buttonPanel.setBackground(backgroundColor);
		buttonPanel
				.setBorder(BorderFactory.createEmptyBorder(20, 180, 20, 180));

		flashcardsButton = new RoundedButton("Flashcards（フラッシュカード）");
		writingButton = new RoundedButton("Writing Practice（書く練習）");
		matchingButton = new RoundedButton("Matching（マッチング）");
		fillBlankButton = new RoundedButton("Fill in the Blank（穴埋め）");
		backButton = new RoundedButton("Back（戻る）");

		RoundedButton[] buttons = { flashcardsButton, writingButton,
				matchingButton, fillBlankButton, backButton };

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
	 * Responds to button clicks on the section menu screen.
	 *
	 * @param e the ActionEvent triggered by the user
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == flashcardsButton)
		{
			if (sectionName.equals("Vocabulary"))
			{
				new FlashcardScreen(sectionName);
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(this,
						sectionName + " flashcards coming next.");
			}
		}
		else if (e.getSource() == writingButton)
		{
			if (sectionName.equals("Vocabulary"))
			{
				new WritingPracticeScreen(sectionName);
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(this,
						sectionName + " writing practice coming next.");
			}
		}
		else if (e.getSource() == matchingButton)
		{
			if (sectionName.equals("Vocabulary"))
			{
				new MatchingScreen(sectionName);
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(this,
						sectionName + " matching coming next.");
			}
		}
		else if (e.getSource() == fillBlankButton)
		{
			if (sectionName.equals("Vocabulary"))
			{
				new FillInBlankScreen(sectionName);
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(this,
						sectionName + " fill in the blank coming next.");
			}
		}
	}
}
