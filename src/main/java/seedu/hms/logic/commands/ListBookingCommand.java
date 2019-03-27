package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_BOOKINGS;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingModel;

/**
 * Lists all customers in the hms book to the user.
 */
public class ListBookingCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "lb";
    public static final String COMMAND_WORD = "list-bookings";

    public static final String MESSAGE_SUCCESS = "Listed all bookings";


    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
