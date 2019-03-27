package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.EMPTY_STR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SORT_TYPE_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_ASC_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_ASC_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITH_PREFIX;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortBookCommand;

public class SortBookCommandParserTest {

    private SortBookCommandParser parser = new SortBookCommandParser();

    @Test
    public void parse_validValue_success() {

        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX + ORDER_ASC_WITH_PREFIX,
            new SortBookCommand(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX));

        assertParseSuccess(parser,
            SORT_NAME_WITH_PREFIX + ORDER_ASC_WITH_PREFIX,
            new SortBookCommand(SORT_NAME_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX));

        assertParseSuccess(parser,
            SORT_RATING_WITH_PREFIX + ORDER_ASC_WITH_PREFIX,
            new SortBookCommand(SORT_RATING_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX));

        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX + ORDER_DES_WITH_PREFIX,
            new SortBookCommand(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX));

        assertParseSuccess(parser,
            SORT_NAME_WITH_PREFIX + ORDER_DES_WITH_PREFIX,
            new SortBookCommand(SORT_NAME_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX));

        assertParseSuccess(parser,
            SORT_RATING_WITH_PREFIX + ORDER_DES_WITH_PREFIX,
            new SortBookCommand(SORT_RATING_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE);

        // missing sort type
        assertParseFailure(parser, SortBookCommand.COMMAND_WORD + ORDER_ASC_WITH_PREFIX, expectedMessage);

        // missing order type
        assertParseFailure(parser, SortBookCommand.COMMAND_WORD + SORT_AUTHOR_WITH_PREFIX, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE);

        // invalid type
        assertParseFailure(parser, SortBookCommand.COMMAND_WORD
            + INVALID_SORT_TYPE_WITH_PREFIX + ORDER_DES_WITH_PREFIX, expectedMessage);

        // invalid order
        assertParseFailure(parser, SortBookCommand.COMMAND_WORD
            + SORT_NAME_WITH_PREFIX + INVALID_ORDER_WITH_PREFIX, expectedMessage);

        // empty parser
        assertParseFailure(parser, SortBookCommand.COMMAND_WORD
            + EMPTY_STR, expectedMessage);
    }

}
