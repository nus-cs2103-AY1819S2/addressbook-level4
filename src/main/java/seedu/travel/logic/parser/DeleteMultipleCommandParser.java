package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.travel.commons.core.index.Index;
import seedu.travel.commons.util.StringUtil;
import seedu.travel.logic.commands.DeleteMultipleCommand;
import seedu.travel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteMultipleCommandParser implements Parser<DeleteMultipleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
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
        String trimmedStartIndex = trimmedIndex[0];
        String trimmedEndIndex = trimmedIndex[1];
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedStartIndex)
                || !StringUtil.isNonZeroUnsignedInteger(trimmedEndIndex)) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_INDEX);
        }
        Index startIndex = Index.fromOneBased(Integer.valueOf(trimmedStartIndex));
        Index endIndex = Index.fromOneBased(Integer.valueOf(trimmedEndIndex));

        return new DeleteMultipleCommand(startIndex, endIndex);
    }
}
