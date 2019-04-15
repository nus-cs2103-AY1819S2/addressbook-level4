package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.ListBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookListFilterPredicate;


/**
 * Parses input arguments and creates a new ListBookCommand object
 */
public class ListBookCommandParser implements Parser<ListBookCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListBookCommand
     * and returns an ListBookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListBookCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_TAG, PREFIX_RATING);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListBookCommand.MESSAGE_USAGE));
        }

        if (!areKeywordsSingleWords(argMultimap, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_TAG, PREFIX_RATING)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListBookCommand.MESSAGE_USAGE));
        }

        List<String> names = argMultimap.getAllValues(PREFIX_NAME);
        List<String> authors = argMultimap.getAllValues(PREFIX_AUTHOR);
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG);
        List<String> ratings = argMultimap.getAllValues(PREFIX_RATING);

        BookListFilterPredicate bookFilter = new BookListFilterPredicate(names, authors, tags, ratings);
        return new ListBookCommand(bookFilter);
    }

    /**
     * Returns true if all keywords provided are single words.
     * {@code ArgumentMultimap}.
     */
    private static boolean areKeywordsSingleWords(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> isKeywordSingleWord(argumentMultimap, prefix));
    }

    /**
     * Returns true if for a certain type of keywords, all are single words.
     * {@code ArgumentMultimap}.
     */
    private static boolean isKeywordSingleWord(ArgumentMultimap argumentMultimap, Prefix prefix) {
        List<String> keywords = argumentMultimap.getAllValues(prefix);
        return keywords.stream().allMatch(keyword -> {
            String preppedWord = keyword.trim();
            return preppedWord.split("\\s+").length == 1;
        });
    }
}
