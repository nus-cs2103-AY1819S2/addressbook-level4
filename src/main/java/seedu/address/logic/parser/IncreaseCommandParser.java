package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.IncreaseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Amount;

public class IncreaseCommandParser implements Parser<IncreaseCommand> {

    /**
     * Parses given {@code String} of arguments in context of the {@code IncreaseCommand
     * @return a {@code IncreaseCommand} object for execution
     * @throws ParseException if the user input does not conform to expected format
     */
    public IncreaseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        Amount amount;

        try {
            amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    IncreaseCommand.MESSAGE_USAGE), ive);
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    IncreaseCommand.MESSAGE_USAGE), e);
        }

        return new IncreaseCommand(amount);
    }
}
