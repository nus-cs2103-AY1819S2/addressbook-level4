package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.Model;

/**
 * Clears the hms book.
 */
public class ClearCustomerCommand extends Command {

    public static final String COMMAND_ALIAS = "clearc";
    public static final String COMMAND_WORD = "clearcustomers";
    public static final String MESSAGE_SUCCESS = "hms book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setHotelManagementSystem(new HotelManagementSystem());
        model.commitHotelManagementSystem();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
