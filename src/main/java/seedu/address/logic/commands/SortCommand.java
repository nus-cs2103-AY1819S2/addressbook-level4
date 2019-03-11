package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;




/**
 * Sorts data in accordance to the given parameter
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + " : Sorts the displayed list of patients OR a patient's records according to the desired parameter.\n"
        + "Parameters: [record] PARAMETER \n"
        + "Example 1: " + COMMAND_WORD + "name\n"
        + "Example 2: " + COMMAND_WORD + "record procedure\n";

    public static final String MESSAGE_SUCCESS = "List has been sorted!";

    public final Comparator<Patient> attrCompare;

    SortCommand(Comparator<Patient> chosenCompare) {
        requireNonNull(chosenCompare);
        this.attrCompare = chosenCompare;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
