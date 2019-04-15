package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FEVER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PAINKILER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FEVER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;
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
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
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
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC, Company.MESSAGE_CONSTRAINTS); // invalid company name
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Medicine} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FEVER + TAG_DESC_PAINKILER + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FEVER + TAG_EMPTY + TAG_DESC_PAINKILER, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FEVER + TAG_DESC_PAINKILER, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + VALID_COMPANY_AMOXICILLIN + INVALID_TAG_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_tooManyTags_failure() {
        StringBuilder sb = new StringBuilder();
        sb.append("1 ").append(NAME_DESC_GABAPENTIN).append(COMPANY_DESC_GABAPENTIN);
        for (int i = 0; i < Medicine.MAX_SIZE_TAG + 1; i++) {
            sb.append(" ").append(PREFIX_TAG).append(i);
        }
        assertParseFailure(parser, sb.toString(), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Medicine.MESSAGE_CONSTRAINTS_TAGS));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEDICINE;
        String userInput = targetIndex.getOneBased() + TAG_DESC_PAINKILER + COMPANY_DESC_AMOXICILLIN
                + NAME_DESC_AMOXICILLIN + TAG_DESC_FEVER;

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_AMOXICILLIN)
                .withCompany(VALID_COMPANY_AMOXICILLIN).withTags(VALID_TAG_PAINKILLER, VALID_TAG_FEVER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN;

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_AMOXICILLIN)
                .withCompany(VALID_COMPANY_AMOXICILLIN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_MEDICINE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMOXICILLIN;
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_AMOXICILLIN)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // company
        userInput = targetIndex.getOneBased() + COMPANY_DESC_AMOXICILLIN;
        descriptor = new EditMedicineDescriptorBuilder().withCompany(VALID_COMPANY_AMOXICILLIN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FEVER;
        descriptor = new EditMedicineDescriptorBuilder().withTags(VALID_TAG_FEVER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + COMPANY_DESC_AMOXICILLIN + TAG_DESC_FEVER
                + COMPANY_DESC_AMOXICILLIN + TAG_DESC_FEVER + COMPANY_DESC_GABAPENTIN + TAG_DESC_PAINKILER;

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withCompany(VALID_COMPANY_GABAPENTIN)
                .withTags(VALID_TAG_FEVER, VALID_TAG_PAINKILLER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + INVALID_COMPANY_DESC + COMPANY_DESC_GABAPENTIN;
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withCompany(VALID_COMPANY_GABAPENTIN)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TAG_DESC_FEVER + INVALID_COMPANY_DESC + NAME_DESC_GABAPENTIN
                + COMPANY_DESC_GABAPENTIN;
        descriptor = new EditMedicineDescriptorBuilder().withTags(VALID_TAG_FEVER)
                .withCompany(VALID_COMPANY_GABAPENTIN).withName(VALID_NAME_GABAPENTIN).build();
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
