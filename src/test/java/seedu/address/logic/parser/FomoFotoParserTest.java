package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CropCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResizeCommand;
import seedu.address.logic.commands.RotateCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FomoFotoParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FomoFotoParser parser = new FomoFotoParser();

    @Ignore
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }
    /* @author kayheen */
    @Test
    public void parseCommand_rotate() throws Exception {
        assertTrue(parser.parseCommand(RotateCommand.COMMAND_WORD + " 90") instanceof RotateCommand);
        RotateCommand expected = (RotateCommand) parser.parseCommand(RotateCommand.COMMAND_WORD + " 90");
        assertEquals(new RotateCommand(90), expected);
    }

    @Test
    public void parseCommand_crop() throws Exception {
        assertTrue(parser.parseCommand(CropCommand.COMMAND_WORD + " 0 0 200 200") instanceof CropCommand);
        CropCommand expected = (CropCommand) parser.parseCommand(CropCommand.COMMAND_WORD + " 0 0 200 200");
        assertEquals(new CropCommand(0, 0, 200, 200), expected);
    }

    @Test
    public void parseCommand_resize() throws Exception {
        assertTrue(parser.parseCommand(ResizeCommand.COMMAND_WORD + " 200 400") instanceof ResizeCommand);
        ResizeCommand expected = (ResizeCommand) parser.parseCommand(ResizeCommand.COMMAND_WORD + " 200 400");
        assertEquals(new ResizeCommand(200, 400), expected);
    }

    /* @author */
    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
