package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;

import org.junit.Test;

import seedu.address.logic.commands.EditSeatsCommand;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

public class EditSeatsCommandParserTest {

    public static final String VALID_TABLE_STATUS = "7";

    private EditSeatsCommandParser parser = new EditSeatsCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "2 7", new EditSeatsCommand(TABLE2.getTableNumber(),
                VALID_TABLE_STATUS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing inputs
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSeatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid table number
        assertParseFailure(parser, "a 0", TableNumber.MESSAGE_CONSTRAINTS);

        // invalid table status
        assertParseFailure(parser, "1 @", TableStatus.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "2 7", TableNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNumberOfInputs_failure() {
        // extra inputs
        assertParseFailure(parser, "2 7 9",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSeatsCommand.MESSAGE_USAGE));

        // insufficient inputs
        assertParseFailure(parser, "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSeatsCommand.MESSAGE_USAGE));
    }
}
