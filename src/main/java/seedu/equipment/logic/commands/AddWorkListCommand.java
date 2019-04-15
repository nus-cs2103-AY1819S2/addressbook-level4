package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Date;
//import seedu.equipment.model.equipment.Equipment;

/**
 * Create a WorkList in the Equipment Manager.
 */
public class AddWorkListCommand extends Command {

    public static final String COMMAND_WORD = "add-w";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a WorkList in the Equipment Manager. "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "DATE "
            + CliSyntax.PREFIX_ASSIGNEE + "ASSIGNEE "
            + CliSyntax.PREFIX_ID + "ID(beginning with 0 is not valid) \n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "12-12-2019 "
            + CliSyntax.PREFIX_ASSIGNEE + "Mary "
            + CliSyntax.PREFIX_ID + "13 ";

    public static final String MESSAGE_SUCCESS = "New WorkList created:(WorkListId) %1$s";
    public static final String MESSAGE_DUPLICATE_EQUIPMENT = "Duplicated WorkList ID, "
            + "this WorkList already exists in the equipment manager.";
    public static final String INVALID_DATE = "Date format is not valid. Need to follow dd-mm-yyyy, for example"
            + " 12-12-2019";

    private final WorkList toAdd;

    /**
     * Creates an AddWorkListCommand to add the specified {@code WorkList}
     */
    public AddWorkListCommand(WorkList workList) {
        requireNonNull(workList);
        toAdd = workList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasWorkList(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EQUIPMENT);
        }

        if (!Date.isValidDate(toAdd.getDate())) {
            throw new CommandException(INVALID_DATE);
        }

        model.addWorkList(toAdd);
        model.commitEquipmentManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWorkListCommand // instanceof handles nulls
                && toAdd.equals(((AddWorkListCommand) other).toAdd));
    }
}
