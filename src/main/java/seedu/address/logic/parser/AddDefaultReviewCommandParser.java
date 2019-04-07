package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddDefaultReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates new AddDefaultReviewCommand object
 */
public class AddDefaultReviewCommandParser implements Parser<AddDefaultReviewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddDefaultReviewCommand
     * and returns an AddDefaultReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDefaultReviewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE));
        }

        String[] indexAndIndicator = trimmedArgs.split("\\s+");

        //checks if there are NOT 2 arguments, this command must have 2 arguments only.
        if (indexAndIndicator.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE));
        }
        Index index;
        String defaultReviewIndicator;
        //checks if the 2 arguments are integers
        if (!indexAndIndicator[0].matches("[1-9]\\d*") || !indexAndIndicator[1].matches("[1-9]\\d*")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE));
        }
        index = Index.fromOneBased(Integer.parseInt(indexAndIndicator[0]));
        defaultReviewIndicator = indexAndIndicator[1];

        return new AddDefaultReviewCommand(index, defaultReviewIndicator);
    }
}
