package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.equipment.testutil.TypicalEquipments.AMY;
import static seedu.equipment.testutil.TypicalEquipments.BOB;

import org.junit.Test;

import seedu.equipment.logic.commands.AddClientCommand;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.testutil.EquipmentBuilder;

public class AddClientCommandParserTest {
    private AddClientCommandParser parser = new AddClientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Equipment expectedEquipment = new EquipmentBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB,
                new AddClientCommand(expectedEquipment.getName()));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB,
                new AddClientCommand(expectedEquipment.getName()));
    }


    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Equipment expectedEquipment = new EquipmentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY, new AddClientCommand(expectedEquipment.getName()));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB, expectedMessage);
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
    }

}
