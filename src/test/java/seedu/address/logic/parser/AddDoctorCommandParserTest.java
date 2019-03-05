package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.model.person.Name;

public class AddDoctorCommandParserTest {
    private AddDoctorCommandParser parser = new AddDoctorCommandParser();

//    @Test
//    public void parse_allFieldsPresent_success() {
//
//        assertParseSuccess(parser, NAME_DESC_JOHN + GENDER_DESC_JOHN + AGE_DESC_JOHN + PHONE_DESC_JOHN
//                + SPECIALISATION_DESC_ACUPUNCTURE, new AddDoctorCommand());
//
//    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, NAME_DESC_JOHN + GENDER_DESC_JOHN + AGE_DESC_JOHN + PHONE_DESC_JOHN
                + SPECIALISATION_DESC_ACUPUNCTURE, Name.MESSAGE_CONSTRAINTS);
    }
}
