package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;

/**
 * Expands a patient's entry so that his or her teeth and dental information can be changed.
 */
public class GoToCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Expands patient identified by the index number used in the displayed person list.\n"
            + "Enable the display and modification of his or her dental information."
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EXPAND_PERSON_SUCCESS = "Displaying dental records of patient %1$s";

    private final Index targetIndex;

    public GoToCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> filteredPersonList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        /* Do not allow going into another patient's records while still in one */
        if (MainWindow.isGoToMode()) {
            throw new CommandException(Messages.MESSAGE_IN_GO_TO_MODE);
        }

        Patient selectedPerson = (Patient) filteredPersonList.get(targetIndex.getZeroBased());
        MainWindow.setRecordPatient(selectedPerson);
        model.setSelectedPerson(null);
        model.setSelectedPerson(selectedPerson);

        return new CommandResult(String.format(MESSAGE_EXPAND_PERSON_SUCCESS, targetIndex.getOneBased()), true,
                false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoToCommand // instanceof handles nulls
                && targetIndex.equals(((GoToCommand) other).targetIndex)); // state check
    }
}
