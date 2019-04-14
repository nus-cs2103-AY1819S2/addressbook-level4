package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_BOTTOM;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_TOP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOTTOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalApparels.ARMANY;
import static seedu.address.testutil.TypicalApparels.BOBYIN;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.testutil.ApparelBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Apparel expectedApparel = new ApparelBuilder(BOBYIN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INPUT_NAME_B + INPUT_COLOR_BLUE
                + INPUT_TYPE_BOTTOM, new AddCommand(expectedApparel));

        // multiple names - last name accepted
        assertParseSuccess(parser, INPUT_NAME_A + INPUT_NAME_B + INPUT_COLOR_BLUE
                + INPUT_TYPE_BOTTOM, new AddCommand(expectedApparel));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, INPUT_NAME_B + INPUT_COLOR_GREEN + INPUT_COLOR_BLUE
                + INPUT_TYPE_BOTTOM, new AddCommand(expectedApparel));

        // multiple emails - last email accepted
        assertParseSuccess(parser, INPUT_NAME_B + INPUT_COLOR_BLUE + INPUT_TYPE_TOP
                + INPUT_TYPE_BOTTOM, new AddCommand(expectedApparel));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Apparel expectedApparel = new ApparelBuilder(ARMANY).build();
        assertParseSuccess(parser, INPUT_NAME_A + INPUT_COLOR_GREEN + INPUT_TYPE_TOP,
                new AddCommand(expectedApparel));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_B + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, INPUT_NAME_B + VALID_COLOR_BLUE + INPUT_TYPE_BOTTOM,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, INPUT_NAME_B + INPUT_COLOR_BLUE + VALID_TYPE_BOTTOM,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_B + VALID_COLOR_BLUE + VALID_TYPE_BOTTOM,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_INPUT_NAME + INPUT_COLOR_BLUE
                + INPUT_TYPE_BOTTOM, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, INPUT_NAME_B + INVALID_INPUT_COLOR
                + INPUT_TYPE_BOTTOM, Color.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, INPUT_NAME_B + INPUT_COLOR_BLUE
                + INVALID_INPUT_TYPE, ClothingType.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_INPUT_NAME + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INPUT_NAME_B + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
