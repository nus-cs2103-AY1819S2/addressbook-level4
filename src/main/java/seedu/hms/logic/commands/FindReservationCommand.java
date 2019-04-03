package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.function.Predicate;

import seedu.hms.commons.core.Messages;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;

/**
 * Finds and lists all reservation in reservation list whose payer's name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindReservationCommand extends ReservationCommand {
    public static final String COMMAND_ALIAS = "fr";
    public static final String COMMAND_WORD = "find-reservation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reservations belonging to the selected "
        + "customer\n"
        + "Parameters: CUSTOMER_IDENTIFICATION_NUMBER\n"
        + "Parameters: INDEX"
        + "[" + PREFIX_ROOM + "ROOM TYPE] "
        //   + "[" + PREFIX_DATES + "DATES(DD/MM/YYYY - DD/MM/YYYY)]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_IDENTIFICATION_NUMBER + "1234567"
        + "[" + PREFIX_ROOM + "SINGLE ROOM] ";
    //  + "[" + PREFIX_DATES + "12/12/2019 - 14/12/2019]";
    private final Predicate<Reservation> reservationPredicate;

    public FindReservationCommand(ReservationContainsPayerPredicate reservationContainsPayerPredicate,
                                  ReservationWithTypePredicate reservationWithTypePredicate) {
        //    ReservationWithDatePredicate reservationWithDatePredicate) {
        this.reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested);
        //  && reservationWithDatePredicate.test(reservationTested);
    }

    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReservationList(reservationPredicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_RESERVATIONS_LISTED_OVERVIEW, model.getFilteredReservationList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindReservationCommand // instanceof handles nulls
            && reservationPredicate.equals(((FindReservationCommand) other).reservationPredicate)); // state check
    }
}
