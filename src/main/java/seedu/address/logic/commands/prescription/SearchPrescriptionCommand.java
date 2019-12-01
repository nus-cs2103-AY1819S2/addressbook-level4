package seedu.address.logic.commands.prescription;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.prescription.PrescriptionContainsKeywordsPredicate;

/**
 * Searches and lists all prescriptions in docX record whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchPrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "search-presc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all prescriptions whose "
            + "description contains any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " fever";

    private final PrescriptionContainsKeywordsPredicate predicate;

    public SearchPrescriptionCommand(PrescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPrescriptionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW,
                        model.getFilteredPrescriptionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchPrescriptionCommand // instanceof handles nulls
                && predicate.equals(((SearchPrescriptionCommand) other).predicate)); // state check
    }
}
