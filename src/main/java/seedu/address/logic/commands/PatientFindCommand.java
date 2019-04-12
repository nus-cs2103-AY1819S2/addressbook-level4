package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class PatientFindCommand extends Command {

    public static final String COMMAND_WORD = "patientfind";
    public static final String COMMAND_WORD2 = "pfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Finds all patient whose particulars contain any of "
            + "the specified parameter's keywords and displays them as a list with index numbers.\n"
            + "Specifying CS will search for case sensitivity, while specifying AND will search for patients containing"
            + " all of the keywords.\n"
            + "Parameters: [CS] [AND] prefix/KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie";

    private ContainsKeywordsPredicate predicate;

    public PatientFindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(this.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public String toString() {
        return predicate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientFindCommand // instanceof handles nulls
                && predicate.equals(((PatientFindCommand) other).predicate)); // state check
    }
}
