package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.model.Model.PREDICATE_SHOW_ALL_EQUIPMENT;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.Model;

/**
 * Lists all equipments in the equipment manager to the user.
 */
public class ListEquipmentCommand extends Command {

    public static final String COMMAND_WORD = "list-e";

    public static final String EXTRA_PARA_EXCEPTION = "Extra parameters will be ignored.";

    public static final String MESSAGE_SUCCESS = "Listed all equipment";

    private boolean extraPara = false;

    /**
     * Crate a ListEquipmentCommand with {@code arguments} given.
     */
    public ListEquipmentCommand(String arguments) {
        if (!arguments.trim().isEmpty()) {
            extraPara = true;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (extraPara) {
            throw new CommandException(EXTRA_PARA_EXCEPTION);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_EQUIPMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
