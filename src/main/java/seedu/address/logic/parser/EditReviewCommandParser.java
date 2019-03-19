package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW_RATING;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditReviewCommand;
import seedu.address.logic.commands.EditReviewCommand.EditReviewDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditReviewCommand object
 */
public class EditReviewCommandParser implements Parser<EditReviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditReviewCommand
     * and returns an EditReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REVIEW_ENTRY, PREFIX_REVIEW_RATING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditReviewCommand.MESSAGE_USAGE), pe);
        }

        EditReviewDescriptor editReviewDescriptor = new EditReviewDescriptor();
        if (argMultimap.getValue(PREFIX_REVIEW_ENTRY).isPresent()) {
            editReviewDescriptor.setEntry(ParserUtil.parseEntry(argMultimap.getValue(PREFIX_REVIEW_ENTRY).get()));
        }
        if (argMultimap.getValue(PREFIX_REVIEW_RATING).isPresent()) {
            editReviewDescriptor.setRating(ParserUtil.parseRating(argMultimap.getValue(PREFIX_REVIEW_RATING).get()));
        }

        if (!editReviewDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReviewCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReviewCommand(index, editReviewDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
