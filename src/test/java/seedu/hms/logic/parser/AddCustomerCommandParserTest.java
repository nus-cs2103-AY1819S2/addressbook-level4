package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.ID_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_DATE_OF_BIRTH_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.hms.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.hms.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.hms.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.hms.testutil.TypicalCustomers.AMY;
import static seedu.hms.testutil.TypicalCustomers.BOB;

import org.junit.Test;

import seedu.hms.logic.commands.AddCustomerCommand;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.tag.Tag;
import seedu.hms.testutil.CustomerBuilder;

public class AddCustomerCommandParserTest {
    private AddCustomerCommandParser parser = new AddCustomerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Customer expectedCustomer = new CustomerBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND, new AddCustomerCommand(expectedCustomer));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
            new AddCustomerCommand(expectedCustomer));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
            new AddCustomerCommand(expectedCustomer));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND, new AddCustomerCommand(expectedCustomer));

        // multiple ids - last id accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_AMY + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCustomerCommand(expectedCustomer));

        // multiple dobs - last dob accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_AMY
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCustomerCommand(expectedCustomer));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND, new AddCustomerCommand(expectedCustomer));

        // multiple tags - all accepted
        Customer expectedCustomerMultipleTags = new CustomerBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
            .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
            + TAG_DESC_FRIEND, new AddCustomerCommand(expectedCustomerMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Customer expectedCustomer = new CustomerBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ID_DESC_AMY,
            new AddCustomerCommand(expectedCustomer));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB,
            expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + DATE_OF_BIRTH_DESC_BOB
                + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB,
            expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + VALID_EMAIL_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB,
            expectedMessage);

        //missing id prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ID_BOB + ADDRESS_DESC_BOB,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
            VALID_NAME_BOB + VALID_PHONE_BOB + VALID_DATE_OF_BIRTH_BOB + VALID_EMAIL_BOB
                + VALID_ID_BOB + VALID_ADDRESS_BOB,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid date of birth
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_DATE_OF_BIRTH_DESC
            + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DateOfBirth.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + INVALID_EMAIL_DESC + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_BOB + ID_DESC_BOB + INVALID_ADDRESS_DESC
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid id
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_BOB + INVALID_ID_DESC + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, IdentificationNo.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
            INVALID_NAME_DESC + PHONE_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB
                + ID_DESC_BOB + INVALID_ADDRESS_DESC,
            Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
    }
}
