package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sets the block out dates for user.
 */
public class SetBlockOutDatesCommand extends Command {

    public static final String COMMAND_WORD = "setBlockOutDates";
    public static final String MESSAGE_SUCCESS = "Block Out Dates set:\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets block out dates.\n"
            + "Parameters: dd/mm/yyyy - dd/mm/yyyy or dd/mm/yyyy"
            + "Example: " + COMMAND_WORD + " 01/04/2019 - 04/04/2019, 06/04/2019\n";

    private final List<Calendar> blockOutDates;

    public SetBlockOutDatesCommand(List<Calendar> blockOutDates) {
        this.blockOutDates = blockOutDates;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setBlockOutDates(blockOutDates);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
