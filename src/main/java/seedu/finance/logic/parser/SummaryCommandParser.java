package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_PERIOD_AMOUNT;
import static seedu.finance.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.finance.logic.commands.SummaryCommand;

import seedu.finance.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SummaryCommand object
 */
public class SummaryCommandParser implements Parser<SummaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns an StatsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SummaryCommand parse(String args) throws ParseException {
        if (args.length() == 0) {
            return new SummaryCommand();
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERIOD_AMOUNT, PREFIX_PERIOD);
        if (!arePrefixesPresent(argMultimap, PREFIX_PERIOD_AMOUNT, PREFIX_PERIOD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        }

        int periodAmount;
        try {
            periodAmount = Integer.parseInt(argMultimap.getValue(PREFIX_PERIOD_AMOUNT).get());
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT,
                            SummaryCommand.MESSAGE_PERIOD_AMOUNT_ERROR
                    )
            );
        }

        String period = argMultimap.getValue(PREFIX_PERIOD).get();
        try {
            return new SummaryCommand(periodAmount, period);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, iae.getMessage()));
        }
    }
}
