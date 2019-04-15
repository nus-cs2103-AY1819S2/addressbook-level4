package seedu.address.logic.commands.prescription;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.prescription.Prescription;

/**
 * Selects a prescription identified using it's displayed index from the docX record.
 */
public class SelectPrescriptionCommand extends Command {
    public static final String COMMAND_WORD = "select-presc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects prescription identified by the index number used in the displayed prescription list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PRESCRIPTION_SUCCESS = "Selected Prescription: %1$s";

    private final Index targetIndex;

    public SelectPrescriptionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Prescription> filteredPrescriptionList = model.getFilteredPrescriptionList();

        if (targetIndex.getZeroBased() >= filteredPrescriptionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
        }

        model.setSelectedPrescription(filteredPrescriptionList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_PRESCRIPTION_SUCCESS, targetIndex.getOneBased()),
                CommandResult.ShowBrowser.PRESCRIPTION_BROWSER);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectPrescriptionCommand // instanceof handles nulls
                && targetIndex.equals(((SelectPrescriptionCommand) other).targetIndex)); // state check
    }
}
