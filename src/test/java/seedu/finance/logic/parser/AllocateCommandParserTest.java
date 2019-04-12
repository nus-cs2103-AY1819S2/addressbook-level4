package seedu.finance.logic.parser;
//@@ author Jackimaru96

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.finance.logic.commands.AllocateCommand;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;

public class AllocateCommandParserTest {
    private AllocateCommandParser parser = new AllocateCommandParser();

    @Test
    public void parse_validFields_success() {
        assertParseSuccess(parser, AMOUNT_DESC_BOB + CATEGORY_DESC_FRIEND,
                new AllocateCommand(new CategoryBudget(VALID_CATEGORY_FRIEND, Double.parseDouble(VALID_AMOUNT_BOB))));
    }

    @Test
    public void parse_invalidFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AllocateCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, " ", expectedMessage);

        // no category
        assertParseFailure(parser, AMOUNT_DESC_BOB + PREFIX_CATEGORY + " ", expectedMessage);

        // invalid category
        assertParseFailure(parser, AMOUNT_DESC_BOB + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);

        // no amount
        assertParseFailure(parser, PREFIX_AMOUNT + " " + CATEGORY_DESC_FRIEND, expectedMessage);

        // invalid amount
        assertParseFailure(parser, INVALID_AMOUNT_DESC + CATEGORY_DESC_FRIEND, Amount.MESSAGE_CONSTRAINTS);
    }
}
