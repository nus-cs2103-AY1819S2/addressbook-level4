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
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMedicines.AMOXICILLIN;
import static seedu.address.testutil.TypicalMedicines.GABAPENTIN;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MedicineBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Medicine expectedMedicine = new MedicineBuilder(GABAPENTIN).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMOXICILLIN + NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_AMOXICILLIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple expiry datedates - last expiry dateaccepted
        assertParseSuccess(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_AMOXICILLIN + EXPIRY_DESC_GABAPENTIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple companies - last company accepted
        assertParseSuccess(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + COMPANY_DESC_AMOXICILLIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple tags - all accepted
        Medicine expectedMedicineMultipleTags = new MedicineBuilder(GABAPENTIN).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedMedicineMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Medicine expectedMedicine = new MedicineBuilder(AMOXICILLIN).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN,
                new AddCommand(expectedMedicine));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN,
                expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + VALID_QUANTITY_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN,
                expectedMessage);

        // missing expiry dateprefix
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + VALID_EXPIRY_GABAPENTIN + COMPANY_DESC_GABAPENTIN,
                expectedMessage);

        // missing company prefix
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + VALID_COMPANY_GABAPENTIN,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_GABAPENTIN + VALID_QUANTITY_GABAPENTIN + VALID_EXPIRY_GABAPENTIN + VALID_COMPANY_GABAPENTIN,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + INVALID_QUANTITY_DESC + EXPIRY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Quantity.MESSAGE_CONSTRAINTS);

        // invalid expiry
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + INVALID_EXPIRY_DESC + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Expiry.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + INVALID_COMPANY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Company.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN + INVALID_COMPANY_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + EXPIRY_DESC_GABAPENTIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
