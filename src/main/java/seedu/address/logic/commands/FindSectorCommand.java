package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.SectorContainsKeywordsPredicate;

/**
 * Finds and lists all companies in address book whose sector contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindSectorCommand extends Command {

    public static final String COMMAND_WORD = "findSector";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all companies with exact sector. (Case insensitive) "
            + "Example: " + COMMAND_WORD + " bank";

    private final SectorContainsKeywordsPredicate predicate;

    public FindSectorCommand(SectorContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCompanyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredCompanyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindSectorCommand // instanceof handles nulls
                && predicate.equals(((FindSectorCommand) other).predicate)); // state check
    }
}
