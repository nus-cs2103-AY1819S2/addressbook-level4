package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsCpnyKeywordsPredicate;

/**
 * Finds and lists all companies in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCpnyCommand extends Command {

    public static final String COMMAND_WORD = "findcpny";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all companies whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsCpnyKeywordsPredicate predicate;

    public FindCpnyCommand(NameContainsCpnyKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCompanyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW, model.getFilteredCompanyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCpnyCommand // instanceof handles nulls
                && predicate.equals(((FindCpnyCommand) other).predicate)); // state check
    }
}

