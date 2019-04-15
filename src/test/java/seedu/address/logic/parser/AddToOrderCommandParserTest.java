package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_2_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_3_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.model.menu.Code;
import seedu.address.model.order.OrderItemStatus;

public class AddToOrderCommandParserTest {
    private AddToOrderCommandParser parser = new AddToOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        List<Code> expectedCodes = new ArrayList<>();
        expectedCodes.add(new Code(VALID_CODE_CHICKEN));
        List<Integer> expectedQuantities = new ArrayList<>();
        expectedQuantities.add(2);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ORDER_DESC_2_CHICKEN,
                new AddToOrderCommand(expectedCodes, expectedQuantities));

        expectedCodes.add(new Code(VALID_CODE_FRIES));
        expectedQuantities.add(3);

        // multiple order items
        assertParseSuccess(parser, ORDER_DESC_2_CHICKEN + ORDER_DESC_3_FRIES,
                new AddToOrderCommand(expectedCodes, expectedQuantities));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToOrderCommand.MESSAGE_USAGE);

        // empty input
        assertParseFailure(parser, "", expectedMessage);

        // missing quantity
        assertParseFailure(parser, VALID_CODE_CHICKEN, expectedMessage);

        // missing code
        assertParseFailure(parser, VALID_QUANTITY_2, expectedMessage);

        // odd number of inputs
        assertParseFailure(parser, VALID_CODE_CHICKEN + VALID_QUANTITY_2 + VALID_CODE_FRIES, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid code
        assertParseFailure(parser, INVALID_ORDER_CODE_DESC, Code.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, INVALID_ORDER_QUANTITY_DESC, OrderItemStatus.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_ORDER_DESC, Code.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ORDER_DESC_2_CHICKEN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToOrderCommand.MESSAGE_USAGE));
    }
}
