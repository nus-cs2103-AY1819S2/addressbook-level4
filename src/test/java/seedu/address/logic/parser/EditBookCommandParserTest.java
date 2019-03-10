package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BOOKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FANTASY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditBookCommand;
import seedu.address.logic.commands.EditBookCommand.EditBookDescriptor;
import seedu.address.model.book.Author;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.book.Rating;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditBookDescriptorBuilder;

public class EditBookCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookCommand.MESSAGE_USAGE);

    private EditBookCommandParser parser = new EditBookCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no book specified
        assertParseFailure(parser, RATING_DESC_CS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, NAME_DESC_ALICE, EditBookCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_BOOKNAME_DESC, BookName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, NAME_DESC_ALICE + INVALID_AUTHOR_DESC, Author.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, NAME_DESC_ALICE + INVALID_RATING_DESC, Rating.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, NAME_DESC_ALICE + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, NAME_DESC_ALICE + INVALID_AUTHOR_DESC + RATING_DESC_ALICE, Author.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, NAME_DESC_ALICE + AUTHOR_DESC_CS + INVALID_AUTHOR_DESC, Author.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Book} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, NAME_DESC_ALICE + TAG_DESC_FANTASY + TAG_DESC_TEXTBOOK + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC_ALICE + TAG_DESC_FANTASY + TAG_EMPTY + TAG_DESC_TEXTBOOK, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC_ALICE + TAG_EMPTY + TAG_DESC_FANTASY + TAG_DESC_TEXTBOOK, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, NAME_DESC_ALICE + INVALID_AUTHOR_DESC + INVALID_RATING_DESC,
                Author.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        BookName target = new BookName(VALID_BOOKNAME_ALICE);
        BookNameContainsExactKeywordsPredicate predicate = new BookNameContainsExactKeywordsPredicate(target);
        String userInput = NAME_DESC_ALICE + AUTHOR_DESC_CS
                + RATING_DESC_ALICE + TAG_DESC_FANTASY;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE)
                .withAuthor(VALID_AUTHOR_CS).withRating(VALID_RATING_ALICE)
                .withTags(VALID_TAG_FANTASY).build();
        EditBookCommand expectedCommand = new EditBookCommand(predicate, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        BookName target = new BookName(VALID_BOOKNAME_ALICE);
        BookNameContainsExactKeywordsPredicate predicate = new BookNameContainsExactKeywordsPredicate(target);
        String userInput = NAME_DESC_ALICE + AUTHOR_DESC_CS
                + RATING_DESC_ALICE;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE)
                .withAuthor(VALID_AUTHOR_CS)
                .withRating(VALID_RATING_ALICE).build();
        EditBookCommand expectedCommand = new EditBookCommand(predicate, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // author
        BookName target = new BookName(VALID_BOOKNAME_ALICE);
        BookNameContainsExactKeywordsPredicate predicate = new BookNameContainsExactKeywordsPredicate(target);
        String userInput = NAME_DESC_ALICE + AUTHOR_DESC_CS;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE)
                .withAuthor(VALID_AUTHOR_CS).build();
        EditBookCommand expectedCommand = new EditBookCommand(predicate, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // rating
        userInput = NAME_DESC_ALICE + RATING_DESC_CS;
        descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE).withRating(VALID_RATING_CS).build();
        expectedCommand = new EditBookCommand(predicate, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = NAME_DESC_ALICE + TAG_DESC_TEXTBOOK;
        descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE).withTags(VALID_TAG_TEXTBOOK).build();
        expectedCommand = new EditBookCommand(predicate, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        BookNameContainsExactKeywordsPredicate predicate = new BookNameContainsExactKeywordsPredicate(new BookName(VALID_BOOKNAME_ALICE));
        String userInput = NAME_DESC_ALICE + AUTHOR_DESC_CS + RATING_DESC_CS
                + TAG_DESC_FANTASY + AUTHOR_DESC_ALICE + RATING_DESC_ALICE + TAG_DESC_TEXTBOOK;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE).withAuthor(VALID_AUTHOR_ALICE)
                .withRating(VALID_RATING_ALICE).withTags(VALID_TAG_FANTASY, VALID_TAG_TEXTBOOK)
                .build();
        EditBookCommand expectedCommand = new EditBookCommand(predicate, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        BookNameContainsExactKeywordsPredicate predicate = new BookNameContainsExactKeywordsPredicate(new BookName(VALID_BOOKNAME_ALICE));
        // no other valid values specified
        String userInput = NAME_DESC_ALICE + INVALID_AUTHOR_DESC + AUTHOR_DESC_CS;
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE).withAuthor(VALID_AUTHOR_CS).build();
        EditBookCommand expectedCommand = new EditBookCommand(predicate, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = NAME_DESC_ALICE + RATING_DESC_CS + INVALID_AUTHOR_DESC
                + AUTHOR_DESC_CS;
        descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE).withAuthor(VALID_AUTHOR_CS).withRating(VALID_RATING_CS)
                .build();
        expectedCommand = new EditBookCommand(predicate, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        BookNameContainsExactKeywordsPredicate predicate = new BookNameContainsExactKeywordsPredicate(new BookName(VALID_BOOKNAME_ALICE));
        String userInput = NAME_DESC_ALICE + TAG_EMPTY;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withBookName(VALID_BOOKNAME_ALICE).withTags().build();
        EditBookCommand expectedCommand = new EditBookCommand(predicate, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
