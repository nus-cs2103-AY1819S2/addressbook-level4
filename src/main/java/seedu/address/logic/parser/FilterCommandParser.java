package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import java.util.Arrays;
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
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        List<String> tagKeywords = Arrays.asList(trimmedArgs.split("\\s+"));
        for (String s : tagKeywords) {
            if (!s.startsWith(PREFIX_TAG_ADD.toString())) {
                throw new ParseException(
                        String.format(MESSAGE_MISSING_PREFIX, FilterCommand.MESSAGE_USAGE));
            }
        }

        //Remove prefix to prepare for filter command
        tagKeywords.replaceAll(
            x -> x.replaceFirst(PREFIX_TAG_ADD.toString(), ""));

        return new FilterCommand(new TagContainsKeywordsPredicate(tagKeywords));
    }

}
