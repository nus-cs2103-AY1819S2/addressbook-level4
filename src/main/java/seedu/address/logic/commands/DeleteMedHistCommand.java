package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * Deletes a patient identified using it's displayed index from the address book.
 */
public class DeleteMedHistCommand extends Command {

    public static final String COMMAND_WORD = "delete-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the medical history identified by index number used in the displayed medical history list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEDHIST_SUCCESS = "Deleted Medical History: %1$s";

    private final Index targetIndex;

    public DeleteMedHistCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<MedicalHistory> lastShownList = model.getFilteredMedHistList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDHIST_DISPLAYED_INDEX);
        }

        MedicalHistory medHistToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteMedHist(medHistToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_MEDHIST_SUCCESS, medHistToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMedHistCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteMedHistCommand) other).targetIndex)); // state check
    }
}
