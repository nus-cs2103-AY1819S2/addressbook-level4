package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIALISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_ALVINA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDoctors.ALVINA;

import org.junit.Test;

import seedu.address.logic.commands.doctor.AddDoctorCommand;
import seedu.address.logic.parser.doctor.AddDoctorCommandParser;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.specialisation.Specialisation;

public class AddDoctorCommandParserTest {
    private AddDoctorCommandParser parser = new AddDoctorCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        assertParseSuccess(parser, NAME_DESC_ALVINA + GENDER_DESC_ALVINA + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_ACUPUNCTURE, new AddDoctorCommand(ALVINA));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_ALVINA
                + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_ACUPUNCTURE, Name.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_ALVINA + INVALID_GENDER_DESC
                + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_ACUPUNCTURE, Gender.MESSAGE_CONSTRAINTS);

        // invalid year of specialisation
        assertParseFailure(parser, NAME_DESC_ALVINA + GENDER_DESC_ALVINA
                + INVALID_YEAR_DESC + PHONE_DESC_ALVINA
                + SPECIALISATION_DESC_ACUPUNCTURE, Year.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_ALVINA + GENDER_DESC_ALVINA
                + YEAR_DESC_ALVINA + INVALID_PHONE_DESC
                + SPECIALISATION_DESC_ACUPUNCTURE, Phone.MESSAGE_CONSTRAINTS);

        // invalid specialisation
        assertParseFailure(parser, NAME_DESC_ALVINA + GENDER_DESC_ALVINA
                + YEAR_DESC_ALVINA + PHONE_DESC_ALVINA
                + INVALID_SPECIALISATION_DESC, Specialisation.MESSAGE_CONSTRAINTS);
    }
}
