package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_CHRONOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_STATUS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_CHRONOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_STATUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHRONOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.appointment.ListAppointmentCommand;
import seedu.address.logic.parser.appointment.ListAppointmentCommandParser;
import seedu.address.model.appointment.AppointmentChronology;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentTime;

public class ListAppointmentCommandParserTest {
    private ListAppointmentCommandParser parser = new ListAppointmentCommandParser();

    @Test
    public void parse_baseCommand_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();
        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, "", command);
    }

    @Test
    public void parse_patientIdFilter_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();
        descriptor.setPatientId(Optional.of(new AppointmentPatientId(VALID_PATIENT_ID)));
        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, DESC_VALID_PATIENT_ID, command);
    }

    @Test
    public void parse_patientIdFilter_failure() {
        String expectedMessage = String.format(AppointmentPatientId.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, DESC_INVALID_PATIENT_ID, expectedMessage);
    }

    @Test
    public void parse_doctorIdFilter_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();
        descriptor.setDoctorId(Optional.of(new AppointmentDoctorId(VALID_DOCTOR_ID)));
        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, DESC_VALID_DOCTOR_ID, command);
    }

    @Test
    public void parse_doctorIdFilter_failure() {
        String expectedMessage = String.format(AppointmentDoctorId.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, DESC_INVALID_DOCTOR_ID, expectedMessage);
    }

    @Test
    public void parse_dateFilter_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();
        descriptor.setDate(Optional.of(new AppointmentDate(VALID_DATE_OF_APPT)));
        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, DESC_VALID_DATE_OF_APPT, command);
    }

    @Test
    public void parse_dateFilter_failure() {
        String expectedMessage = String.format(AppointmentDate.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, DESC_INVALID_DATE_OF_APPT, expectedMessage);
    }

    @Test
    public void parse_timeFilter_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();
        descriptor.setTime(Optional.of(new AppointmentTime(VALID_START_TIME)));
        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, DESC_VALID_START_TIME, command);
    }

    @Test
    public void parse_timeFilter_failure() {
        String expectedMessage = String.format(AppointmentTime.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, DESC_INVALID_START_TIME, expectedMessage);
    }

    @Test
    public void parse_statusFilter_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();
        descriptor.setStatus(Optional.of(AppointmentStatus.valueOf(VALID_STATUS)));
        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, DESC_VALID_STATUS, command);
    }

    @Test
    public void parse_statusFilter_failure() {
        String expectedMessage = String.format(AppointmentStatus.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, DESC_INVALID_STATUS, expectedMessage);
    }

    @Test
    public void parse_chronologyFilter_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();
        descriptor.setChronology(Optional.of(AppointmentChronology.valueOf(VALID_CHRONOLOGY)));
        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, DESC_VALID_CHRONOLOGY, command);
    }

    @Test
    public void parse_chronologyFilter_failure() {
        String expectedMessage = String.format(AppointmentChronology.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, DESC_INVALID_CHRONOLOGY, expectedMessage);
    }

    @Test
    public void parse_allFilter_success() {
        ListAppointmentCommand.ListAppointmentDescriptor descriptor =
                new ListAppointmentCommand.ListAppointmentDescriptor();

        descriptor.setPatientId(Optional.of(new AppointmentPatientId(VALID_PATIENT_ID)));
        descriptor.setDoctorId(Optional.of(new AppointmentDoctorId(VALID_DOCTOR_ID)));
        descriptor.setDate(Optional.of(new AppointmentDate(VALID_DATE_OF_APPT)));
        descriptor.setTime(Optional.of(new AppointmentTime(VALID_START_TIME)));
        descriptor.setStatus(Optional.of(AppointmentStatus.valueOf(VALID_STATUS)));
        descriptor.setChronology(Optional.of(AppointmentChronology.valueOf(VALID_CHRONOLOGY)));

        ListAppointmentCommand command = new ListAppointmentCommand(descriptor);

        assertParseSuccess(parser, DESC_VALID_PATIENT_ID + DESC_VALID_DOCTOR_ID
                + DESC_VALID_DATE_OF_APPT + DESC_VALID_START_TIME + DESC_VALID_STATUS
                + DESC_VALID_CHRONOLOGY, command);
    }
}
