package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sets the block out dates for user.
 */
public class SetBlockOutDatesCommand extends Command {

    public static final String COMMAND_WORD = "setBlockOutDates";

    public static final String MESSAGE_SET_DATES_SUCCESS = "Block Out Dates set:\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Calendar> blockOutDates = new ArrayList<>();
        model.setBlockOutDates(blockOutDates);
        model.commitAddressBook();
        return null;
    }

}
