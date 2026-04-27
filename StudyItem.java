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
 * Chapter 2.4: Class Definition
 * Chapter 3.2: Passing Information to an Object
 * Chapter 3.3: Constructor Methods
 * Chapter 3.4: Retrieving Information from an Object
 *
 * Other References: 
 * Oracle. (n.d.). Class String. Java Platform SE API Specification.
 * Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html
 * 
 * Version/date:
 * 1.0 — April 9, 2026  — Initial version created
 * 1.1 — April 24, 2026 — Javadoc improvements and concept comments added
 * 1.2 — April 25, 2026 — Citations updated
 *
 * Responsibilities of class:
 * Represents one study item in the マイ練習 application.
 * This class stores the section, prompt, answer, and optional extra
 * information for one vocabulary or practice item.
 */

// Class: StudyItem is a blueprint for creating study card objects.
// Object: every StudyItem created from this class is one actual study card.
// IS-A: StudyItem is a regular Java class used to create StudyItem objects.
public class StudyItem
{
    /** The study category this item belongs to, such as Vocabulary or Grammar. */
    // Instance Variable: section belongs to each object and stores its own value.
    // Private: only this class can read or change section directly.
    private String section;
    // HAS-A: StudyItem has a section that identifies the study category.
 
    /** The question or front side of the study card, such as a Japanese word. */
    // Instance Variable: each StudyItem object has its own prompt value.
    // String: prompt stores text, such as a Japanese word.
    private String prompt;
    // HAS-A: StudyItem has a prompt that stores the question or front side.
 
    /** The correct answer or English meaning for this study card. */
    // Instance Variable: each object stores its own answer independently.
    private String answer;
    // HAS-A: StudyItem has an answer that stores the correct response or meaning.
 
    /** Optional extra information, such as a translation hint or usage note. */
    // Instance Variable: extra is optional data stored per object.
    private String extra;
    // HAS-A: StudyItem has extra information, such as a clue, translation, or note.
 
    /**
     * Creates a StudyItem object with section data and study content.
     * This constructor stores the values passed into it so the practice screens
     * can later display the prompt, answer, and extra information.
     *
     * @param section the section this item belongs to
     * @param prompt the front or question part of the item
     * @param answer the correct answer or meaning
     * @param extra additional information about the item
     */
    // Constructor: this method runs when a new StudyItem object is created.
    // Parameter: section, prompt, answer, extra are the values handed in.
    public StudyItem(String section, String prompt, String answer, String extra)
    {
        // Store the study category for this item.
        // Instance Variable vs Parameter: this.section = the field, section = the argument passed in.
        this.section = section;
 
        // Store the prompt or question text.
        this.prompt = prompt;
 
        // Store the correct answer or meaning.
        this.answer = answer;
 
        // Store optional extra information, such as a clue or translation.
        this.extra = extra;
    }
 
    /**
     * Returns the section name for this StudyItem.
     *
     * @return the section of this study item
     */
    // Accessor/Getter: getSection() lets other classes read section without touching it directly.
    // Encapsulation: the field is private but the getter is public — controlled access.
    public String getSection()
    {
        // Return the study category stored in this object.
        return section;
    }
 
    /**
     * Returns the prompt text for this StudyItem.
     *
     * @return the prompt text
     */
    // Accessor/Getter: getPrompt() safely returns the prompt value.
    public String getPrompt()
    {
        // Return the prompt or question stored in this object.
        return prompt;
    }
 
    /**
     * Returns the answer text for this StudyItem.
     *
     * @return the answer text
     */
    // Accessor/Getter: getAnswer() safely returns the answer value.
    public String getAnswer()
    {
        // Return the correct answer stored in this object.
        return answer;
    }
 
    /**
     * Returns the extra information for this StudyItem.
     *
     * @return the extra text
     */
    // Accessor/Getter: getExtra() safely returns the extra value.
    public String getExtra()
    {
        // Return the extra clue, translation, or note stored in this object.
        return extra;
    }
}