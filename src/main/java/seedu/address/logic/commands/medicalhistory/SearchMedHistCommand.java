package seedu.address.logic.commands.medicalhistory;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedHistContainsKeywordsPredicate;

/**
 * Searches and lists all medical histories in docX record whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchMedHistCommand extends Command {

    public static final String COMMAND_WORD = "search-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all medical histories containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " fever";

    private final MedHistContainsKeywordsPredicate predicate;

    public SearchMedHistCommand(MedHistContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMedHistList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEDHISTS_LISTED_OVERVIEW, model.getFilteredMedHistList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchMedHistCommand // instanceof handles nulls
                && predicate.equals(((SearchMedHistCommand) other).predicate)); // state check
    }
}
