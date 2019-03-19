package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.equipment.EquipmentContainsKeywordsPredicate;

/**
 * Filters and lists all equipments in Equipment Manager whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all equipments whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list of index numbers.\n"
            + "Parameters: KEYWORD [MORE KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " west urgent";

    private final EquipmentContainsKeywordsPredicate predicate;

    public FilterCommand(EquipmentContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EQUIPMENTS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instance of handles nulls
                && predicate.equals(((FilterCommand) other).predicate));

    }
}
