package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindBookCommand object
 */
public class FindBookCommandParser implements Parser<FindBookCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBookCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBookCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindBookCommand(new BookNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
