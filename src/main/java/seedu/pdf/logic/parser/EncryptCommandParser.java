package seedu.pdf.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.pdf.logic.parser.CliSyntax.getAllPrefixes;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.EncryptCommand;
import seedu.pdf.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class EncryptCommandParser implements Parser<EncryptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EncryptCommand
     * and returns an EncryptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EncryptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        String password;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, getAllPrefixes());

        if (CliSyntax.arePrefixesPresent(argMultimap, CliSyntax.getInvalidPrefixesForCommand(PREFIX_PASSWORD))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EncryptCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EncryptCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EncryptCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_PASSWORD).get().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EncryptCommand.MESSAGE_USAGE));
        }

        password = argMultimap.getValue(PREFIX_PASSWORD).get();
        return new EncryptCommand(index, password);
    }
}
