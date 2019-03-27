package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.medicine.MedicineExpiryThresholdPredicate;

import java.util.function.Predicate;

/**
 * Adds a medicine to the inventory.
 */
public class WarningCommand extends Command {

    public static final String COMMAND_WORD = "warning";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes thresholds used in the warning panel. "
            + "Parameters: "
            + "[" + PREFIX_EXPIRY + "EXPIRY_THRESHOLD" + "] "
            + "[" + PREFIX_QUANTITY + "LOW_STOCK_THRESHOLD]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXPIRY + "10 "
            + PREFIX_QUANTITY + "20 ";

    private final Predicate predicate;

    private boolean showThreshold = false;

    /**
     * Creates a WarningCommand to change the specified warning threshold.
     */
    public WarningCommand(Predicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    /**
     * Creates a WarningCommand to show the current threshold levels.
     */
    public WarningCommand(String show) {
        this.showThreshold = true;
        this.predicate = changeThreshold -> false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (showThreshold) {
            WarningPanelPredicateAccessor predicateAccessor = model.getWarningPanelPredicateAccessor();
            return new CommandResult(
                    String.format(Messages.MESSAGE_SHOW_CURRENT_THRESHOLDS,
                            predicateAccessor.getExpiryThreshold(), predicateAccessor.getLowStockThreshold()));
        }

        if (predicate instanceof MedicineExpiryThresholdPredicate) {
            model.updateFilteredExpiringMedicineList(predicate);
        } else {
            model.updateFilteredLowStockMedicineList(predicate);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_MEDICINES_LISTED_OVERVIEW, model.getFilteredMedicineList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WarningCommand // instanceof handles nulls
                && predicate.equals(((WarningCommand) other).predicate));
    }
}
