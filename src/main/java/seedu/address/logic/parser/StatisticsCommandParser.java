package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and returns a StatisticsCommand
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    private static final String MMYY_REGEX = "^(0[1-9]|1[0-2])(\\d{2})$";
    private static final DateTimeFormatter MMYY_FORMATTER = DateTimeFormatter.ofPattern("MMyy");
    /**
     * Parses the given {@code String} of arguments in the context of the StatisticsCommand
     * and returns a StatisticsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatisticsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        final String[] tokens = trimmedArgs.split("\\s+");
        // check if there are at least 1 arguments
        if (tokens.length < 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
        // check if the FROM MMYY is valid
        if (!tokens[0].matches(MMYY_REGEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
        YearMonth fromYearMonth = YearMonth.parse(tokens[0], MMYY_FORMATTER);
        YearMonth toYearMonth = fromYearMonth;
        // if there is a TO MMYY, check if it is valid
        if (tokens.length == 2) {
            if (!tokens[1].matches(MMYY_REGEX)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
            }
            toYearMonth = YearMonth.parse(tokens[1], MMYY_FORMATTER);
        }
        return new StatisticsCommand(fromYearMonth, toYearMonth);
    }
}
