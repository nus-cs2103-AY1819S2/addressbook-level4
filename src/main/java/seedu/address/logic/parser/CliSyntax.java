package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {


    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_SKILL = new Prefix("skill/");
    public static final Prefix PREFIX_POS = new Prefix("position/");
    public static final Prefix PREFIX_GPA = new Prefix("g/");
    public static final Prefix PREFIX_EDUCATION = new Prefix("d/");
    public static final Prefix PREFIX_DEGREE = new Prefix("deg/");

    /* Prefix for Filtering definitions */
    public static final Prefix PREFIX_FILTER_NAME = new Prefix("name<");
    public static final Prefix PREFIX_FILTER_NAME_REVERSE = new Prefix(">name");
    public static final Prefix PREFIX_FILTER_PHONE = new Prefix("phone<");
    public static final Prefix PREFIX_FILTER_PHONE_REVERSE = new Prefix(">phone");
    public static final Prefix PREFIX_FILTER_EMAIL = new Prefix("email<");
    public static final Prefix PREFIX_FILTER_EMAIL_REVERSE = new Prefix(">email");
    public static final Prefix PREFIX_FILTER_ADDRESS = new Prefix("addr<");
    public static final Prefix PREFIX_FILTER_ADDRESS_REVERSE = new Prefix(">addr");

    public static final Prefix PREFIX_FILTER_SKILL = new Prefix("skill<");
    public static final Prefix PREFIX_FILTER_SKILL_REVERSE = new Prefix(">skill");
    public static final Prefix PREFIX_FILTER_POS = new Prefix("pos<");
    public static final Prefix PREFIX_FILTER_POS_REVERSE = new Prefix(">pos");
    public static final Prefix PREFIX_FILTER_ENDORSEMENT = new Prefix("end<");
    public static final Prefix PREFIX_FILTER_ENDORSEMENT_REVERSE = new Prefix(">end");
    public static final Prefix PREFIX_FILTER_GPA = new Prefix("gpa<");
    public static final Prefix PREFIX_FILTER_GPA_REVERSE = new Prefix(">gpa");
    public static final Prefix PREFIX_FILTER_DEGREE = new Prefix("deg<");
    public static final Prefix PREFIX_FILTER_DEGREE_REVERSE = new Prefix(">deg");
    public static final Prefix PREFIX_FILTER_EDUCATION = new Prefix("edu<");
    public static final Prefix PREFIX_FILTER_EDUCATION_REVERSE = new Prefix(">edu");


    /* Sort-Word definitions*/
    public static final SortWord SORTWORD_DEGREE = new SortWord("degree");
    public static final SortWord SORTWORD_EDUCATION = new SortWord("education");
    public static final SortWord SORTWORD_ENDORSEMENTS = new SortWord("endorsements");
    public static final SortWord SORTWORD_ENDORSEMENT_NUMBER = new SortWord("endorsement number");
    public static final SortWord SORTWORD_GPA = new SortWord("gpa");
    public static final SortWord SORTWORD_NAME = new SortWord("name");
    public static final SortWord SORTWORD_POSITION_NUMBER = new SortWord("position number");
    public static final SortWord SORTWORD_POSITIONS = new SortWord("positions");
    public static final SortWord SORTWORD_REVERSE_DEGREE = new SortWord("reverse degree");
    public static final SortWord SORTWORD_REVERSE_EDUCATION = new SortWord("reverse education");
    public static final SortWord SORTWORD_REVERSE_ENDORSEMENTS = new SortWord("reverse endorsements");
    public static final SortWord SORTWORD_REVERSE_ENDORSEMENT_NUMBER = new SortWord("reverse endorsement number");
    public static final SortWord SORTWORD_REVERSE_GPA = new SortWord("reverse gpa");
    public static final SortWord SORTWORD_REVERSE_NAME = new SortWord("reverse name");
    public static final SortWord SORTWORD_REVERSE_POSITION_NUMBER = new SortWord("reverse position number");
    public static final SortWord SORTWORD_REVERSE_POSITIONS = new SortWord("reverse positions");
    public static final SortWord SORTWORD_REVERSE_SKILL_NUMBER = new SortWord("reverse skill number");
    public static final SortWord SORTWORD_REVERSE_SKILLS = new SortWord("reverse skills");
    public static final SortWord SORTWORD_REVERSE_SURNAME = new SortWord("reverse surname");
    public static final SortWord SORTWORD_SKILL_NUMBER = new SortWord("skill number");
    public static final SortWord SORTWORD_SKILLS = new SortWord("skills");
    public static final SortWord SORTWORD_SURNAME = new SortWord("surname");
}
