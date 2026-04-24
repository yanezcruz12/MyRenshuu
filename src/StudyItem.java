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
 * Represents one study item in the マイ練習 application.
 * A study item stores a prompt, an answer, and optional extra information.
 *
 * @author Cruz Yanez
 * @version 1.0
 */
public class StudyItem
{
    private String section;
    private String prompt;
    private String answer;
    private String extra;

    /**
     * Creates a study item with section data and study content.
     *
     * @param section the section this item belongs to
     * @param prompt the front or question part of the item
     * @param answer the correct answer or meaning
     * @param extra additional information about the item
     */
    public StudyItem(String section, String prompt, String answer, String extra)
    {
        this.section = section;
        this.prompt = prompt;
        this.answer = answer;
        this.extra = extra;
    }

    /**
     * Returns the section name.
     *
     * @return the section of this study item
     */
    public String getSection()
    {
        return section;
    }

    /**
     * Returns the prompt.
     *
     * @return the prompt text
     */
    public String getPrompt()
    {
        return prompt;
    }

    /**
     * Returns the answer.
     *
     * @return the answer text
     */
    public String getAnswer()
    {
        return answer;
    }

    /**
     * Returns the extra information.
     *
     * @return the extra text
     */
    public String getExtra()
    {
        return extra;
    }
}
