package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListReviewCommand object
 */
public class ListReviewCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ListReviewCommand
     * and returns an ListReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListReviewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListReviewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListReviewCommand.MESSAGE_USAGE), pe);
        }
    }
}
