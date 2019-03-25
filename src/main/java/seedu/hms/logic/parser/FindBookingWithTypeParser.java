package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_BOOKING_SERVICE_TYPE;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hms.logic.commands.FindBookingWithTypeCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.booking.BookingWithTypePredicate;
import seedu.hms.model.booking.ServiceType;

/**
 * Parses input arguments and creates a new FindNameCommand object
 */
public class FindBookingWithTypeParser implements Parser<FindBookingWithTypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindBookingContainsPayerCommand
     * and returns an FindBookingContainsPayerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBookingWithTypeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookingWithTypeCommand.MESSAGE_USAGE));
        }
        String serviceType = args.substring(1);
        if (!ServiceType.typeExist(serviceType)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_BOOKING_SERVICE_TYPE, FindBookingWithTypeCommand.MESSAGE_USAGE));
        }
        return new FindBookingWithTypeCommand(new BookingWithTypePredicate(serviceType));
    }

}
