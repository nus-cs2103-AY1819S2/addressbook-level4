package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMedicines.AMY;
import static seedu.address.testutil.TypicalMedicines.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Email;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MedicineBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Medicine expectedMedicine = new MedicineBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, NAME_DESC_BOB + QUANTITY_DESC_AMY + QUANTITY_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple companies - last company accepted
        assertParseSuccess(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_AMY
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMedicine));

        // multiple tags - all accepted
        Medicine expectedMedicineMultipleTags = new MedicineBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedMedicineMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Medicine expectedMedicine = new MedicineBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + QUANTITY_DESC_AMY + EMAIL_DESC_AMY + COMPANY_DESC_AMY,
                new AddCommand(expectedMedicine));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_QUANTITY_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + VALID_EMAIL_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing company prefix
        assertParseFailure(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + VALID_COMPANY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_QUANTITY_BOB + VALID_EMAIL_BOB + VALID_COMPANY_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_QUANTITY_DESC + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Quantity.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + INVALID_EMAIL_DESC + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + INVALID_COMPANY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Company.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + QUANTITY_DESC_BOB + EMAIL_DESC_BOB + INVALID_COMPANY_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + QUANTITY_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
