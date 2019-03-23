package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.EquipmentContainsKeywordsPredicate;

/**
 * Filters and lists all equipments in Equipment Manager whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters equipment based on specific field.\n"
            + "Parameters: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_PHONE + "PHONE "
            + CliSyntax.PREFIX_EMAIL + "EMAIL "
            + CliSyntax.PREFIX_ADDRESS + "ADDRESS "
            + CliSyntax.PREFIX_SERIALNUMBER + "SERIAL NUMBER "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "John Doe "
            + CliSyntax.PREFIX_PHONE + "98765432 "
            + CliSyntax.PREFIX_EMAIL + "johnd@example.com "
            + CliSyntax.PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + CliSyntax.PREFIX_SERIALNUMBER + "A008866X "
            + CliSyntax.PREFIX_TAG + "west "
            + CliSyntax.PREFIX_TAG + "urgent";

    private EquipmentContainsKeywordsPredicate predicate;

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
