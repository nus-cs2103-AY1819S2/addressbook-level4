package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.EMPTY_STR;

import static seedu.address.logic.commands.CommandTestUtil.FIRST_SUBORDER_ASC_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.FIRST_SUBORDER_DES_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SORT_TYPE_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_ASC_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_ASC_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SECOND_SUBORDER_DES_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.THIRD_SUBORDER_DES_WITH_PREFIX;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import seedu.address.logic.commands.SortBookCommand;

public class SortBookCommandParserTest {

    private SortBookCommandParser parser = new SortBookCommandParser();

    @Test
    public void parse_validValue_success() {

        List<String> types = new ArrayList<>();

        // without specify order with one type
        types.add(SORT_AUTHOR_WITHOUT_PREFIX);
        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX,
            new SortBookCommand(types, null, new HashMap<>()));

        // with main order
        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX + ORDER_ASC_WITH_PREFIX,
            new SortBookCommand(types, ORDER_ASC_WITHOUT_PREFIX, new HashMap<>()));

        // without specify order with two types
        types.add(SORT_NAME_WITHOUT_PREFIX);
        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX + SORT_NAME_WITH_PREFIX,
            new SortBookCommand(types, null, new HashMap<>()));

        // with sub order
        Map<String, String> map = new HashMap<>();
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX + SORT_NAME_WITH_PREFIX + FIRST_SUBORDER_DES_WITH_PREFIX,
            new SortBookCommand(types,
                null,
                map));

        // with both main and sub order
        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX
                + SORT_NAME_WITH_PREFIX
                + ORDER_DES_WITH_PREFIX
                + FIRST_SUBORDER_DES_WITH_PREFIX,
            new SortBookCommand(types,
                ORDER_DES_WITHOUT_PREFIX,
                map));

        // with only 3rd order
        map = new HashMap<>();
        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        types.add(SORT_RATING_WITHOUT_PREFIX);
        assertParseSuccess(parser,
            SORT_AUTHOR_WITH_PREFIX
                + SORT_NAME_WITH_PREFIX
                + SORT_RATING_WITH_PREFIX
                + THIRD_SUBORDER_DES_WITH_PREFIX,
            new SortBookCommand(types,
                null,
                map));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE);

        // missing sort type
        assertParseFailure(parser, SortBookCommand.COMMAND_WORD + ORDER_ASC_WITH_PREFIX, expectedMessage);

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

        // extra sub Order
        assertParseFailure(parser, SortBookCommand.COMMAND_WORD
            + SORT_RATING_WITH_PREFIX
            + FIRST_SUBORDER_ASC_WITH_PREFIX
            + SECOND_SUBORDER_DES_WITH_PREFIX, expectedMessage);

    }

}
