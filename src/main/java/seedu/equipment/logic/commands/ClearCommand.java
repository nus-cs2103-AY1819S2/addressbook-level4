package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;

/**
 * Clears the equipment book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Equipment Manager has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setAddressBook(new EquipmentManager());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
