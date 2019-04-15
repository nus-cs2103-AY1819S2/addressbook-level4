package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_SERIAL_NUMBER_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.equipment.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.EditCommand;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Date;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.testutil.EditEquipmentDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, VALID_SERIAL_NUMBER_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid equipment
        assertParseFailure(parser, "1" + INVALID_SERIAL_NUMBER_DESC,
                SerialNumber.MESSAGE_CONSTRAINTS); // serial number
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid date
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + DATE_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Equipment} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATE_DESC + VALID_ADDRESS_AMY
                        + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + DATE_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + SERIAL_NUMBER_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withDate(VALID_DATE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withSerialNumber(VALID_SERIAL_NUMBER_AMY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + DATE_DESC_AMY + SERIAL_NUMBER_DESC_AMY;

        EditCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withDate(VALID_DATE_AMY).withSerialNumber(VALID_SERIAL_NUMBER_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder().withName(VALID_NAME_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditEquipmentDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_AMY;
        descriptor = new EditEquipmentDescriptorBuilder().withDate(VALID_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // equipment
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditEquipmentDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // serial number
        userInput = targetIndex.getOneBased() + SERIAL_NUMBER_DESC_AMY;
        descriptor = new EditEquipmentDescriptorBuilder().withSerialNumber(VALID_SERIAL_NUMBER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditEquipmentDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + SERIAL_NUMBER_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + SERIAL_NUMBER_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB
                + SERIAL_NUMBER_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withDate(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB).withSerialNumber(VALID_SERIAL_NUMBER_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DATE_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB + SERIAL_NUMBER_DESC_BOB;
        descriptor = new EditEquipmentDescriptorBuilder().withPhone(VALID_PHONE_BOB).withDate(VALID_DATE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSerialNumber(VALID_SERIAL_NUMBER_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
