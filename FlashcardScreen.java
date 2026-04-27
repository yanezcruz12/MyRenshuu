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
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
 *   Covers: JLabel and JPanel used to build the flashcard display
 * Chapter 13.5: The Java Event Model
 *   Covers: addActionListener(this) and actionPerformed(ActionEvent e)
 * Chapter 13.6: Case Study: Designing a Basic GUI
 *   Covers: overall pattern of building a GUI screen with panels and buttons
 * Chapter 13.7: Containers and Layout Managers
 *   Covers: BoxLayout stacking the card label and extra label vertically
 * Chapter 10.7: From the Java Library: JOptionPane
 *   Covers: showMessageDialog used when vocabulary file cannot be loaded
 * Chapter 11.2: Streams and Files
 *   Covers: FileNotFoundException thrown by DataLoader.loadVocabulary()
 * Chapter 5.2: Boolean Data and Operators
 *   Covers: showingAnswer boolean and the ! operator in showingAnswer = !showingAnswer
 *
 *Other References:
 * Oracle. (n.d.). Class JFrame. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFrame.html
 *
 * Oracle. (n.d.). Class JOptionPane. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JOptionPane.html
 *
 * Oracle. (n.d.). Class ArrayList. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
 *
 * Oracle. (n.d.). Operators Summary. The Java Tutorials.
 * Retrieved from https://docs.oracle.com/javase/tutorial/java/nutsandbolts/opsummary.html
 *   Covers: ! logical complement operator used in showingAnswer = !showingAnswer
 *
 * Version/date: 
 * 1.0 — April 11, 2026  — Class created, basic window and card layout designed
 * 1.1 — April 16, 2026 — loadData() and updateCard() added, showingAnswer boolean introduced for flip mechanic
 * 1.2 — April 23, 2026 — Flip, Next, and Back buttons connected to ActionListener,
                          each button now triggers its correct action when clicked,
                          extraLabel added to show reading hints on answer side.
 * 1.3 — April 25, 2026 — Javadoc improvements, concept comments added. 
 *
 * Responsibilities of class:
 * Displays a flashcard practice screen for the マイ練習 application.
 * This class loads vocabulary items, shows one flashcard at a time,
 * flips between the prompt and answer, and lets the user move to the
 * next card or return to the section menu.
 */

//Inheritance: FlashcardScreen extends JFrame, receiving all window behavior.
//Override: actionPerformed is the custom version provided for this screen.
//IS-A: FlashcardScreen is a JFrame because it extends JFrame.
//IS-A: FlashcardScreen is an ActionListener because it implements ActionListener.
public class FlashcardScreen extends JFrame implements ActionListener
{
 private String sectionName;
 // HAS-A: FlashcardScreen has a section name to know which study section is being used.

 private ArrayList<StudyItem> items;
 // HAS-MANY: FlashcardScreen has many StudyItem objects stored in an ArrayList.

 private int currentIndex;
 // HAS-A: FlashcardScreen has a current index to track the current flashcard.

 // Instance Variable: showingAnswer is a boolean that belongs to this object.
 // Private: only this class can change whether the answer side is showing.
 private boolean showingAnswer;
 // HAS-A: FlashcardScreen has a boolean value to track whether the answer side is showing.

 private JLabel sectionLabel;
 private JLabel cardLabel;
 private JLabel extraLabel;
 // HAS-MANY: FlashcardScreen has many JLabel objects to display section, card, and extra text.

 private RoundedButton flipButton;
 private RoundedButton nextButton;
 private RoundedButton backButton;
 // HAS-MANY: FlashcardScreen has many RoundedButton objects for user actions.

 /**
  * Creates a flashcard screen for the selected section.
  * This constructor sets up the window, loads vocabulary data, creates
  * the card display, creates buttons, and connects the buttons to the
  * ActionListener.
  *
  * @param sectionName the name of the section to study
  */
 public FlashcardScreen(String sectionName)
 {
     this.sectionName = sectionName;
     this.items = new ArrayList<>();
     this.currentIndex = 0;
     this.showingAnswer = false;

     // Set up the main window for the flashcard screen.
     setTitle("マイ練習 - " + sectionName + " Flashcards");
     setSize(950, 700);
     setDefaultCloseOperation(EXIT_ON_CLOSE);

     // Load the vocabulary data before displaying the first flashcard.
     loadData();

     // Create color objects used for the screen background and buttons.
     Color backgroundColor = new Color(252, 251, 247);
     Color buttonColor = new Color(230, 238, 250);

     // Create the main panel that holds all screen components vertically.
     JPanel mainPanel = new JPanel();
     mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
     mainPanel.setBackground(backgroundColor);

     // Create the section label at the top of the screen.
     // This label shows the selected section and tells the user
     // they are in Flashcards mode.
     sectionLabel = new JLabel(sectionName + " Flashcards");
     sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
     sectionLabel.setForeground(new Color(44, 74, 120));
     sectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

     // Create the main card label.
     // This label displays either the Japanese prompt or the English answer.
     cardLabel = new JLabel("", JLabel.CENTER);
     cardLabel.setFont(new Font("Serif", Font.BOLD, 34));
     cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

     // Create the extra label.
     // This label displays optional extra information, such as notes or readings.
     extraLabel = new JLabel(" ");
     extraLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
     extraLabel.setForeground(new Color(100, 100, 100));
     extraLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

     // Create the card panel that visually groups the flashcard text
     // into one white card area.
     JPanel cardPanel = new JPanel();
     cardPanel.setBackground(Color.WHITE);
     cardPanel.setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createLineBorder(new Color(190, 190, 190), 2),
             BorderFactory.createEmptyBorder(60, 60, 60, 60)));
     cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
     cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
     cardPanel.setMaximumSize(new java.awt.Dimension(500, 250));

     // Make sure the card text is centered inside the card panel.
     cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     extraLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

     // Add the card text and extra information to the card panel
     // with spacing so the flashcard is easier to read.
     cardPanel.add(Box.createVerticalGlue());
     cardPanel.add(cardLabel);
     cardPanel.add(Box.createVerticalStrut(20));
     cardPanel.add(extraLabel);
     cardPanel.add(Box.createVerticalGlue());

     // Create the buttons used to flip cards, move forward, and go back.
     flipButton = new RoundedButton("Flip（めくる）");
     nextButton = new RoundedButton("Next（次へ）");
     backButton = new RoundedButton("Back（戻る）");

     // Store the buttons in an array so the same formatting and
     // ActionListener can be applied using one loop.
     RoundedButton[] buttons = { flipButton, nextButton, backButton };

     // Create a button panel to hold the Flip, Next, and Back buttons.
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

     // Add the title, flashcard panel, and buttons to the main screen layout.
     mainPanel.add(Box.createVerticalStrut(35));
     mainPanel.add(sectionLabel);
     mainPanel.add(Box.createVerticalStrut(40));
     mainPanel.add(cardPanel);
     mainPanel.add(Box.createVerticalStrut(30));
     mainPanel.add(buttonPanel);

     // Add the main panel to the JFrame.
     add(mainPanel);

     // Display the first flashcard after the GUI components are created.
     updateCard();

     // Center the window on the screen and make it visible.
     setLocationRelativeTo(null); // center the window on the screen
     setVisible(true);           // make the window appear
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
         // Only load vocabulary data when the selected section is Vocabulary.
         if (sectionName.equals("Vocabulary"))
         {
             items = DataLoader.loadVocabulary("vocabulary.txt");
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
  * Updates the flashcard display based on the current item.
  * This method checks whether the prompt side or answer side should be
  * displayed, then updates the labels using the current StudyItem.
  */
 private void updateCard()
 {
     // If no vocabulary items were loaded, show a message on the card.
     if (items.isEmpty())
     {
         cardLabel.setText("No study items found.");
         extraLabel.setText(" ");
         return;
     }

     // Get the current StudyItem based on the current index.
     StudyItem currentItem = items.get(currentIndex);

     // If showingAnswer is false, show the front of the flashcard.
     if (!showingAnswer)
     {
         cardLabel.setText(currentItem.getPrompt());
         extraLabel.setText(" ");
     }
     // If showingAnswer is true, show the answer side of the flashcard.
     else
     {
         cardLabel.setText(currentItem.getAnswer());
     }
 }

 /**
  * Responds to button clicks on the flashcard screen.
  * If Flip is clicked, the card switches between prompt and answer.
  * If Next is clicked, the next flashcard is shown. If Back is clicked,
  * the user returns to the section menu.
  *
  * @param e the ActionEvent fired by the button the user clicked,
  *             containing the source component for identification
  */
 @Override
 public void actionPerformed(ActionEvent e)
 {
     // If the Flip button is clicked, switch between prompt and answer.
     if (e.getSource() == flipButton)
     {
         // if Statement: ! is the logical complement — flips true to false and false to true.
         showingAnswer = !showingAnswer;
         updateCard();
     }
     // If the Next button is clicked, move to the next flashcard.
     else if (e.getSource() == nextButton)
     {
         if (!items.isEmpty())
         {
             // Variable: currentIndex tracks which card is showing.
             currentIndex = (currentIndex + 1) % items.size(); // wrap back to 0 at end
             showingAnswer = false;
             updateCard();
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