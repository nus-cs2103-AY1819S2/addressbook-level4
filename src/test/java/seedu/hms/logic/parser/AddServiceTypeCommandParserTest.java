package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.hms.logic.commands.AddServiceTypeCommand;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.util.TimeRange;

public class AddServiceTypeCommandParserTest {

    private AddServiceTypeCommandParser parser = new AddServiceTypeCommandParser();

    @Test
    public void parse_validArgs_returnsAddServiceTypeCommand() {
        ServiceType serviceType = new ServiceType(50, new TimeRange(8, 22), "Gym", 7.0);
        assertParseSuccess(parser, " n/GYM cap/50 rate/7.0 :/8-22", new AddServiceTypeCommand(serviceType));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddServiceTypeCommand.MESSAGE_USAGE));
    }
}
