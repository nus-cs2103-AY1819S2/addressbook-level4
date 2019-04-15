package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.pdf.commons.core.Messages;
import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.NameContainsKeywordsPredicate;

/**
 * Finds and lists all pdfs in pdf book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all pdfs whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " algorithm";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPdfList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PDFS_LISTED_OVERVIEW, model.getFilteredPdfList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
