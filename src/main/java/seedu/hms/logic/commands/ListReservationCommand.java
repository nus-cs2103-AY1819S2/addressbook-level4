package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationModel;

/**
 * Lists all customers in the hms book to the user.
 */
public class ListReservationCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "lr";
    public static final String COMMAND_WORD = "list-reservations";

    public static final String MESSAGE_SUCCESS = "Listed all reservations";


    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
