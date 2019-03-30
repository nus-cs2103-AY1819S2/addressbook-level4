package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BOOKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_INTERESTING;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INTERESTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBooks.ALI;
import static seedu.address.testutil.TypicalBooks.CS;

import org.junit.Test;

import seedu.address.logic.commands.AddBookCommand;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

public class AddBookCommandParserTest {
    private AddBookCommandParser parser = new AddBookCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder(CS).withTags(VALID_TAG_TEXTBOOK).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS + AUTHOR_DESC_CS
                + RATING_DESC_CS + TAG_DESC_TEXTBOOK, new AddBookCommand(expectedBook));

        // multiple book names - last name accepted
        assertParseSuccess(parser, NAME_DESC_ALICE + NAME_DESC_CS + AUTHOR_DESC_CS + RATING_DESC_CS
                + TAG_DESC_TEXTBOOK, new AddBookCommand(expectedBook));

        // multiple author - last author accepted
        assertParseSuccess(parser, NAME_DESC_CS + AUTHOR_DESC_ALICE + AUTHOR_DESC_CS + RATING_DESC_CS
                + TAG_DESC_TEXTBOOK, new AddBookCommand(expectedBook));

        // multiple ratings - last rating accepted
        assertParseSuccess(parser, NAME_DESC_CS + AUTHOR_DESC_CS + RATING_DESC_ALICE + RATING_DESC_CS
                + TAG_DESC_TEXTBOOK, new AddBookCommand(expectedBook));

        // multiple tags - all accepted
        Book expectedBookMultipleTags = new BookBuilder(CS).withTags(VALID_TAG_TEXTBOOK, VALID_TAG_INTERESTING)
                .build();
        assertParseSuccess(parser, NAME_DESC_CS + AUTHOR_DESC_CS + RATING_DESC_CS
                + TAG_DESC_TEXTBOOK + TAG_DESC_INTERESTING, new AddBookCommand(expectedBookMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedBook = new BookBuilder(ALI).withTags().build();
        assertParseSuccess(parser, NAME_DESC_ALICE + AUTHOR_DESC_ALICE + RATING_DESC_ALICE,
                new AddBookCommand(expectedBook));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_BOOKNAME_CS + AUTHOR_DESC_CS + RATING_DESC_CS,
                expectedMessage);

        // missing author prefix
        assertParseFailure(parser, NAME_DESC_CS + VALID_AUTHOR_CS + RATING_DESC_CS,
                expectedMessage);

        // missing rating prefix
        assertParseFailure(parser, NAME_DESC_CS + AUTHOR_DESC_CS + VALID_RATING_CS,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_BOOKNAME_CS + VALID_AUTHOR_CS + VALID_RATING_CS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_BOOKNAME_DESC + AUTHOR_DESC_CS + RATING_DESC_CS
                + TAG_DESC_FANTASY + TAG_DESC_TEXTBOOK, BookName.MESSAGE_CONSTRAINTS);

        // invalid author
        assertParseFailure(parser, NAME_DESC_CS + INVALID_AUTHOR_DESC + RATING_DESC_CS
                + TAG_DESC_FANTASY + TAG_DESC_TEXTBOOK, Author.MESSAGE_CONSTRAINTS);

        // invalid rating
        assertParseFailure(parser, NAME_DESC_CS + AUTHOR_DESC_CS + INVALID_RATING_DESC
                + TAG_DESC_INTERESTING + TAG_DESC_TEXTBOOK, Rating.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CS + AUTHOR_DESC_CS + RATING_DESC_CS
                + INVALID_TAG_DESC + VALID_TAG_FANTASY, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_BOOKNAME_DESC + AUTHOR_DESC_CS + RATING_DESC_CS,
                BookName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CS + AUTHOR_DESC_CS + RATING_DESC_CS
                        + TAG_DESC_INTERESTING + TAG_DESC_TEXTBOOK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE));
    }
}
