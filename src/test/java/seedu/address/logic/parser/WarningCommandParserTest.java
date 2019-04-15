package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPIRY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.logic.commands.WarningCommand;
import seedu.address.model.Model;
import seedu.address.model.threshold.Threshold;

public class WarningCommandParserTest {
    private WarningCommandParser parser = new WarningCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String lowStockInput = " " + PREFIX_QUANTITY + Model.DEFAULT_LOW_STOCK_THRESHOLD.getNumericValue();
        String expiryInput = " " + PREFIX_EXPIRY + Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue();

        // without additional whitespace
        assertParseSuccess(parser, lowStockInput,
                new WarningCommand(WarningPanelPredicateType.LOW_STOCK, Model.DEFAULT_LOW_STOCK_THRESHOLD));
        assertParseSuccess(parser, expiryInput,
                new WarningCommand(WarningPanelPredicateType.EXPIRY, Model.DEFAULT_EXPIRY_THRESHOLD));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + lowStockInput,
                new WarningCommand(WarningPanelPredicateType.LOW_STOCK, Model.DEFAULT_LOW_STOCK_THRESHOLD));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + expiryInput,
                new WarningCommand(WarningPanelPredicateType.EXPIRY, Model.DEFAULT_EXPIRY_THRESHOLD));

        // case insensitive "show" option
        assertParseSuccess(parser, " " + "SHOW", new WarningCommand(true));
        assertParseSuccess(parser, " " + "show", new WarningCommand(true));
        assertParseSuccess(parser, " " + "ShOw", new WarningCommand(true));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WarningCommand.MESSAGE_USAGE);

        // missing expiry prefix
        assertParseFailure(parser, " " + Model.DEFAULT_EXPIRY_THRESHOLD.value, expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, " " + Model.DEFAULT_LOW_STOCK_THRESHOLD.value, expectedMessage);

        // all fields missing
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WarningCommand.MESSAGE_USAGE);

        // invalid expiry
        assertParseFailure(parser, INVALID_EXPIRY_DESC, Threshold.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, INVALID_QUANTITY_DESC, Threshold.MESSAGE_CONSTRAINTS);

        // space in word "show"
        assertParseFailure(parser, " " + "sh ow", expectedMessage);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_QUANTITY + Model.DEFAULT_LOW_STOCK_THRESHOLD.value,
                expectedMessage);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_EXPIRY + Model.DEFAULT_EXPIRY_THRESHOLD.value,
                expectedMessage);
    }
}
