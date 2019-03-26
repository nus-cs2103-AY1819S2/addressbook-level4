package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sets the maximum number of interviews that can be held in a day.
 */
public class SetMaxInterviewsADayCommand extends Command {

    public static final String COMMAND_WORD = "setMaxInterviewsADay";
    public static final String MESSAGE_SUCCESS = "Maximum number of interviews per day set";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the maximum number of interviews a day.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n";

    private final int maxInterviewsADay;

    public SetMaxInterviewsADayCommand(int maxInterviewsADay) {
        this.maxInterviewsADay = maxInterviewsADay;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setMaxInterviewsADay(maxInterviewsADay);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
