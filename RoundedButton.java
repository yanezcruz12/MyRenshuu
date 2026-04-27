import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Lead Author(s):
 * @author Cruz Yanez
 * @version 1.0
 *
 * Other contributors:
 * None
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java:
 * Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * Chapter 8.2: Java's Inheritance Mechanism
 * Chapter 13.3: The Swing Component Set
 *
 * Oracle. (n.d.). Class JButton. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JButton.html
 *
 * Oracle. (n.d.). Class Graphics. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Graphics.html
 *
 * Oracle. (n.d.). Class Graphics2D. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Graphics2D.html
 *
 * Oracle. (n.d.). Class RenderingHints. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/RenderingHints.html
 *
 *
 * Version/date:
 * 1.0 — April 16, 2026 — Initial version created,
 *                         custom rounded painting using Graphics2D designed
 * 1.1 — April 17, 2026 — Anti-aliasing refined with RenderingHints,
 *                        getWidth() - 1 and getHeight() - 1 adjusted
 *                        to keep border stroke inside button bounds                     
 * 1.1 — April 24, 2026 — Javadoc improvements, concept comments added
 *                    
 * Responsibilities of class:
 * Creates a reusable custom button for マイ練習 application.
 * This class changes the appearance of a normal JButton by drawing
 * a rounded background and rounded border.
 */

// Inheritance: RoundedButton extends JButton, receiving all button behavior for
// free.
// Subclass: RoundedButton is the child class that adds custom painting.
// Superclass: JButton is the parent class that RoundedButton inherits from.
// IS-A: RoundedButton is a JButton because it extends JButton.
public class RoundedButton extends JButton
{
	/**
	 * Creates a RoundedButton with the given label text.
	 * Calls the JButton constructor via super(text) to preserve all standard
	 * button behavior such as text storage and ActionEvent firing. Then turns
	 * off all default JButton painting so paintComponent and paintBorder can
	 * draw a custom rounded shape without interference from the default look.
	 *
	 * @param text the label string to display centered on the button face
	 */
	public RoundedButton(String text)
	{
		// super(): calls the JButton constructor first to set up the parent
		// part.
		// Call the JButton constructor so the button still stores and displays
		// text.
		super(text);

		// Turn off the focus border so the custom button has a cleaner look.
		setFocusPainted(false);

		// Turn off the default filled button background because paintComponent
		// will draw the rounded background manually.
		setContentAreaFilled(false);

		// Turn off the default border because paintBorder will draw a custom
		// rounded border.
		setBorderPainted(false);

		// Make the button non-opaque so the custom rounded shape blends with
		// the GUI.
		setOpaque(false);
	}

	/**
	 * Paints the rounded filled background of this button, then lets JButton
	 * draw its text label on top. Overrides JButton's paintComponent so a
	 * rounded rectangle replaces the default rectangular fill. A Graphics2D
	 * copy is used so rendering changes stay isolated to this paint call.
	 *
	 * @param g the Graphics context provided by Swing when this component needs
	 *          repainting
	 */
	// Override: paintComponent replaces JButton's default rectangular painting
	// with our custom version.
	// Polymorphism: the same method name behaves differently here than in the
	// standard JButton.
	@Override
	protected void paintComponent(Graphics g)
	{
		// Create a Graphics2D copy from the original Graphics object.
		// Graphics2D gives access to more advanced drawing features.
		Graphics2D g2 = (Graphics2D) g.create();

		// Turn on anti-aliasing so the rounded button edges look smoother.
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Set the drawing color to the button's current background color.
		g2.setColor(getBackground());

		// Draw the filled rounded rectangle that becomes the button background.
		// The last two numbers control how rounded the corners are.
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

		// Let JButton paint its normal content, such as the button text,
		// on top of the custom rounded background.
		super.paintComponent(g2);

		// Dispose of the copied Graphics2D object to release drawing resources.
		g2.dispose();
	}

	/**
	 * Paints a rounded gray outline around the edge of this button.
	 * Overrides JButton's paintBorder so the border matches the rounded shape
	 * drawn by paintComponent. Uses getWidth() - 1 and getHeight() - 1 to keep
	 * the border stroke fully inside the button's bounds.
	 *
	 * @param g the Graphics context provided by Swing when this component needs
	 *          repainting
	 */
	// Override: paintBorder replaces JButton's default rectangular border with
	// a rounded one.
	@Override
	protected void paintBorder(Graphics g)
	{
		// Create a Graphics2D copy so the custom border drawing is separate
		// from the original Graphics object used by Swing.
		Graphics2D g2 = (Graphics2D) g.create();

		// Turn on anti-aliasing so that the border edges look smoother.
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Sets the border color to gray.
		g2.setColor(new Color(160, 160, 160));

		// Draws a rounded rectangle border around the button.
		// getWidth() - 1 and getHeight() - 1 keeps the border inside the button
		// area.
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

		// Dispose of the copied Graphics2D object after drawing the border.
		g2.dispose();
	}
}