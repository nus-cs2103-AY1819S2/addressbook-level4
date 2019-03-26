package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.WorkList;
//import seedu.equipment.model.equipment.Equipment;

/**
 * Create a WorkList in the Equipment Manager.
 */
public class AddWorkListCommand extends Command {

    public static final String COMMAND_WORD = "add-w";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a WorkList in the Equipment Manager. "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "DATE "
            + "[" + CliSyntax.PREFIX_ASSIGNEE + "ASSIGNEE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "2019-02-12 "
            + CliSyntax.PREFIX_ASSIGNEE + "Mei Yen ";

    public static final String MESSAGE_SUCCESS = "New WorkList created: %1$s";
    public static final String MESSAGE_DUPLICATE_EQUIPMENT = "Duplicated WorkList ID, "
            + "this WorkList already exists in the equipment manager.";

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
