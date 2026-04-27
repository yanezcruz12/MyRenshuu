import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
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
 * Chapter 2.6: From the Java Library: java.util.Scanner
 * Chapter 11.2: Streams and Files
 * Chapter 11.3: Case Study: Reading and Writing Text Files
 * Chapter 11.4: The File Class
 *
 *Other References:
 * Oracle. (n.d.). Class File. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/File.html
 *
 * Oracle. (n.d.). Class Scanner. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html
 *
 * Oracle. (n.d.). Class FileNotFoundException. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileNotFoundException.html
 *
 * Oracle. (n.d.). Class ArrayList. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
 *
 * Version/date:
 * 1.0 — April 9, 2026  — Initial version created
 * 1.1 — April 23, 2026 — loadFillBlank() method added
 * 1.2 — April 25, 2026 — Switched Scanner to try-with-resources to ensure file is always closed
 *
 * Responsibilities of class:
 * Loads study data from external text files and converts each line into
 * StudyItem objects for the マイ練習 application.
 *
* IS-A relationship:
 * DataLoader is a regular utility class. It does not extend another custom
 * class or implement an interface.
 *
 * HAS-A / HAS-MANY relationship:
 * DataLoader creates and returns many StudyItem objects stored in an
 * ArrayList.
 */
// Class: DataLoader is a blueprint for a utility class with no instance variables.
// Public: DataLoader is accessible to all other classes in the program.
public class DataLoader
{
    /**
     * Loads vocabulary items from a text file and converts each line into a StudyItem.
     * Each line in the file must use the comma-separated format: prompt,answer,extra.
     * Blank lines are skipped automatically. If any field is missing from a line,
     * an empty string is used in its place to prevent an ArrayIndexOutOfBoundsException.
     *
     * @param fileName the name of the vocabulary data file to read from disk
     * @return an ArrayList containing one StudyItem object for each valid line in the file
     * @throws FileNotFoundException if the specified file cannot be located on disk
     */
    // Method: loadVocabulary defines what DataLoader can do when asked to load a file.
    // Parameter: fileName is the information handed in so the method knows which file to open.
    // Exception: throws FileNotFoundException means this method may signal an error to its caller.
    public static ArrayList<StudyItem> loadVocabulary(String fileName)
            throws FileNotFoundException
    {
        // Creates an empty list that will hold every StudyItem built from the file.
        // Variable: items is a named storage location holding the list of objects built from the file.
        ArrayList<StudyItem> items = new ArrayList<>();
 
        // Exception: try-with-resources handles the case where file reading fails.
        // try-with-resources automatically closes the Scanner when the block finishes,
        // even if an exception is thrown mid-loop. This ensures the file is always released.
        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            // Loops through every line in the file until there are no more lines to read.
            while (scanner.hasNextLine())
            {
                // Reads the next line of text and removes any leading or trailing whitespace.
                String line = scanner.nextLine().trim();
 
                // Skips blank lines so empty StudyItem objects are not created.
                if (!line.isEmpty())
                {
                    // Splits the line at every comma, producing an array of string parts.
                    // For example: "cat,ねこ,animal" becomes ["cat", "ねこ", "animal"].
                    String[] parts = line.split(",");
 
                    // Safely reads each part using a ternary operator.
                    // If the part exists, it is trimmed. If not, an empty string is used.
                    String prompt = parts.length > 0 ? parts[0].trim() : ""; // first field
                    String answer = parts.length > 1 ? parts[1].trim() : ""; // second field
                    String extra  = parts.length > 2 ? parts[2].trim() : ""; // third field
 
                    // Builds one StudyItem from the extracted fields and adds it to the list.
                    items.add(new StudyItem("Vocabulary", prompt, answer, extra));
                }
            }
        } // Scanner is automatically closed here no matter what
 
        // Returns the completed ArrayList of StudyItem objects to the caller.
        return items;
    }
 
    /**
     * Loads fill-in-the-blank vocabulary items from a text file and converts each line
     * into a StudyItem. Each line must use the pipe-separated format: sentence|translation|answer.
     * Pipe is used instead of comma because sentences may contain commas naturally.
     * Blank lines are skipped. Missing fields default to an empty string.
     *
     * @param fileName the name of the fill-in-the-blank data file to read from disk
     * @return an ArrayList containing one StudyItem object for each valid line in the file
     * @throws FileNotFoundException if the specified file cannot be located on disk
     */
    public static ArrayList<StudyItem> loadFillBlank(String fileName)
            throws FileNotFoundException
    {
        // Creates an empty list that will hold every StudyItem built from the file.
        // Variable: items is a named storage location holding the list of objects built from the file.
        ArrayList<StudyItem> items = new ArrayList<>();
 
        // Exception: try-with-resources handles the case where file reading fails.
        // try-with-resources automatically closes the Scanner when the block finishes,
        // even if an exception is thrown mid-loop. This ensures the file is always released.
        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            // Loops through every line in the file until there are no more lines to read.
            while (scanner.hasNextLine())
            {
                // Reads the next line of text and removes any leading or trailing whitespace.
                String line = scanner.nextLine().trim();
 
                // Skips blank lines so empty StudyItem objects are not created.
                if (!line.isEmpty())
                {
                    // Splits on pipe (|) instead of comma because sentences may contain commas.
                    // The double backslash escapes the pipe character for Java regex.
                    // For example: "I eat rice.|私はご飯を食べます。|食べます" splits into 3 parts.
                    String[] parts = line.split("\\|");
 
                    // Safely reads each part — note the field order differs from loadVocabulary.
                    String prompt = parts.length > 0 ? parts[0].trim() : ""; // the sentence with a blank
                    String extra  = parts.length > 1 ? parts[1].trim() : ""; // the translation clue
                    String answer = parts.length > 2 ? parts[2].trim() : ""; // the correct answer
 
                    // Builds one StudyItem from the extracted fields and adds it to the list.
                    items.add(new StudyItem("Vocabulary", prompt, answer, extra));
                }
            }
        } // Scanner is automatically closed here no matter what
 
        // Returns the completed ArrayList of StudyItem objects to the caller.
        return items;
    }
}