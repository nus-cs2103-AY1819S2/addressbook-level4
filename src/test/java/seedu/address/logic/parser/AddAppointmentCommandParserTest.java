package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.appointment.Appointment;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddAppointmentCommand command = new AddAppointmentCommand(new Appointment(1,
                1, "2019-06-01", "9"));
        assertParseSuccess(parser, "add-appt pid/1 did/1 d/2019-06-01 t/9", command);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // all prefixes missing
        assertParseFailure(parser, AddAppointmentCommand.COMMAND_WORD + "",
                expectedMessage);
    }
}
