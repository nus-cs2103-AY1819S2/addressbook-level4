package seedu.address.logic.commands.medicalhistory;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * Selects a patient identified using it's displayed index from the docX record.
 */
public class SelectMedHistCommand extends Command {
    public static final String COMMAND_WORD = "select-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects medical history identified by the index number used in the displayed medical history list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_MEDHIST_SUCCESS = "Selected Medical History: %1$s";

    private final Index targetIndex;

    public SelectMedHistCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<MedicalHistory> filteredMedHistList = model.getFilteredMedHistList();

        if (targetIndex.getZeroBased() >= filteredMedHistList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDHIST_DISPLAYED_INDEX);
        }

        model.setSelectedMedHist(filteredMedHistList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_MEDHIST_SUCCESS, targetIndex.getOneBased()),
                CommandResult.ShowBrowser.MED_HIST_BROWSER);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectMedHistCommand // instanceof handles nulls
                && targetIndex.equals(((SelectMedHistCommand) other).targetIndex)); // state check
    }
}
