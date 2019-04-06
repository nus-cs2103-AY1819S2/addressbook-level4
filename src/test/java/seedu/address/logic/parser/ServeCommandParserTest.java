package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_2_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CHICKEN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ServeCommand;
import seedu.address.model.menu.Code;
import seedu.address.model.order.OrderItemStatus;

public class ServeCommandParserTest {
    private ServeCommandParser parser = new ServeCommandParser();

    @Test
    public void parse_validValue_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ORDER_DESC_2_CHICKEN,
                new ServeCommand(new Code("W09"), 2));

        // quantity not specified
        assertParseSuccess(parser, VALID_CODE_CHICKEN,
                new ServeCommand(new Code("W09"), 1));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid code
        assertParseFailure(parser, INVALID_ORDER_CODE_DESC, Code.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, INVALID_ORDER_QUANTITY_DESC, OrderItemStatus.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_ORDER_DESC, Code.MESSAGE_CONSTRAINTS);
    }
}
