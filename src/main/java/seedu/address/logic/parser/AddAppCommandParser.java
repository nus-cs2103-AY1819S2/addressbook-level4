package seedu.address.logic.parser;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddAppCommandParser implements Parser<AddAppCommand> {
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_START = new Prefix("s/");
    public static final Prefix PREFIX_END = new Prefix("e/");

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppCommand
     * and returns an AddAppCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATE, PREFIX_START, PREFIX_END);

        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_INDEX,
                PREFIX_DATE, PREFIX_START, PREFIX_END);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        if (!prefixesPresent || !preamblePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get().trim());
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get().trim());
        LocalDateTime start = date.atTime(startTime);
        LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END).get().trim());
        LocalDateTime end = date.atTime(endTime);

        return new AddAppCommand(index, start, end);
//        try {
//            // token: index, date, start, end
//            String[] token = args.split("\\s");
//            //System.out.println(args);
//            //System.out.println(token[1]);
//            Index index = ParserUtil.parseIndex(token[1]);
//            //System.out.println("this working");
//
//            char[] value = new char[2];
//            value[0] = token[2].charAt(0);
//            value[1] = token[2].charAt(1);
//            Integer day = Integer.parseInt(new String(value));
//
//            value[0] = token[2].charAt(2);
//            value[1] = token[2].charAt(3);
//            Integer month = Integer.parseInt(new String(value));
//
//            value[0] = token[2].charAt(4);
//            value[1] = token[2].charAt(5);
//            int year = Integer.parseInt(new String(value));
//            year += 2000;
//
//            value[0] = token[3].charAt(0);
//            value[1] = token[3].charAt(1);
//            Integer startHour = Integer.parseInt(new String(value));
//
//            value[0] = token[3].charAt(2);
//            value[1] = token[3].charAt(3);
//            Integer startMin = Integer.parseInt(new String(value));
//
//            value[0] = token[4].charAt(0);
//            value[1] = token[4].charAt(1);
//            Integer endHour = Integer.parseInt(new String(value));
//
//            value[0] = token[4].charAt(2);
//            value[1] = token[4].charAt(3);
//            Integer endMin = Integer.parseInt(new String(value));
//
//
//            LocalDateTime start = LocalDateTime.of(year, month, day, startHour, startMin);
//            LocalDateTime end = LocalDateTime.of(year, month, day, endHour, endMin);
//
//            return new AddAppCommand(index, start, end);
//        } catch (ParseException pe) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE), pe);
//        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
