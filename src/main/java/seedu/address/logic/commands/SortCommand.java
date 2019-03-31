package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Sort and lists all persons in address book according to a parameter
 * parameter matching is case insensitive
 * there is option to sort by ascending or descending order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "Sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all persons according to the specified parameter "
            + "(case-insensitive) and the specified order (ascending or descending). The result is displayed as a "
            + "list with index numbers.\n"
            + "Parameters: PARAMETER ORDER\n"
            + "List of PARAMETER: n, sp, rp"
            + "List of ORDER: increasing, decreasing"
            + "Example: " + COMMAND_WORD + " n increasing ";

    private final NameContainsKeywordsPredicate predicate;

    public SortCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean requiresMainList() {
        return true;
    }

    @Override
    public boolean requiresArchiveList() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && predicate.equals(((SortCommand) other).predicate)); // state check
    }
}
