package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddDefaultReviewUtil.DEFAULT_ENTRIES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddDefaultReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

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

        checkForTwoArguments(indexAndIndicator);
        checkForIntegerArguments(indexAndIndicator);
        checkForValidDefaultIndex(indexAndIndicator);

        Index index;
        int defaultReviewIndicatorInt;

        index = Index.fromOneBased(Integer.parseInt(indexAndIndicator[0]));
        defaultReviewIndicatorInt = Integer.parseInt(indexAndIndicator[1]);

        Rating newRating = new Rating(String.valueOf(defaultReviewIndicatorInt));
        Entry newEntry = new Entry(DEFAULT_ENTRIES.get(defaultReviewIndicatorInt - 1));
        Review newReview = new Review(newEntry, newRating);

        return new AddDefaultReviewCommand(index, newReview);
    }

    /**
     * Checks that the user input only two arguments.
     */
    private void checkForTwoArguments(String[] indexAndIndicator) throws ParseException {
        if (indexAndIndicator.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks that the user input arguments are integers.
     */
    private void checkForIntegerArguments(String[] indexAndIndicator) throws ParseException {
        if (!indexAndIndicator[0].matches("[1-9]\\d*") || !indexAndIndicator[1].matches("[1-9]\\d*")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks that the input default review index is between 1 to 5 inclusive.
     */
    private void checkForValidDefaultIndex(String[] indexAndIndicator) throws ParseException {
        int defaultReviewIndicatorInt = Integer.parseInt(indexAndIndicator[1]);

        if (defaultReviewIndicatorInt > 5 || defaultReviewIndicatorInt < 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDefaultReviewCommand.MESSAGE_USAGE));
        }
    }
}
