package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.threshold.Threshold;


/**
 * Change or show thresholds used in warning panel.
 */
public class WarningCommand extends Command {

    public static final String COMMAND_WORD = "warning";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change or check thresholds used in the warning panel. "
            + "Parameters: "
            + "[" + PREFIX_EXPIRY + "EXPIRY_THRESHOLD" + "] "
            + "[" + PREFIX_QUANTITY + "LOW_STOCK_THRESHOLD] "
            + "[" + "SHOW" + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUANTITY + "20\n"
            + "NOTE: only one parameter allowed.";

    private final Threshold threshold;
    private final WarningPanelPredicateType type;
    private final boolean showThreshold;


    public WarningCommand(WarningPanelPredicateType type, Threshold threshold) {
        requireNonNull(type);
        requireNonNull(threshold);
        this.showThreshold = false;
        this.threshold = threshold;
        this.type = type;
    }

    /**
     * Creates a WarningCommand to show the current threshold levels.
     */
    public WarningCommand(String show) {
        requireNonNull(show);
        this.showThreshold = true;
        this.threshold = null;
        this.type = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (showThreshold) {
            return showCurrentThresholds(model);
        } else {
            return changeThreshold(type, threshold, model);
        }
    }

    /**
     * Show current thresholds used in warning panel.
     * @param model
     * @return Message with current thresholds used in warning panel
     */
    private CommandResult showCurrentThresholds(Model model) {
        Threshold expiryThreshold = model.getWarningPanelThreshold(WarningPanelPredicateType.EXPIRY);
        Threshold lowStockThreshold = model.getWarningPanelThreshold(WarningPanelPredicateType.LOW_STOCK);
        return new CommandResult(
                String.format(Messages.MESSAGE_SHOW_CURRENT_THRESHOLDS,
                        expiryThreshold.getNumericValue(), lowStockThreshold.getNumericValue()));
    }

    /**
     * Change threshold used in warning panel.
     * @param type
     * @param threshold
     * @param model
     * @return Message with current thresholds used in warning panel
     */
    private CommandResult changeThreshold(WarningPanelPredicateType type, Threshold threshold, Model model) {
        model.changeWarningPanelListThreshold(type, threshold);
        return showCurrentThresholds(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WarningCommand // instanceof handles nulls
                && threshold.equals(((WarningCommand) other).threshold)
                && showThreshold == ((WarningCommand) other).showThreshold);
    }
}
