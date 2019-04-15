package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.RevenueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.statistics.Date;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Year;

/**
 * Parses input arguments and creates a new RevenueCommand object
 */
public class RevenueCommandParser implements Parser<RevenueCommand> {
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RevenueCommand
     * and returns an RevenueCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RevenueCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_YEAR, PREFIX_MONTH, PREFIX_DAY);

        Day day = null;
        Month month = null;
        Year year = null;

        if (!arePrefixesPresent(argMultimap, PREFIX_YEAR)
                && (arePrefixesPresent(argMultimap, PREFIX_MONTH) || arePrefixesPresent(argMultimap, PREFIX_DAY))
                || (!arePrefixesPresent(argMultimap, PREFIX_MONTH)
                && arePrefixesPresent(argMultimap, PREFIX_YEAR, PREFIX_DAY))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevenueCommand.MESSAGE_USAGE));

        } else if (arePrefixesPresent(argMultimap, PREFIX_YEAR, PREFIX_MONTH, PREFIX_DAY)) {
            //daily
            year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
            month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
            day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());

            StringBuilder dateString = new StringBuilder();
            dateString.append(day).append(".").append(month).append(".").append(year);

            try {
                Date date = new Date(dateString.toString());
            } catch (IllegalArgumentException exception) {
                throw new ParseException(String.format(Date.MESSAGE_CONSTRAINTS, RevenueCommand.MESSAGE_USAGE));
            }


        } else if (arePrefixesPresent(argMultimap, PREFIX_YEAR, PREFIX_MONTH)) {
            //Monthly
            year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
            month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());

        } else if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
            //Yearly
            year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        }

        Revenue revenue = new Revenue(day, month, year);

        return new RevenueCommand(revenue);
    }
}
