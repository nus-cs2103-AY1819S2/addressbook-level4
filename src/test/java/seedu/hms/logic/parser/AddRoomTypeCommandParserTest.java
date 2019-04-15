package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hms.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.hms.logic.commands.AddRoomTypeCommand;
import seedu.hms.model.reservation.roomType.RoomType;

public class AddRoomTypeCommandParserTest {

    private AddRoomTypeCommandParser parser = new AddRoomTypeCommandParser();

    @Test
    public void parse_validArgs_returnsAddRoomTypeCommand() {
        RoomType roomType = new RoomType(50, "DOUBLE ROOM", 700.0);
        assertParseSuccess(parser, " n/DOUBLE ROOM cap/50 rate/700.0", new AddRoomTypeCommand(roomType));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddRoomTypeCommand.MESSAGE_USAGE));
    }
}
