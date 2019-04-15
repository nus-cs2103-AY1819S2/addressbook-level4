package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Searches and lists all persons in archive book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ArchiveSearchCommand extends Command {

    public static final String COMMAND_WORD = "archivesearch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches in the archive all persons whose "
            + "names/phone/email/remark/address/tags contain any of the specified keywords (case-insensitive) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice 96521418 clementi 4room";
    private final PersonContainsKeywordsPredicate predicate;

    public ArchiveSearchCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredArchivedPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredArchivedPersonList().size()),
                false, false, true);
    }

    @Override
    public boolean requiresMainList() {
        return false;
    }

    @Override
    public boolean requiresArchiveList() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveSearchCommand // instanceof handles nulls
                && predicate.equals(((ArchiveSearchCommand) other).predicate)); // state check
    }
}
