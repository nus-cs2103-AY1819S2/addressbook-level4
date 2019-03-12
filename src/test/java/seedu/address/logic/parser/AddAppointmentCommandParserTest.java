package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.appointment.Appointment;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddAppointmentCommand command = new AddAppointmentCommand(Index.fromOneBased(1),
                new Appointment("appointment1"));
        assertParseSuccess(parser, "1 r/appointment1", command);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // all prefixes missing
        assertParseFailure(parser, "1 appointment1",
                expectedMessage);

        // index missing
        assertParseFailure(parser, "-1 r/appointment1", expectedMessage);
    }
}
