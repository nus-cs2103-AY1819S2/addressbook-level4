package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.DeleteLessonCommand;
import seedu.address.logic.commands.management.ExitCommand;
import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.logic.commands.management.HistoryCommand;
import seedu.address.logic.commands.management.ListLessonsCommand;
import seedu.address.logic.commands.management.OpenLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ManagementModeParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ManagementModeParser parser = new ManagementModeParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parse(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parse(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parse(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parse(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parse("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_addLesson() throws Exception {
        String command = AddLessonCommand.COMMAND_WORD + " "
                + Syntax.PREFIX_LESSON_NAME + "Capitals of the world "
                + Syntax.PREFIX_LESSON_CORE_HEADER + "Country "
                + Syntax.PREFIX_LESSON_CORE_HEADER + "Capital "
                + Syntax.PREFIX_LESSON_OPT_HEADER + "Hint";
        assertTrue(parser.parse(command) instanceof AddLessonCommand);
    }

    @Test
    public void parseCommand_deleteLesson() throws Exception {
        String command = DeleteLessonCommand.COMMAND_WORD + " 1";
        assertTrue(parser.parse(command) instanceof DeleteLessonCommand);
    }

    @Test
    public void parseCommand_listLessons() throws Exception {
        assertTrue(parser.parse(ListLessonsCommand.COMMAND_WORD)
                instanceof ListLessonsCommand);
    }

    @Test
    public void parseCommand_openLessons() throws Exception {
        String command = OpenLessonCommand.COMMAND_WORD + " 2";
        assertTrue(parser.parse(command) instanceof OpenLessonCommand);
    }


    //    @Test
    //    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
    //        assertTrue(parser.parse(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
    //        assertTrue(parser.parse("redo 1") instanceof RedoCommand);
    //    }
    //
    //    @Test
    //    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
    //        assertTrue(parser.parse(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
    //        assertTrue(parser.parse("undo 3") instanceof UndoCommand);
    //    }
    /*@Test
    public void parseCommand_start() throws Exception {
        assertTrue(parser.parse(QuizStartCommand.COMMAND_WORD) instanceof QuizStartCommand);
    }*/

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parse("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parse("unknownCommand");
    }
}
