package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.hms.logic.commands.FindReservationCommand;
import seedu.hms.logic.commands.GenerateBillForReservationCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;
import seedu.hms.model.reservation.ReservationWithDatePredicate;
import seedu.hms.model.util.DateRange;
import seedu.hms.model.util.TimeRange;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class GenerateBillForReservationCommandParser implements Parser<GenerateBillForReservationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GenerateBillForReservationCommand
     * and returns an GenerateBillForReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForReservationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_IDENTIFICATION_NUMBER,
                PREFIX_ROOM, PREFIX_DATES);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindReservationCommand.MESSAGE_USAGE));
        }

        ReservationContainsPayerPredicate reservationContainsPayerPredicate;
        if (argMultimap.getValue(PREFIX_IDENTIFICATION_NUMBER).isPresent()) {
            reservationContainsPayerPredicate = new ReservationContainsPayerPredicate(
                ParserUtil.parseIdNum(argMultimap.getValue(PREFIX_IDENTIFICATION_NUMBER).get()).toString());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindReservationCommand.MESSAGE_USAGE));
        }

        ReservationWithTypePredicate reservationWithTypePredicate;
        if (argMultimap.getValue(PREFIX_SERVICE).isPresent()) {
            reservationWithTypePredicate = new ReservationWithTypePredicate(
                ParserUtil.parseService(argMultimap.getValue(PREFIX_SERVICE).get()).getName());
        } else {
            reservationWithTypePredicate = new ReservationWithTypePredicate("");
        }

        //search in whole day if timing is not provided
        DateRange dateRange = ParserUtil.parseDates(argMultimap.getValue(PREFIX_DATES)
            .orElse("01/01/0000 - 31/12/9999"));
        ReservationWithDatePredicate reservationWithinDatePredicate = new ReservationWithDatePredicate(dateRange);

        return new GenerateBillForReservationCommand(reservationContainsPayerPredicate,
            reservationWithTypePredicate,
            reservationWithinDatePredicate);
    }


}
