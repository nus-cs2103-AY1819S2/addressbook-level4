package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class Syntax {
    /* Prefix definitions */
    // Used by Lesson commands
    public static final Prefix PREFIX_LESSON_NAME = new Prefix("n/");

    // Used by Card and Lesson commands
    public static final Prefix PREFIX_TEST = new Prefix("t/");
    public static final Prefix PREFIX_HINT = new Prefix("h/");

    // Used by Start command
    public static final Prefix PREFIX_START_COUNT = new Prefix("c/");
    public static final Prefix PREFIX_START_MODE = new Prefix("m/");

    /**
     * This is a constants-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     */
    private Syntax() { }
}
