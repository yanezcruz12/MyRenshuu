import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads study data from text files for the マイ練習 application.
 * This class reads study items and converts them into StudyItem objects.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class DataLoader
{
    /**
     * Loads vocabulary items from a text file.
     * Each line must use the format: prompt,answer,extra
     *
     * @param fileName the name of the vocabulary data file
     * @return an ArrayList of vocabulary study items
     * @throws FileNotFoundException if the file cannot be found
     */
    public static ArrayList<StudyItem> loadVocabulary(String fileName)
            throws FileNotFoundException
    {
        ArrayList<StudyItem> items = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine().trim();

            if (!line.isEmpty())
            {
                String[] parts = line.split(",");

                String prompt = parts.length > 0 ? parts[0].trim() : "";
                String answer = parts.length > 1 ? parts[1].trim() : "";
                String extra = parts.length > 2 ? parts[2].trim() : "";

                items.add(new StudyItem("Vocabulary", prompt, answer, extra));
            }
        }

        scanner.close();
        return items;
    }

    /**
     * Loads fill-in-the-blank vocabulary items from a text file.
     * Each line must use the format: sentence,translation,answer
     *
     * @param fileName the name of the fill-in-the-blank data file
     * @return an ArrayList of fill-in-the-blank study items
     * @throws FileNotFoundException if the file cannot be found
     */
    public static ArrayList<StudyItem> loadFillBlank(String fileName)
            throws FileNotFoundException
    {
        ArrayList<StudyItem> items = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine().trim();

            if (!line.isEmpty())
            {
                String[] parts = line.split("\\|");

                String prompt = parts.length > 0 ? parts[0].trim() : "";
                String extra = parts.length > 1 ? parts[1].trim() : "";
                String answer = parts.length > 2 ? parts[2].trim() : "";

                items.add(new StudyItem("Vocabulary", prompt, answer, extra));
            }
        }

        scanner.close();
        return items;
    }
}