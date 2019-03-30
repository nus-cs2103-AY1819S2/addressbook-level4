package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteReviewCommand object
 */
public class DeleteReviewCommandParser implements Parser<DeleteReviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReviewCommand
     * and returns an DeleteReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReviewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteReviewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReviewCommand.MESSAGE_USAGE), pe);
        }
    }

}
