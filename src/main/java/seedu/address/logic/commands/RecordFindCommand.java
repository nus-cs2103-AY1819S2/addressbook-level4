package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive unless specified.
 */
public class RecordFindCommand extends Command {

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

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(this.predicate);
        return new CommandResult(String.format(Messages.MESSAGE_RECORDS_LISTED_OVERVIEW,
            model.getFilteredRecordList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof RecordFindCommand
            && predicate.equals(((RecordFindCommand) other).predicate));
    }
}
