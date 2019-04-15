package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import quickdocs.logic.commands.StatisticsCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.record.StatisticsManager;

/**
 * Parses input argument and returns a StatisticsCommand
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    private static final String MMYYYY_REGEX = "^(0[1-9]|1[0-2])(\\d{4})$";
    private static final DateTimeFormatter MMYYYY_FORMATTER = DateTimeFormatter.ofPattern("MMyyyy");
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
        // check if the FROM MMYYYY is valid
        if (!tokens[0].matches(MMYYYY_REGEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
        YearMonth fromYearMonth = YearMonth.parse(tokens[0], MMYYYY_FORMATTER);
        // check if the FROM MMYYYY is not before January 2019
        if (fromYearMonth.isBefore(StatisticsManager.START_DATE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
        YearMonth toYearMonth = fromYearMonth;
        // if there is a TO MMYYYY, check if it is valid
        if (tokens.length == 2) {
            if (!tokens[1].matches(MMYYYY_REGEX)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
            }
            toYearMonth = YearMonth.parse(tokens[1], MMYYYY_FORMATTER);
        }
        // check if TO MMYYYY is not before FROM MMYYYY
        if (toYearMonth.isBefore(fromYearMonth)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
        return new StatisticsCommand(fromYearMonth, toYearMonth);
    }
}
