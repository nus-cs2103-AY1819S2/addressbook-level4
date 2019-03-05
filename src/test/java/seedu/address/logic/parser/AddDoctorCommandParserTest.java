package seedu.address.logic.parser;

import seedu.address.logic.commands.AddDoctorCommand;

import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_ACUPUNCTURE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

public class AddDoctorCommandParserTest {
    private AddDoctorCommandParser parser = new AddDoctorCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        assertParseSuccess(parser, NAME_DESC_JOHN + GENDER_DESC_JOHN + AGE_DESC_JOHN + PHONE_DESC_JOHN
                + SPECIALISATION_DESC_ACUPUNCTURE, new AddDoctorCommand());

    }
}
