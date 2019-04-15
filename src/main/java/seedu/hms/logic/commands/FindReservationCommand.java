package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.function.Predicate;

import seedu.hms.commons.core.Messages;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithDatePredicate;
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
        + "Parameters: "
        + "[" + PREFIX_IDENTIFICATION_NUMBER + "CUSTOMER_IDENTIFICATION_NUMBER] "
        + "[" + PREFIX_ROOM + "ROOM TYPE]"
        + "[" + PREFIX_DATES + "DATES(DD/MM/YYYY - DD/MM/YYYY)]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_IDENTIFICATION_NUMBER + "1234567 "
        + PREFIX_ROOM + "SINGLE ROOM "
        + "[" + PREFIX_DATES + "12/12/2019 - 14/12/2019]";
    private final Predicate<Reservation> reservationPredicate;
    private final ReservationContainsPayerPredicate reservationContainsPayerPredicate;
    private final ReservationWithTypePredicate reservationWithTypePredicate;
    private final ReservationWithDatePredicate reservationWithDatePredicate;

    public FindReservationCommand(ReservationContainsPayerPredicate reservationContainsPayerPredicate,
                                  ReservationWithTypePredicate reservationWithTypePredicate,
                                  ReservationWithDatePredicate reservationWithDatePredicate) {
        this.reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested)
            && reservationWithDatePredicate.test(reservationTested);
        this.reservationContainsPayerPredicate = reservationContainsPayerPredicate;
        this.reservationWithTypePredicate = reservationWithTypePredicate;
        this.reservationWithDatePredicate = reservationWithDatePredicate;
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
            && reservationContainsPayerPredicate.equals(((FindReservationCommand) other)
            .reservationContainsPayerPredicate)
            && reservationWithTypePredicate.equals(((FindReservationCommand) other)
            .reservationWithTypePredicate)
            && reservationWithDatePredicate.equals(((FindReservationCommand) other)
            .reservationWithDatePredicate)); // state check
    }
}
