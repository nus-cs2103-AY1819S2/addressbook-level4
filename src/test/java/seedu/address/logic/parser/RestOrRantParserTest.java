package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.Mode;
import seedu.address.logic.commands.AddToMenuCommand;
import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.logic.commands.ClearOrderCommand;
import seedu.address.logic.commands.DeleteFromOrderCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RestaurantModeCommand;
import seedu.address.logic.commands.TableModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.TableNumber;
import seedu.address.testutil.MenuItemBuilder;
import seedu.address.testutil.OrderItemBuilder;
import seedu.address.testutil.RestOrRantUtil;

public class RestOrRantParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RestOrRantParser parser = new RestOrRantParser();

    //    @Test
    //    public void parseCommand_add() throws Exception {
    //        Person person = new PersonBuilder().build();
    //        AddCommand command = (AddCommand) parser.parseCommand(Mode.RESTAURANT_MODE, RestOrRantUtil
    //                .getAddCommand(person));
    //        assertEquals(new AddCommand(person), command);
    //        command = (AddCommand) parser.parseCommand(Mode.RESTAURANT_MODE, RestOrRantUtil.getAddAlias(person));
    //        assertEquals(new AddCommand(person), command);
    //    }

    //    @Test TODO
    //    public void parseCommand_addTable() throws Exception {
    //        Table table = new TableBuilder().build();
    //        AddTableCommand command = (AddTableCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
    //                AddTableCommand.COMMAND_WORD + " " + RestOrRantUtil.getTableDetails(table));
    //        List<TableStatus> tableStatuses = new ArrayList<>();
    //        tableStatuses.add(table.getTableStatus());
    //        assertEquals(new AddTableCommand(tableStatuses), command);
    //        try {
    //            parser.parseCommand(Mode.TABLE_MODE,
    //                    AddTableCommand.COMMAND_WORD + " " + RestOrRantUtil.getTableDetails(table));
    //            throw new AssertionError("The expected ParseException was not thrown.");
    //        } catch (ParseException pe) {
    //            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
    //        }
    //        try {
    //            parser.parseCommand(Mode.MENU_MODE,
    //                    AddTableCommand.COMMAND_WORD + " " + RestOrRantUtil.getTableDetails(table));
    //            throw new AssertionError("The expected ParseException was not thrown.");
    //        } catch (ParseException pe) {
    //            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
    //        }
    //    }

    @Test
    public void parseCommand_addToOrder() throws Exception {
        OrderItem orderItem = new OrderItemBuilder().build();
        AddToOrderCommand command = (AddToOrderCommand) parser.parseCommand(Mode.TABLE_MODE,
                AddToOrderCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
        List<Code> codes = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        codes.add(orderItem.getMenuItemCode());
        quantities.add(orderItem.getQuantity());
        assertEquals(new AddToOrderCommand(codes, quantities), command);
        try {
            parser.parseCommand(Mode.RESTAURANT_MODE,
                    AddToOrderCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE,
                    AddToOrderCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_addMenuItem() throws Exception {
        MenuItem menuItem = new MenuItemBuilder().build();
        AddToMenuCommand command = (AddToMenuCommand) parser.parseCommand(Mode.MENU_MODE,
                AddToMenuCommand.COMMAND_WORD + " " + RestOrRantUtil.getMenuItemDetails(menuItem));
        assertEquals(new AddToMenuCommand(menuItem), command);
        try {
            parser.parseCommand(Mode.TABLE_MODE,
                    AddToMenuCommand.COMMAND_WORD + " " + RestOrRantUtil.getMenuItemDetails(menuItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.RESTAURANT_MODE,
                    AddToMenuCommand.COMMAND_WORD + " " + RestOrRantUtil.getMenuItemDetails(menuItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_clearOrder() throws Exception {
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, ClearOrderCommand.COMMAND_WORD) instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, ClearOrderCommand.COMMAND_ALIAS) instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                ClearOrderCommand.COMMAND_WORD + " 3") instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                ClearOrderCommand.COMMAND_ALIAS + " 3") instanceof ClearOrderCommand);
        try {
            parser.parseCommand(Mode.RESTAURANT_MODE, ClearOrderCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE, ClearOrderCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_deleteFromOrder() throws Exception {
        DeleteFromOrderCommand command = (DeleteFromOrderCommand) parser.parseCommand(Mode.TABLE_MODE,
                DeleteFromOrderCommand.COMMAND_WORD + " W09");
        assertEquals(new DeleteFromOrderCommand(new Code("W09")), command);

        try {
            parser.parseCommand(Mode.RESTAURANT_MODE, DeleteFromOrderCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE, DeleteFromOrderCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    // TODO: keep for future use
    //    @Test
    //    public void parseCommand_delete() throws Exception {
    //        DeleteCommand command = (DeleteCommand) parser.parseCommand(
    //                Mode.RESTAURANT_MODE, DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
    //        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    //        command = (DeleteCommand) parser.parseCommand(
    //                Mode.RESTAURANT_MODE, DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
    //        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    //    }
    //
    //    @Test
    //    public void parseCommand_edit() throws Exception {
    //        Person person = new PersonBuilder().build();
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
    //        EditCommand command = (EditCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
    //                EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
    //                + RestOrRantUtil.getEditPersonDescriptorDetails(descriptor));
    //        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    //        command = (EditCommand) parser.parseCommand(Mode.RESTAURANT_MODE, EditCommand.COMMAND_ALIAS + " "
    //                + INDEX_FIRST_PERSON.getOneBased() + " "
    //                + RestOrRantUtil.getEditPersonDescriptorDetails(descriptor));
    //        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    //    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
    }

    //    @Test
    //    public void parseCommand_find() throws Exception {
    //        List<String> keywords = Arrays.asList("foo", "bar", "baz");
    //        FindCommand command = (FindCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
    //                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
    //        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    //        command = (FindCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
    //                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
    //        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    //    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);

        try {
            parser.parseCommand(Mode.RESTAURANT_MODE, "histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }

        try {
            parser.parseCommand(Mode.RESTAURANT_MODE, "hists");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_restaurantMode() throws Exception {
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                RestaurantModeCommand.COMMAND_WORD + " 3") instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                RestaurantModeCommand.COMMAND_ALIAS + " 3") instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
    }

    @Test
    public void parseCommand_tableMode() throws Exception {
        TableModeCommand command =
                (TableModeCommand) parser.parseCommand(Mode.RESTAURANT_MODE, TableModeCommand.COMMAND_WORD + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.RESTAURANT_MODE, TableModeCommand.COMMAND_ALIAS + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.TABLE_MODE, TableModeCommand.COMMAND_WORD + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.TABLE_MODE, TableModeCommand.COMMAND_ALIAS + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.MENU_MODE, TableModeCommand.COMMAND_WORD + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.MENU_MODE, TableModeCommand.COMMAND_ALIAS + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
    }

    @Test
    public void parseCommand_menuMode() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                RestaurantModeCommand.COMMAND_WORD + " 3") instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                RestaurantModeCommand.COMMAND_ALIAS + " 3") instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
    }

    //    @Test
    //    public void parseCommand_list() throws Exception {
    //        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ListCommand.COMMAND_WORD) instanceof ListCommand);
    //        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
    //                ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    //        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ListCommand.COMMAND_ALIAS) instanceof ListCommand);
    //        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
    //                ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    //    }

    //    @Test
    //    public void parseCommand_select() throws Exception {
    //        SelectCommand command = (SelectCommand) parser.parseCommand(
    //                Mode.RESTAURANT_MODE, SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
    //        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    //        command = (SelectCommand) parser.parseCommand(
    //                Mode.RESTAURANT_MODE, SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
    //        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    //    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand(Mode.RESTAURANT_MODE, "");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand(Mode.RESTAURANT_MODE, "unknownCommand");
    }
}
