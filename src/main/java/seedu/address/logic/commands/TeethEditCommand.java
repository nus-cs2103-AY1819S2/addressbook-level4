package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Teeth;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;

/**
 * Edits the details of an existing person in the address book.
 */
public class TeethEditCommand extends Command {

    public static final String COMMAND_WORD = "teethedit";

    public static final String BAD_RANGE = "Valid tooth numbers are from 1 to 32.";

    public static final String BAD_STATUS = "Valid statuses are 0 (present and healthy), 1 (problematic),"
            + " and 2 (absent).";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the condition of the patient's tooth identified "
            + "by the tooth number. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_STATUS + "STATUS \n"
            + "Healthy tooth = 0, Problematic tooth = 1, Absent tooth = 2 \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STATUS + "0";

    public static final String MESSAGE_EDIT_TOOTH_SUCCESS = "Tooth status is updated!";
    public static final String MESSAGE_NOT_EDITED = "Please provide the new tooth status";

    private final int status;
    private final int toothNumber;

    /**
     * @param toothNumber tooth number to edit.
     * @param status the updated status of the tooth
     */
    public TeethEditCommand(int toothNumber, int status) {
        this.toothNumber = toothNumber;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person personToEdit = MainWindow.getRecordPatient();
        Teeth teethToEdit = ((Patient) personToEdit).getTeeth();
        teethToEdit.getTooth(toothNumber).setTo(status);

        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_EDIT_TOOTH_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeethEditCommand)) {
            return false;
        }

        // state check
        TeethEditCommand e = (TeethEditCommand) other;
        return toothNumber == e.toothNumber
                && this.status == ((TeethEditCommand) other).status;
    }

}
