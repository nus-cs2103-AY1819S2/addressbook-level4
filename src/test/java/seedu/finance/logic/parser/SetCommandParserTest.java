package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.commands.CommandTestUtil.*;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.finance.logic.commands.SetCommand;
import seedu.finance.model.record.Amount;

public class SetCommandParserTest {
    private SetCommandParser parser = new SetCommandParser();

    @Test
    public void parse_validField_success() {
        assertParseSuccess(parser, AMOUNT_DESC_BOB, new SetCommand(VALID_AMOUNT_BOB));

    }

    @Test
    public void parse_invalidField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE);

        //no amount
        assertParseFailure(parser, " ", expectedMessage);

        //invalid amount
        System.out.println(SetCommand.COMMAND_WORD + INVALID_AMOUNT_DESC);
        assertParseFailure(parser, INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);
    }
}
