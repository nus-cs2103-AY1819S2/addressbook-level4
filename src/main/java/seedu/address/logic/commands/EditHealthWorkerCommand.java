package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class EditHealthWorkerCommand extends EditCommand implements HealthWorkerCommand{

    public static final String MESSAGE_EDIT_HEALTHWORKER_SUCCESS = "Edited Health Worker: %1$s";

    @Override
    public void edit(Model model, Object toEdit, Object edited) {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
