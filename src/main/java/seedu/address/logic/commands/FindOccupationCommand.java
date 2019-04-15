package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.OccupationContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose occupation contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindOccupationCommand extends Command {

    public static final String COMMAND_WORD = "findOccupation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons with exact occupation. (Case insensitive) "
            + "Example: " + COMMAND_WORD + " Teacher";

    private final OccupationContainsKeywordsPredicate predicate;

    public FindOccupationCommand(OccupationContainsKeywordsPredicate predicate) {
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindOccupationCommand // instanceof handles nulls
                && predicate.equals(((FindOccupationCommand) other).predicate)); // state check
    }
}
