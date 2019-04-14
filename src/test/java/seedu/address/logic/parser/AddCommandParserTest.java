package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FEVER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PAINKILER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FEVER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMedicines.GABAPENTIN;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MedicineBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Medicine expectedMedicine = new MedicineBuilder(GABAPENTIN).withTags(VALID_TAG_FEVER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_FEVER, new AddCommand(expectedMedicine));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMOXICILLIN + NAME_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_FEVER, new AddCommand(expectedMedicine));


        // multiple companies - last company accepted
        assertParseSuccess(parser, NAME_DESC_GABAPENTIN + COMPANY_DESC_AMOXICILLIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_FEVER, new AddCommand(expectedMedicine));

        // multiple tags - all accepted
        Medicine expectedMedicineMultipleTags = new MedicineBuilder(GABAPENTIN)
                .withTags(VALID_TAG_FEVER, VALID_TAG_PAINKILLER).build();
        assertParseSuccess(parser, NAME_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN + TAG_DESC_PAINKILER + TAG_DESC_FEVER,
                new AddCommand(expectedMedicineMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Medicine expectedMedicine = new MedicineBuilder(GABAPENTIN).withTags().build();
        assertParseSuccess(parser, NAME_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN,
                new AddCommand(expectedMedicine));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_GABAPENTIN + COMPANY_DESC_GABAPENTIN, expectedMessage);

        // missing company prefix
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN
                + VALID_COMPANY_GABAPENTIN, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_GABAPENTIN + VALID_COMPANY_GABAPENTIN, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + COMPANY_DESC_GABAPENTIN + TAG_DESC_PAINKILER
                + TAG_DESC_FEVER, Name.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + INVALID_COMPANY_DESC + TAG_DESC_PAINKILER
                + TAG_DESC_FEVER, Company.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN + INVALID_TAG_DESC
                + VALID_TAG_FEVER, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_COMPANY_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_PAINKILER + TAG_DESC_FEVER, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyTags_failure() {
        StringBuilder sb = new StringBuilder();
        sb.append(NAME_DESC_GABAPENTIN).append(COMPANY_DESC_GABAPENTIN);
        for (int i = 0; i < Medicine.MAX_SIZE_TAG + 1; i++) {
            sb.append(" ").append(PREFIX_TAG).append(i);
        }
        assertParseFailure(parser, sb.toString(), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Medicine.MESSAGE_CONSTRAINTS_TAGS));
    }
}
