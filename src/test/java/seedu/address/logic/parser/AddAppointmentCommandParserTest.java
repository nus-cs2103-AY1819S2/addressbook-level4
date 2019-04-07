package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.appointment.FutureAppointment;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        FutureAppointment futureAppointment = new FutureAppointment(Integer.parseInt(VALID_PATIENT_ID),
                Integer.parseInt(VALID_DOCTOR_ID),
                LocalDate.parse(VALID_DATE_OF_APPT),
                LocalTime.parse(VALID_START_TIME));
        AddAppointmentCommand command = new AddAppointmentCommand(futureAppointment);
        assertParseSuccess(parser, DESC_VALID_PATIENT_ID + DESC_VALID_DOCTOR_ID
                + DESC_VALID_DATE_OF_APPT + DESC_VALID_START_TIME, command);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // all prefixes missing
        assertParseFailure(parser, AddAppointmentCommand.COMMAND_WORD + "",
                expectedMessage);
    }
}
