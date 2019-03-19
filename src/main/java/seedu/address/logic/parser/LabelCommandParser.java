package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.commands.LabelCommand.DEFAULT_FILENAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileName;
import seedu.address.logic.commands.LabelCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LabelCommand object
 */
public class LabelCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the LabelCommand
     * and returns an LabelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LabelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE);
        Index index;
        FileName fileName;
        try {
            if (argMultimap.getValue(PREFIX_FILE).isPresent()){
                fileName = ParserUtil.parseFileName(argMultimap.getValue(PREFIX_FILE).get(), true);
            } else {
                fileName = new FileName(DEFAULT_FILENAME);
            }
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LabelCommand.MESSAGE_USAGE), pe);
        }

        return new LabelCommand(index, fileName);

    }
}
