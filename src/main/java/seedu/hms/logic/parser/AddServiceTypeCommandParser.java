package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.stream.Stream;

import seedu.hms.logic.commands.AddServiceTypeCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.util.TimeRange;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class AddServiceTypeCommandParser implements Parser<AddServiceTypeCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddServiceTypeCommand
     * and returns an AddServiceTypeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddServiceTypeCommand parse(String args)
        throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIMING, PREFIX_CAPACITY, PREFIX_RATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIMING, PREFIX_CAPACITY, PREFIX_RATE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddServiceTypeCommand.MESSAGE_USAGE));
        }

        String serviceName = ParserUtil.parseType(argMultimap.getValue(PREFIX_NAME).get());
        TimeRange timing = ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).get());
        int capacity = ParserUtil.parseCapacity(argMultimap.getValue(PREFIX_CAPACITY).get());
        double rate = ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get());

        ServiceType serviceType = new ServiceType(capacity, timing, serviceName, rate);
        return new AddServiceTypeCommand(serviceType);
    }

}
