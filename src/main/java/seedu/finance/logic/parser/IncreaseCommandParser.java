package seedu.finance.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.util.NoSuchElementException;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.logic.commands.IncreaseCommand;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.record.Amount;

/**
 * Parses input arguments and creates a new {@code IncreaseCommand} object
 */
public class IncreaseCommandParser implements Parser<IncreaseCommand> {

    /**
     * Parses given {@code String} of arguments in context of the {@code IncreaseCommand}
     * and returns a {@code IncreaseCommand} object for execution
     * @throws ParseException if the user input does not conform to expected format
     */
    public IncreaseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        Amount amount;

        try {
            amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        } catch (IllegalValueException | NoSuchElementException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    IncreaseCommand.MESSAGE_USAGE), ive);
        }

        return new IncreaseCommand(amount);
    }
}
