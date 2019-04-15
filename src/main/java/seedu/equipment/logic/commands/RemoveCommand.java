package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.SerialNumber;

/**
 * Remove an equipment from a WorkList in the Equipment Manager.
 */
public class RemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove an equipment from a WorkList in the Equipment Manager. "
            + "Parameters: "
            + CliSyntax.PREFIX_ID + "WORKLISTID "
            + CliSyntax.PREFIX_SERIALNUMBER + "EQUIPMENT \n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_ID + "1 "
            + CliSyntax.PREFIX_SERIALNUMBER + "A008866X ";

    public static final String MESSAGE_SUCCESS_ONE = "The equipment removed from the worklist(ID = ";
    public static final String MESSAGE_SUCCESS_TWO = "): Serial Number: %1$s";
    public static final String MESSAGE_EQUIPMENT_NOT_FOUND = "This equipment serial number is not found.";
    public static final String MESSAGE_WORKLIST_NOT_FOUND = "This worklist id is not found.";
    public static final String MESSAGE_EQUIPMENT_NOT_IN_WORKLIST = "The equipment is not in the worklist.";

    private final WorkListId id;
    private final SerialNumber sr;

    /**
     * Creates a PutCommand.
     */
    public RemoveCommand(WorkListId id, SerialNumber sr) {
        requireNonNull(id);
        requireNonNull(sr);
        this.id = id;
        this.sr = sr;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasEquipmentWithSerialNumber(sr)) {
            throw new CommandException(MESSAGE_EQUIPMENT_NOT_FOUND);
        }

        WorkList sampleWorkList = new WorkList("09-09-2018", "sampleName", id);
        if (!model.hasWorkList(sampleWorkList)) {
            throw new CommandException(MESSAGE_WORKLIST_NOT_FOUND);
        }

        if (!model.hasEquipmentInWorkList(id, sr)) {
            throw new CommandException(MESSAGE_EQUIPMENT_NOT_IN_WORKLIST);
        }

        model.removeEquipment(id, sr);

        model.commitEquipmentManager();
        String success = MESSAGE_SUCCESS_ONE + id.value + MESSAGE_SUCCESS_TWO;
        return new CommandResult(String.format(success, sr.serialNumber));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveCommand // instanceof handles nulls
                && id.equals(((RemoveCommand) other).id)
                && sr.equals(((RemoveCommand) other).sr));
    }
}
