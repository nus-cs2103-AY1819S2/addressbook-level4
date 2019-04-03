package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;

import java.util.List;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pdf.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object.
 * Filter command is actually a modified version of find command,
 * albeit using tag as the find 'keyword'.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_TAG_NAME);

        if (trimmedArgs.isEmpty() || argMultiMap.getAllValues(PREFIX_TAG_NAME).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        List<String> tagKeywords = argMultiMap.getAllValues(PREFIX_TAG_NAME);

        return new FilterCommand(new TagContainsKeywordsPredicate(tagKeywords));
    }

}
