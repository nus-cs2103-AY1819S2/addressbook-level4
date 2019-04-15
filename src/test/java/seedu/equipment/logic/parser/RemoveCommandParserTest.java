package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.commands.CommandTestUtil.ID_DESC_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.ID_DESC_LISTB;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_SERIAL_NUMBER_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_WORKLISTID_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ID_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.equipment.logic.commands.RemoveCommand;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.SerialNumber;

public class RemoveCommandParserTest {
    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        WorkListId expectedId = new WorkListId(VALID_ID_LISTA);
        SerialNumber expectedSerialNumber = new SerialNumber(VALID_SERIAL_NUMBER_BOB);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ID_DESC_LISTA + SERIAL_NUMBER_DESC_BOB,
                new RemoveCommand(expectedId, expectedSerialNumber));

        // multiple Ids - last Id accepted
        assertParseSuccess(parser, ID_DESC_LISTB + ID_DESC_LISTA + SERIAL_NUMBER_DESC_BOB,
                new RemoveCommand(expectedId, expectedSerialNumber));

        // multiple Serial Numbers - last Serial Numbers accepted
        assertParseSuccess(parser, ID_DESC_LISTA + SERIAL_NUMBER_DESC_AMY + SERIAL_NUMBER_DESC_BOB,
                new RemoveCommand(expectedId, expectedSerialNumber));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE);

        // missing WorkListId prefix
        assertParseFailure(parser, VALID_ID_LISTA + SERIAL_NUMBER_DESC_BOB, expectedMessage);

        // missing Serial Number prefix
        assertParseFailure(parser, ID_DESC_LISTA + VALID_SERIAL_NUMBER_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ID_LISTA + VALID_SERIAL_NUMBER_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid WorkListId
        assertParseFailure(parser, INVALID_WORKLISTID_DESC + SERIAL_NUMBER_DESC_BOB,
                WorkListId.MESSAGE_CONSTRAINTS);

        // invalid Serial Number
        assertParseFailure(parser, ID_DESC_LISTA + INVALID_SERIAL_NUMBER_DESC,
                SerialNumber.MESSAGE_CONSTRAINTS);
    }
}
