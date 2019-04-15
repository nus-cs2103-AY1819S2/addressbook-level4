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
import seedu.address.ui.StatWindow;


/**
 * Shows statistics for the given patient
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + " : Shows statistics for the identified patient. Patient can be identified either by the index number in "
        + "the displayed patient list OR by keyword.\n"
        + "Parameters: INDEX (must be positive integer) \n"
        + "Example 1: " + COMMAND_WORD + " 3\n";

    public static final String MESSAGE_SUCCESS = "Statistic for patient %1$s printed!";

    private Patient toStat;
    private Index index;

    public StatsCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        //TODO: Implement stat execution for keyword
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index != null) {
            this.toStat = extractPatientFromIndex(lastShownList);
        }
        StatWindow.setStatPatient(this.toStat);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toStat.getName()), false, true, false);
    }

    /**
     * Returns the patient who corresponds to the inputted index.
     */
    private Patient extractPatientFromIndex(List<Person> lastShownList) throws CommandException {
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return (Patient) lastShownList.get(index.getZeroBased());
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StatsCommand // instanceof handles nulls
            && index.equals(((StatsCommand) other).index));
    }
}
