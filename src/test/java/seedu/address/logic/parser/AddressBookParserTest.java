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

import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.ClearCustomerCommand;
import seedu.address.logic.commands.DeleteCustomerCommand;
import seedu.address.logic.commands.EditCustomerCommand;
import seedu.address.logic.commands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCustomerCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCustomerCommand;
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
        AddCustomerCommand command = (AddCustomerCommand) parser.parseCommand(CustomerUtil.getAddCommand(customer),
            new CustomerManager(), new BookingManager());
        assertEquals(new AddCustomerCommand(customer), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Customer customer = new CustomerBuilder().build();
        AddCustomerCommand commandAlias = (AddCustomerCommand) parser.parseCommand(
                AddCustomerCommand.COMMAND_ALIAS + " " + CustomerUtil.getCustomerDetails(customer),
                new CustomerManager(), new BookingManager());
        assertEquals(new AddCustomerCommand(customer), commandAlias);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof ClearCustomerCommand);
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_WORD + " 3", new CustomerManager(),
            new BookingManager()) instanceof ClearCustomerCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof ClearCustomerCommand);
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_ALIAS + " 3", new CustomerManager(),
            new BookingManager()) instanceof ClearCustomerCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCustomerCommand command = (DeleteCustomerCommand) parser.parseCommand(
            DeleteCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
            new CustomerManager(), new BookingManager());
        assertEquals(new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCustomerCommand commandAlias = (DeleteCustomerCommand) parser.parseCommand(
            DeleteCustomerCommand.COMMAND_ALIAS + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
            new CustomerManager(), new BookingManager());
        assertEquals(new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER), commandAlias);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Customer customer = new CustomerBuilder().build();
        EditCustomerCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(customer).build();
        EditCustomerCommand command = (EditCustomerCommand) parser.parseCommand(EditCustomerCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CUSTOMER.getOneBased() + " " + CustomerUtil.getEditCustomerDescriptorDetails(descriptor),
            new CustomerManager(), new BookingManager());
        assertEquals(new EditCustomerCommand(INDEX_FIRST_CUSTOMER, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Customer customer = new CustomerBuilder().build();
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(customer).build();
        EditCustomerCommand commandAlias = (EditCustomerCommand) parser.parseCommand(
                EditCustomerCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_CUSTOMER.getOneBased() + " " + CustomerUtil.getEditCustomerDescriptorDetails(descriptor),
            new CustomerManager(), new BookingManager());
        assertEquals(new EditCustomerCommand(INDEX_FIRST_CUSTOMER, descriptor), commandAlias);
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
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
            FindNameCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
            new CustomerManager(), new BookingManager());
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindNameCommand commandAlias = (FindNameCommand) parser.parseCommand(
            FindNameCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")),
            new CustomerManager(), new BookingManager());
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), commandAlias);
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
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD, new CustomerManager(), new BookingManager())
            instanceof ListCustomerCommand);
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD + " 3", new CustomerManager(),
            new BookingManager()) instanceof ListCustomerCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_ALIAS, new CustomerManager(), new BookingManager())
            instanceof ListCustomerCommand);
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_ALIAS + " 3", new CustomerManager(),
            new BookingManager()) instanceof ListCustomerCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCustomerCommand command = (SelectCustomerCommand) parser.parseCommand(
            SelectCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(), new CustomerManager(),
            new BookingManager());
        assertEquals(new SelectCustomerCommand(INDEX_FIRST_CUSTOMER), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCustomerCommand commandAlias = (SelectCustomerCommand) parser.parseCommand(
            SelectCustomerCommand.COMMAND_ALIAS + " " + INDEX_FIRST_CUSTOMER.getOneBased(), new CustomerManager(),
            new BookingManager());
        assertEquals(new SelectCustomerCommand(INDEX_FIRST_CUSTOMER), commandAlias);
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
