package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Inventory;
import seedu.address.model.Model;

/**
 * Clears the inventory.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Inventory has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setInventory(new Inventory());
        model.commitInventory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
