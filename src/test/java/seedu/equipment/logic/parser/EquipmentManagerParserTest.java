package seedu.equipment.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.equipment.logic.commands.AddCommand;
import seedu.equipment.logic.commands.ClearCommand;
import seedu.equipment.logic.commands.DeleteCommand;
import seedu.equipment.logic.commands.EditCommand;
import seedu.equipment.logic.commands.EditCommand.EditEquipmentDescriptor;
import seedu.equipment.logic.commands.ExitCommand;
import seedu.equipment.logic.commands.FindCommand;
import seedu.equipment.logic.commands.HelpCommand;
import seedu.equipment.logic.commands.HistoryCommand;
import seedu.equipment.logic.commands.ListEquipmentCommand;
import seedu.equipment.logic.commands.ListWorkListCommand;
import seedu.equipment.logic.commands.RedoCommand;
import seedu.equipment.logic.commands.SelectCommand;
import seedu.equipment.logic.commands.SortCommand;
import seedu.equipment.logic.commands.UndoCommand;
import seedu.equipment.logic.parser.EquipmentManagerParser;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.NameContainsKeywordsPredicate;
import seedu.equipment.testutil.EditPersonDescriptorBuilder;
import seedu.equipment.testutil.EquipmentBuilder;
import seedu.equipment.testutil.EquipmentUtil;

public class EquipmentManagerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EquipmentManagerParser parser = new EquipmentManagerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Equipment equipment = new EquipmentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(EquipmentUtil.getAddCommand(equipment));
        assertEquals(new AddCommand(equipment), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Equipment equipment = new EquipmentBuilder().build();
        EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder(equipment).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + EquipmentUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
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
    public void parseCommand_liste() throws Exception {
        assertTrue(parser.parseCommand(ListEquipmentCommand.COMMAND_WORD) instanceof ListEquipmentCommand);
        assertTrue(parser.parseCommand(ListEquipmentCommand.COMMAND_WORD + " 3") instanceof ListEquipmentCommand);
    }

    @Test
    public void parseCommand_listw() throws Exception {
        assertTrue(parser.parseCommand(ListWorkListCommand.COMMAND_WORD) instanceof ListWorkListCommand);
        assertTrue(parser.parseCommand(ListWorkListCommand.COMMAND_WORD + " 3")
                instanceof ListWorkListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
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
