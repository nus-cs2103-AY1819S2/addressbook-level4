package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hms.logic.commands.FindBookingContainsPayerCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.booking.BookingContainsPayerPredicate;

/**
 * Parses input arguments and creates a new FindNameCommand object
 */
public class FindBookingContainsPayerParser implements Parser<FindBookingContainsPayerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindBookingContainsPayerCommand
     * and returns an FindBookingContainsPayerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBookingContainsPayerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookingContainsPayerCommand.MESSAGE_USAGE));
        }
        String payerId = args.substring(1);
        return new FindBookingContainsPayerCommand(new BookingContainsPayerPredicate(payerId));
    }

}

