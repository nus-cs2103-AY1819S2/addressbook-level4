package seedu.finance.logic.parser;
//@@ author Jackimaru96

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.finance.logic.commands.AllocateCommand;

public class AllocateCommandParserTest {
    private AllocateCommandParser parser = new AllocateCommandParser();

    @Test
    public void parse_invalidFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AllocateCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AllocateCommand.COMMAND_WORD, expectedMessage);

        // no category
        assertParseFailure(parser, AllocateCommand.COMMAND_WORD + " " + PREFIX_AMOUNT + VALID_AMOUNT_AMY
                + PREFIX_CATEGORY, expectedMessage);

        // no amount
        assertParseFailure(parser, AllocateCommand.COMMAND_WORD + " " + PREFIX_AMOUNT + " " + PREFIX_CATEGORY
                + VALID_CATEGORY_FRIEND, expectedMessage);
    }
}
