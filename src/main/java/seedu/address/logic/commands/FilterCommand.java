package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.pdf.TagContainsKeywordsPredicate;

/**
 * Filters and lists all persons in address book which has matching tag(s) specified.
 * Tag matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters and displays all persons whose "
            + "names contain any of the specified keywords (case-sensitive) and displays them as a "
            + "list with index numbers.\n"
            + "Parameters: t/TAG [t/MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " t/2103 t/project";

    private final TagContainsKeywordsPredicate predicate;

    public FilterCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
