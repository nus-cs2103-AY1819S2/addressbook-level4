package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectReviewCommand object
 */
public class SelectReviewCommandParser implements Parser<SelectReviewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectReviewCommand
     * and returns an SelectReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectReviewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectReviewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectReviewCommand.MESSAGE_USAGE), pe);
        }
    }
}
