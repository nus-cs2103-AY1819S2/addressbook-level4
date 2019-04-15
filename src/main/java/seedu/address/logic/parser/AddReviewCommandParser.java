package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW_RATING;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

/**
 * Parses input arguments and creates a new AddReviewCommand object
 */
public class AddReviewCommandParser implements Parser<AddReviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReviewCommand
     * and returns an AddReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REVIEW_ENTRY, PREFIX_REVIEW_RATING);

        if (!arePrefixesPresent(argMultimap, PREFIX_REVIEW_ENTRY, PREFIX_REVIEW_RATING)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReviewCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReviewCommand.MESSAGE_USAGE), pe);
        }

        Entry entry = ParserUtil.parseEntry(argMultimap.getValue(PREFIX_REVIEW_ENTRY).get());
        Rating rating = ParserUtil.parseRating(argMultimap.getValue(PREFIX_REVIEW_RATING).get());


        Review review = new Review(entry, rating);

        return new AddReviewCommand(index, review);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
