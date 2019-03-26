package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRestOrRant.FRENCH_FRIES;

import org.junit.Test;

import seedu.address.logic.commands.AddToMenuCommand;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.testutil.MenuItemBuilder;

public class AddToMenuCommandParserTest {
    private AddToMenuCommandParser parser = new AddToMenuCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        MenuItem expectedMenuItem = new MenuItemBuilder(FRENCH_FRIES).build();
        System.out.println(expectedMenuItem.toString());

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_FRIES + CODE_DESC_FRIES + PRICE_DESC_FRIES,
                new AddToMenuCommand(expectedMenuItem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_CHICKEN + NAME_DESC_FRIES + CODE_DESC_FRIES + PRICE_DESC_FRIES,
                new AddToMenuCommand(expectedMenuItem));

        // multiple codes - last code accepted
        assertParseSuccess(parser, NAME_DESC_FRIES + CODE_DESC_CHICKEN + CODE_DESC_FRIES + PRICE_DESC_FRIES,
                new AddToMenuCommand(expectedMenuItem));

        // multiple prices - last price accepted
        assertParseSuccess(parser, NAME_DESC_FRIES + CODE_DESC_FRIES + PRICE_DESC_CHICKEN + PRICE_DESC_FRIES,
                new AddToMenuCommand(expectedMenuItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToMenuCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_FRIES + CODE_DESC_FRIES + PRICE_DESC_FRIES, expectedMessage);

        // missing code prefix
        assertParseFailure(parser, NAME_DESC_FRIES + VALID_CODE_FRIES + PRICE_DESC_FRIES, expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_FRIES + CODE_DESC_FRIES + VALID_PRICE_FRIES, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_FRIES + VALID_CODE_FRIES + VALID_PRICE_FRIES, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + CODE_DESC_FRIES + PRICE_DESC_FRIES,
                Name.MESSAGE_CONSTRAINTS);

        // invalid code
        assertParseFailure(parser, NAME_DESC_FRIES + INVALID_CODE_DESC + PRICE_DESC_FRIES,
                Code.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_FRIES + CODE_DESC_FRIES + INVALID_PRICE_DESC,
                Price.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + CODE_DESC_FRIES + INVALID_PRICE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_FRIES + CODE_DESC_FRIES + PRICE_DESC_FRIES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToMenuCommand.MESSAGE_USAGE));
    }

}
