package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCustomerDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.BookingManager;
import seedu.address.model.CustomerManager;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.CustomerUtil;
import seedu.address.testutil.EditCustomerDescriptorBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Customer customer = new CustomerBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(CustomerUtil.getAddCommand(customer),
            new CustomerManager(), new BookingManager());
        assertEquals(new AddCommand(customer), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Customer customer = new CustomerBuilder().build();
        AddCommand commandAlias = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS + " "
            + CustomerUtil.getCustomerDetails(customer), new CustomerManager(), new BookingManager());
        assertEquals(new AddCommand(customer), commandAlias);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", new CustomerManager(),
            new BookingManager()) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3", new CustomerManager(),
            new BookingManager()) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
            new CustomerManager(), new BookingManager());
        assertEquals(new DeleteCommand(INDEX_FIRST_CUSTOMER), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand commandAlias = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
            new CustomerManager(), new BookingManager());
        assertEquals(new DeleteCommand(INDEX_FIRST_CUSTOMER), commandAlias);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Customer customer = new CustomerBuilder().build();
        EditCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(customer).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CUSTOMER.getOneBased() + " " + CustomerUtil.getEditCustomerDescriptorDetails(descriptor),
            new CustomerManager(), new BookingManager());
        assertEquals(new EditCommand(INDEX_FIRST_CUSTOMER, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Customer customer = new CustomerBuilder().build();
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(customer).build();
        EditCommand commandAlias = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_CUSTOMER.getOneBased() + " " + CustomerUtil.getEditCustomerDescriptorDetails(descriptor),
            new CustomerManager(), new BookingManager());
        assertEquals(new EditCommand(INDEX_FIRST_CUSTOMER, descriptor), commandAlias);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", new CustomerManager(),
            new BookingManager()) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitAlias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3", new CustomerManager(),
            new BookingManager()) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
            new CustomerManager(), new BookingManager());
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand commandAlias = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")),
            new CustomerManager(), new BookingManager());
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), commandAlias);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", new CustomerManager(),
            new BookingManager()) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpAlias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3", new CustomerManager(),
            new BookingManager()) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3", new CustomerManager(),
            new BookingManager()) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", new CustomerManager(), new BookingManager());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3", new CustomerManager(),
            new BookingManager()) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", new CustomerManager(), new BookingManager());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", new CustomerManager(),
            new BookingManager()) instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3", new CustomerManager(),
            new BookingManager()) instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(), new CustomerManager(),
            new BookingManager());
        assertEquals(new SelectCommand(INDEX_FIRST_CUSTOMER), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand commandAlias = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_CUSTOMER.getOneBased(), new CustomerManager(),
            new BookingManager());
        assertEquals(new SelectCommand(INDEX_FIRST_CUSTOMER), commandAlias);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1", new CustomerManager(), new BookingManager())
            instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommandAlias() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1", new CustomerManager(), new BookingManager())
            instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3", new CustomerManager(), new BookingManager())
            instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommandAlias() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3", new CustomerManager(), new BookingManager())
            instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("", new CustomerManager(), new BookingManager());
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", new CustomerManager(), new BookingManager());
    }
}
