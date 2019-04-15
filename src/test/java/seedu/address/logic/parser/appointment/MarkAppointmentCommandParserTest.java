package seedu.address.logic.parser.appointment;

import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_MARK_APPT_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INVALID_MARK_APPT_STATUS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_MARK_APPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MARK_APPT_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.appointment.MarkAppointmentCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentStatus;

public class MarkAppointmentCommandParserTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private MarkAppointmentCommandParser parser = new MarkAppointmentCommandParser();

    @Test
    public void parse_baseCommand_success() throws ParseException {
        MarkAppointmentCommand.ChangedAppointmentDescriptor descriptor =
                new MarkAppointmentCommand.ChangedAppointmentDescriptor();
        descriptor.setStatus(AppointmentStatus.valueOf(VALID_STATUS));
        MarkAppointmentCommand command =
                new MarkAppointmentCommand(ParserUtil.parseIndex(VALID_MARK_APPT_INDEX), descriptor);

        assertParseSuccess(parser, DESC_VALID_MARK_APPT, command);
    }

    @Test
    public void parse_invalidIndex_failure() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(DESC_INVALID_MARK_APPT_INDEX);
        assertParseFailure(parser, DESC_INVALID_MARK_APPT_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidStatus_failure() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(DESC_INVALID_MARK_APPT_STATUS);
        assertParseFailure(parser, DESC_INVALID_MARK_APPT_STATUS, AppointmentStatus.MESSAGE_CONSTRAINTS);
    }
}
