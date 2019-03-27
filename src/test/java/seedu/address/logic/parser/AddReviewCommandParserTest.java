package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BOOKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REVIEWMESSAGE_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.REVIEWTITLE_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWMESSAGE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWTITLE_CS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddReviewCommand;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

public class AddReviewCommandParserTest {
    private AddReviewCommandParser parser = new AddReviewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Review expectedReview = new Review(
                new ReviewTitle(VALID_REVIEWTITLE_CS), new BookName(VALID_BOOKNAME_CS), VALID_REVIEWMESSAGE_CS);
        BookNameContainsExactKeywordsPredicate expectedPredicate =
                new BookNameContainsExactKeywordsPredicate(new BookName(VALID_BOOKNAME_CS));
        // whitespace only preamble
        assertParseSuccess(parser, NAME_DESC_CS + REVIEWTITLE_DESC_CS
                + REVIEWMESSAGE_DESC_CS, new AddReviewCommand(expectedReview));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReviewCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_BOOKNAME_CS + REVIEWTITLE_DESC_CS
                        + REVIEWMESSAGE_DESC_CS, expectedMessage);

        // missing review title prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS + VALID_REVIEWTITLE_CS
                + REVIEWMESSAGE_DESC_CS, expectedMessage);

        // missing review prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS + REVIEWTITLE_DESC_CS
                + VALID_REVIEWMESSAGE_CS, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_BOOKNAME_CS + VALID_REVIEWTITLE_CS
                + VALID_REVIEWMESSAGE_CS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_BOOKNAME_DESC + REVIEWTITLE_DESC_CS
                + REVIEWMESSAGE_DESC_CS, BookName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CS + REVIEWTITLE_DESC_CS
                + REVIEWMESSAGE_DESC_CS, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReviewCommand.MESSAGE_USAGE));
    }
}
