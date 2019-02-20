package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPIRY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MEDICINE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditMedicineDescriptor;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditMedicineDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMOXICILLIN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMOXICILLIN, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMOXICILLIN, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_EXPIRY_DESC, Expiry.MESSAGE_CONSTRAINTS); // invalid expiry
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC, Company.MESSAGE_CONSTRAINTS); // invalid company name
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid quantity followed by valid expiry
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC + EXPIRY_DESC_AMOXICILLIN, Quantity.MESSAGE_CONSTRAINTS);

        // valid quantity followed by invalid quantity. The test case for invalid quantity followed by valid quantity
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + QUANTITY_DESC_GABAPENTIN + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Medicine} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EXPIRY_DESC + VALID_COMPANY_AMOXICILLIN + VALID_QUANTITY_AMOXICILLIN,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEDICINE;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_GABAPENTIN + TAG_DESC_HUSBAND
                + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN + NAME_DESC_AMOXICILLIN + TAG_DESC_FRIEND;

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_AMOXICILLIN)
                .withQuantity(VALID_QUANTITY_GABAPENTIN).withExpiry(VALID_EXPIRY_AMOXICILLIN).withCompany(VALID_COMPANY_AMOXICILLIN)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_AMOXICILLIN;

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withQuantity(VALID_QUANTITY_GABAPENTIN)
                .withExpiry(VALID_EXPIRY_AMOXICILLIN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_MEDICINE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMOXICILLIN;
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_AMOXICILLIN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_AMOXICILLIN;
        descriptor = new EditMedicineDescriptorBuilder().withQuantity(VALID_QUANTITY_AMOXICILLIN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // expiry
        userInput = targetIndex.getOneBased() + EXPIRY_DESC_AMOXICILLIN;
        descriptor = new EditMedicineDescriptorBuilder().withExpiry(VALID_EXPIRY_AMOXICILLIN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // company
        userInput = targetIndex.getOneBased() + COMPANY_DESC_AMOXICILLIN;
        descriptor = new EditMedicineDescriptorBuilder().withCompany(VALID_COMPANY_AMOXICILLIN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditMedicineDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN
                + TAG_DESC_FRIEND + QUANTITY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + TAG_DESC_FRIEND
                + QUANTITY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + TAG_DESC_HUSBAND;

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withQuantity(VALID_QUANTITY_GABAPENTIN)
                .withExpiry(VALID_EXPIRY_GABAPENTIN).withCompany(VALID_COMPANY_GABAPENTIN).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + INVALID_QUANTITY_DESC + QUANTITY_DESC_GABAPENTIN;
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withQuantity(VALID_QUANTITY_GABAPENTIN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EXPIRY_DESC_GABAPENTIN + INVALID_QUANTITY_DESC + COMPANY_DESC_GABAPENTIN
                + QUANTITY_DESC_GABAPENTIN;
        descriptor = new EditMedicineDescriptorBuilder().withQuantity(VALID_QUANTITY_GABAPENTIN).withExpiry(VALID_EXPIRY_GABAPENTIN)
                .withCompany(VALID_COMPANY_GABAPENTIN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_MEDICINE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
