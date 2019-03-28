package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.SKILLS_DESC_ANDY;
import static seedu.address.logic.parser.CommandMode.MODE_HEALTHWORKER;
import static seedu.address.logic.parser.CommandMode.MODE_REQUEST;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddHealthWorkerCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteHealthWorkerCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditHealthWorkerCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListHealthWorkerCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.request.AssignRequestCommand;
import seedu.address.logic.commands.request.ClearRequestCommand;
import seedu.address.logic.commands.request.DeleteRequestCommand;
import seedu.address.logic.commands.request.FilterRequestCommand;
import seedu.address.logic.commands.request.ListRequestCommand;
import seedu.address.logic.commands.request.SelectRequestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.testutil.EditHealthWorkerDescriptorBuilder;
import seedu.address.testutil.HealthWorkerBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        HealthWorker healthWorker = new HealthWorkerBuilder(ANDY).build();
        AddHealthWorkerCommand addHealthWorkerCommand = (AddHealthWorkerCommand) parser.parseCommand(
                AddCommand.COMMAND_WORD + " " + MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                        + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY);
        assertEquals(new AddHealthWorkerCommand(healthWorker), addHealthWorkerCommand);
    }

    @Test
    public void parseCommand_clearRequest() throws Exception {
        assertTrue(parser.parseCommand(ClearRequestCommand.COMMAND_WORD) instanceof ClearRequestCommand);
    }

    @Test
    public void parseCommand_assignRequest() throws Exception {
        assertTrue(parser.parseCommand(AssignRequestCommand.COMMAND_WORD + " hw/1 r/1")
            instanceof AssignRequestCommand);
    }

    @Test
    public void parseCommand_findRequest() throws Exception {
        assertTrue(parser.parseCommand(FilterCommand.COMMAND_WORD + " " + MODE_REQUEST + " n/alice")
            instanceof FilterRequestCommand);
    }

    @Test
    public void parseCommand_selectRequest() throws Exception {
        assertTrue(parser.parseCommand(SelectRequestCommand.COMMAND_WORD + " 1")
            instanceof SelectRequestCommand);
    }

    @Test
    public void parseCommand_deleteRequest() throws Exception {
        assertTrue(parser.parseCommand(DeleteCommand.COMMAND_WORD + " " + MODE_REQUEST + " 3")
            instanceof DeleteRequestCommand);
    }

    @Test
    public void parseCommand_listRequest() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + MODE_REQUEST) instanceof ListRequestCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteHealthWorkerCommand command = (DeleteHealthWorkerCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + MODE_HEALTHWORKER + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteHealthWorkerCommand(INDEX_FIRST), command);

        DeleteRequestCommand secondCommand = (DeleteRequestCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + MODE_REQUEST + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteRequestCommand(INDEX_FIRST), secondCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        HealthWorker healthWorker = new HealthWorkerBuilder(ANDY).build();
        EditHealthWorkerCommand.EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder(
                healthWorker).build();
        EditHealthWorkerCommand editHealthWorkerCommand = (EditHealthWorkerCommand) parser.parseCommand(
                EditCommand.COMMAND_WORD + " " + MODE_HEALTHWORKER + " " + INDEX_FIRST.getOneBased()
                        + NAME_DESC_ANDY + PHONE_DESC_ANDY + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY
                        + SKILLS_DESC_ANDY);
        assertEquals(new EditHealthWorkerCommand(INDEX_FIRST, descriptor), editHealthWorkerCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        // TODO: To replace with filter tests
        // List<String> keywords = Arrays.asList("foo", "bar", "baz");
        // FilterHealthWorkerCommand command = (FilterHealthWorkerCommand) parser.parseCommand(
        //        FilterHealthWorkerCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        // assertEquals(new FilterHealthWorkerCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " healthworker")
                instanceof ListHealthWorkerCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " h")
                instanceof ListHealthWorkerCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST), command);
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

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
