package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWTITLE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_REVIEWTITLE, PREFIX_REVIEW);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_REVIEWTITLE, PREFIX_REVIEW)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReviewCommand.MESSAGE_USAGE));
        }

        BookName name = ParserUtil.parseBookName(argMultimap.getValue(PREFIX_NAME).get());
        ReviewTitle title = ParserUtil.parseReviewTitle(argMultimap.getValue(PREFIX_REVIEWTITLE).get());
        String reviewMessage = ParserUtil.parseReview(argMultimap.getValue(PREFIX_REVIEW).get());

        Review review = new Review(title, name, reviewMessage);

        return new AddReviewCommand(review);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
