package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_DELETEM_INDEX;

import seedu.travel.commons.core.index.Index;
import seedu.travel.commons.util.StringUtil;
import seedu.travel.logic.commands.DeleteMultipleCommand;
import seedu.travel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteMultipleCommandParser implements Parser<DeleteMultipleCommand> {

    private static final int ARGUMENT_END_INDEX = 1;
    private static final int ARGUMENT_START_INDEX = 0;
    private static final int NUM_OF_REQUIRED_ARGUMENTS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMultipleCommand
     * and returns an DeleteMultipleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMultipleCommand parse(String args) throws ParseException {

        try {
            return parseMultipleIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMultipleCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    private DeleteMultipleCommand parseMultipleIndex(String args) throws ParseException {

        String[] trimmedIndex = args.trim().split(" ");

        if (trimmedIndex.length != NUM_OF_REQUIRED_ARGUMENTS) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex[ARGUMENT_START_INDEX])
                || !StringUtil.isNonZeroUnsignedInteger(trimmedIndex[ARGUMENT_END_INDEX])) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_INDEX);
        }

        String trimmedStartIndex = trimmedIndex[ARGUMENT_START_INDEX];
        String trimmedEndIndex = trimmedIndex[ARGUMENT_END_INDEX];

        if (Integer.valueOf(trimmedStartIndex) > Integer.valueOf(trimmedEndIndex)) {
            throw new ParseException(MESSAGE_INVALID_DELETEM_INDEX);
        }

        Index startIndex = Index.fromOneBased(Integer.valueOf(trimmedStartIndex));
        Index endIndex = Index.fromOneBased(Integer.valueOf(trimmedEndIndex));

        return new DeleteMultipleCommand(startIndex, endIndex);
    }
}
