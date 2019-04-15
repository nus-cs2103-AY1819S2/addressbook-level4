package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import quickdocs.logic.commands.AddRemCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.reminder.Reminder;

/**
 * Parses input arguments and creates a new {@code AddRemCommand} object.
 */
public class AddRemCommandParser implements Parser<AddRemCommand> {
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_START = new Prefix("s/");
    public static final Prefix PREFIX_END = new Prefix("e/");
    public static final Prefix PREFIX_COMMENT = new Prefix("c/");

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddRemCommand}
     * and returns an {@code AddRemCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddRemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE, PREFIX_START, PREFIX_END, PREFIX_COMMENT);

        // check if required prefixes are present
        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_TITLE,
                PREFIX_DATE, PREFIX_START);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        if (!prefixesPresent || !preamblePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemCommand.MESSAGE_USAGE));
        }

        String title = argMultimap.getValue(PREFIX_TITLE).get().trim();
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        LocalTime start = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get().trim());
        LocalTime end;
        String comment;

        // check if end time was provided
        if (argMultimap.getValue(PREFIX_END).isPresent()) {
            end = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END).get().trim());
        } else {
            end = null;
        }

        // check if comments were provided
        if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            comment = argMultimap.getValue(PREFIX_COMMENT).get().trim();
        } else {
            comment = null;
        }

        return new AddRemCommand(new Reminder(title, comment, date, start, end));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
