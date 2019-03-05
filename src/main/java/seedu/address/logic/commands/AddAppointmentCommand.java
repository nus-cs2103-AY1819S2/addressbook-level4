package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add-appt";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException{
        throw new CommandException("test");
    }
}
