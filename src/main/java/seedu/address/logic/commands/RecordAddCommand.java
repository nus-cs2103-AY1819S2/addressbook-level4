package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.ui.MainWindow;

/**
 * Adds a patient to the address book.
 */
public class RecordAddCommand extends Command {

    public static final String COMMAND_WORD = "recordadd";
    public static final String COMMAND_WORD2 = "radd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Adds a new dental record to a patient. "
            + "Parameters: " + PREFIX_PROCEDURE + "PROCEDURE "
            + PREFIX_DESC + "DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PROCEDURE + "Other-Checkup "
            + PREFIX_DESC + "Patient went through all four wisdom tooth extraction today ";

    public static final String MESSAGE_SUCCESS = "New record added to %1$s";

    public static final String MESSAGE_DUPLICATE_RECORD = "Record already exists, but is still added to %1$s!";

    public static final String MESSAGE_ERROR = "Please specify the patient using the goto command first";

    private final Patient toAdd;
    private Record addRecord;

    /**
     * Creates an RecordAddCommand to add a new dental record to a specified {@code Patient}
     * @param record the record to be added.
     */
    public RecordAddCommand(Record record) {
        requireNonNull(record);
        toAdd = MainWindow.getRecordPatient();
        this.addRecord = record;
    }

    /**
     * Executes the command.
     * Note that duplicate records still get added. But a duplicate message is shown to user.
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return the {@code CommandResult} of the command call.
     * @throws CommandException the error message when the method is called in non-GoTo mode.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (MainWindow.isGoToMode() && toAdd != null) {
            requireNonNull(model);

            boolean isDuplicate = model.hasRecord(this.addRecord);

            model.addRecord(this.addRecord);

            if (isDuplicate) {
                return new CommandResult(String.format(MESSAGE_DUPLICATE_RECORD, toAdd.getName()));
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
            }

        } else {
            throw new CommandException(MESSAGE_ERROR);
        }
    }

    /**
     * Checks if both commands are equal.
     * True only if both are the same instance.
     * Duplicate description and patient is okay.
     * @param other the other command to check if equals.
     * @return true if equals, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else {
            return other == this // short circuit if same object
                    || (other instanceof RecordAddCommand // instanceof handles nulls
                    && addRecord.equals(((RecordAddCommand) other).addRecord));
        }
    }
}
