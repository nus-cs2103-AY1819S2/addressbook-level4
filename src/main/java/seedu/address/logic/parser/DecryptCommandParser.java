package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.EncryptCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.getAllPrefixes;

public class DecryptCommandParser implements Parser<DecryptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DecryptCommand
     * and returns an DecryptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DecryptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        String password;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, getAllPrefixes());

        if (CliSyntax.arePrefixesPresent(argMultimap, CliSyntax.getInvalidPrefixesForCommand(PREFIX_PASSWORD))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecryptCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecryptCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecryptCommand.MESSAGE_USAGE));
        }

        password = argMultimap.getValue(PREFIX_PASSWORD).get();
        return new DecryptCommand(index, password);
    }
}
