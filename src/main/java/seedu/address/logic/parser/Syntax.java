package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class Syntax {

    /* Prefix definitions */
    // Used by Lesson commands
    public static final Prefix PREFIX_LESSON_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CORE_QUESTION = new Prefix("q/");
    public static final Prefix PREFIX_CORE_ANSWER = new Prefix("a/");

    // Used by Card and Lesson commands
    public static final Prefix PREFIX_CORE = new Prefix("c/");
    public static final Prefix PREFIX_OPTIONAL = new Prefix("o/");

    // Used by Start command
    public static final Prefix PREFIX_START_NAME = new Prefix("n/");
    public static final Prefix PREFIX_START_COUNT = new Prefix("c/");
    public static final Prefix PREFIX_START_MODE = new Prefix("m/");
}
