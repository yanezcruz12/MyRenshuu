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
 * Represents the opening screen of the マイ練習 application.
 * This screen welcomes the user and allows navigation to the home screen.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class WelcomeScreen extends JFrame implements ActionListener
{
    private RoundedButton startButton;

    /**
     * Creates the welcome screen and initializes its layout and components.
     */
    public WelcomeScreen()
    {
        setTitle("マイ練習");
        setSize(950, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color backgroundColor = new Color(247, 248, 252);
        getContentPane().setBackground(backgroundColor);

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JLabel titleLabel = new JLabel("マイ練習", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 42));
        titleLabel.setForeground(new Color(36, 62, 110));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("MyRenshuu", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        subtitleLabel.setForeground(new Color(80, 80, 80));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel taglineLabel = new JLabel("日本語で練習しましょう", SwingConstants.CENTER);
        taglineLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        taglineLabel.setForeground(new Color(100, 100, 100));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(subtitleLabel);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(taglineLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon logoIcon = new ImageIcon("images/myrenshuu_logo.png");
        if (logoIcon.getIconWidth() > 0)
        {
            Image scaledImage = logoIcon.getImage().getScaledInstance(
                    3４0, 3４0, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        }
        else
        {
            logoLabel.setText("Logo goes here");
            logoLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
            logoLabel.setForeground(new Color(120, 120, 120));
        }

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createVerticalGlue());

        startButton = new RoundedButton("Start");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(220, 50));
        startButton.setBackground(new Color(214, 228, 249));
        startButton.setForeground(Color.BLACK);
        startButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 40, 10));
        bottomPanel.add(startButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Responds to button clicks on the welcome screen.
     *
     * @param e the ActionEvent triggered by the user
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == startButton)
        {
            new HomeScreen();
            dispose();
        }
    }
}
