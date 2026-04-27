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
 * Chapter 1.2: Designing Good Programs
 * Chapter 1.5: Editing, Compiling, and Running a Java Program
 *
 * Version/date:
 * 1.0 — April 7, 2026  — Initial version created
 * 1.1 — April 24, 2026 — Javadoc comments and concepts added
 *
 * Responsibilities of class:
 * Launches the マイ練習 application. This class contains the main method,
 * which is the starting point of the program.
 */
 
// Class: MyRenshuuApp is the blueprint that contains the program entry point.
// Public: MyRenshuuApp is accessible by the Java runtime to find and run main().
// IS-A: MyRenshuuApp is a regular Java class. It does not extend another custom class.
// HAS-A: MyRenshuuApp has the main method that starts the application.
public class MyRenshuuApp
{
    /**
     * Starts the マイ練習 application by creating the opening WelcomeScreen.
     * This is the first method Java executes when the program is launched.
     * No variable is needed to store the WelcomeScreen reference because
     * Swing's event dispatch thread keeps the window alive automatically.
     *
     * @param args command-line arguments passed at launch, not used by this program
     */
    public static void main(String[] args)
    {
        // Object: new WelcomeScreen() creates one actual WelcomeScreen object from its class blueprint.
        // Create the first screen of the application.
        // This starts the GUI by opening the welcome screen.
        new WelcomeScreen();
    }
}