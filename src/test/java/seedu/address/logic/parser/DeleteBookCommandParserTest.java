package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBooks.CS;

import org.junit.Test;

import seedu.address.logic.commands.DeleteBookCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.testutil.BookBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteBookCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteBookCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteBookCommandParserTest {

    private DeleteBookCommandParser parser = new DeleteBookCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteBookCommand() {
        Book book = new BookBuilder(CS).build();
        assertParseSuccess(parser, NAME_DESC_CS,
                new DeleteBookCommand(new BookNameContainsExactKeywordsPredicate(book.getBookName())));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBookCommand.MESSAGE_USAGE));
    }
}
