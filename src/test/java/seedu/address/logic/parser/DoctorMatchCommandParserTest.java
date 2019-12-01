package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIALISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.doctor.DoctorMatchCommand;
import seedu.address.logic.parser.doctor.DoctorMatchCommandParser;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.doctor.DoctorMatch;
import seedu.address.model.person.doctor.DoctorSpecialisationMatchesPredicate;
import seedu.address.model.person.specialisation.Specialisation;

public class DoctorMatchCommandParserTest {

    private DoctorMatchCommandParser parser = new DoctorMatchCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Specialisation spec = new Specialisation("acupuncture");
        AppointmentDate date = new AppointmentDate(VALID_DATE_OF_APPT);
        AppointmentTime time = new AppointmentTime(VALID_START_TIME);
        DoctorMatch dm = new DoctorMatch(spec, date, time);
        DoctorSpecialisationMatchesPredicate pred = new DoctorSpecialisationMatchesPredicate(dm);
        assertParseSuccess(parser,
                SPECIALISATION_DESC_ACUPUNCTURE + DESC_VALID_DATE_OF_APPT + DESC_VALID_START_TIME,
                new DoctorMatchCommand(pred));
    }

    @Test
    public void parse_missingFields_failure() {

        // invalid specialisation
        assertParseFailure(parser,
                INVALID_SPECIALISATION_DESC + DESC_VALID_DATE_OF_APPT + DESC_VALID_START_TIME,
                Specialisation.MESSAGE_CONSTRAINTS);

        // invalid appointment date
        assertParseFailure(parser,
                SPECIALISATION_DESC_ACUPUNCTURE + DESC_INVALID_DATE_OF_APPT + DESC_VALID_START_TIME,
                AppointmentDate.MESSAGE_CONSTRAINTS);

        // invalid appointment time
        assertParseFailure(parser,
                SPECIALISATION_DESC_ACUPUNCTURE + DESC_VALID_DATE_OF_APPT + DESC_INVALID_START_TIME,
                AppointmentTime.MESSAGE_CONSTRAINTS);
    }
}
