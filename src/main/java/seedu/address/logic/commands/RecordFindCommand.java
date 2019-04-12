package seedu.address.logic.commands;

import seedu.address.model.util.predicate.ContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive unless specified.
 */
public class RecordFindCommand {

    public static final String COMMAND_WORD = "recordfind";
    public static final String COMMAND_WORD2 = "rfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
        + ": Finds all records whose particulars contain any of "
        + "the specified parameter's keywords and displays them as a list with index numbers.\n"
        + "Specifying CS will search for case sensitivity, while specifying AND will search for patients containing"
        + "all of the keywords.\n"
        + "Parameters: [CS] [AND] prefix/KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " pro/crown brace";

    private ContainsKeywordsPredicate predicate;

    public RecordFindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
}
