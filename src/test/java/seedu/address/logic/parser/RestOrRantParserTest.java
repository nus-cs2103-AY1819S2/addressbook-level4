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
import seedu.address.logic.commands.AddTableCommand;
import seedu.address.logic.commands.AddToMenuCommand;
import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearOrderCommand;
import seedu.address.logic.commands.ClearTablesCommand;
import seedu.address.logic.commands.DeleteFromMenuCommand;
import seedu.address.logic.commands.DeleteFromOrderCommand;
import seedu.address.logic.commands.EditPaxCommand;
import seedu.address.logic.commands.EditSeatsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RestaurantModeCommand;
import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.commands.SpaceForCommand;
import seedu.address.logic.commands.TableModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.MenuItemBuilder;
import seedu.address.testutil.OrderItemBuilder;
import seedu.address.testutil.RestOrRantUtil;
import seedu.address.testutil.TableBuilder;

public class RestOrRantParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RestOrRantParser parser = new RestOrRantParser();

    @Test
    public void parseCommand_addTable() throws Exception {
        Table table = new TableBuilder().build();
        AddTableCommand command = (AddTableCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
                AddTableCommand.COMMAND_WORD + " " + RestOrRantUtil.getAddTableDetails(table));
        List<TableStatus> tableStatuses = new ArrayList<>();
        tableStatuses.add(table.getTableStatus());
        assertEquals(new AddTableCommand(tableStatuses), command);
        command = (AddTableCommand) parser.parseCommand(Mode.RESTAURANT_MODE, "add" + " "
                + RestOrRantUtil.getAddTableDetails(table));
        assertEquals(new AddTableCommand(tableStatuses), command);
        try {
            parser.parseCommand(Mode.TABLE_MODE,
                    AddTableCommand.COMMAND_WORD + " " + RestOrRantUtil.getAddTableDetails(table));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE,
                    AddTableCommand.COMMAND_WORD + " " + RestOrRantUtil.getAddTableDetails(table));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_editPax() throws Exception {
        Table table = new TableBuilder().build();
        EditPaxCommand command = (EditPaxCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
                EditPaxCommand.COMMAND_WORD + " " + table.getTableNumber().toString() + " 4");
        assertEquals(new EditPaxCommand(new String[]{"1", "4"}), command);
        try {
            parser.parseCommand(Mode.TABLE_MODE,
                    EditPaxCommand.COMMAND_WORD + " " + table.getTableNumber().toString() + " 4");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE,
                    EditPaxCommand.COMMAND_WORD + " " + table.getTableNumber().toString() + " 4");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_editSeats() throws Exception {
        Table table = new TableBuilder().build();
        EditSeatsCommand command = (EditSeatsCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
                EditSeatsCommand.COMMAND_WORD + " " + table.getTableNumber().toString() + " 8");
        assertEquals(new EditSeatsCommand(table.getTableNumber(), "8"), command);
        try {
            parser.parseCommand(Mode.TABLE_MODE,
                    EditSeatsCommand.COMMAND_WORD + " " + table.getTableNumber().toString() + " 8");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE,
                    EditSeatsCommand.COMMAND_WORD + " " + table.getTableNumber().toString() + " 8");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_spaceFor() throws Exception {
        SpaceForCommand command = (SpaceForCommand) parser.parseCommand(Mode.RESTAURANT_MODE,
                SpaceForCommand.COMMAND_WORD + " 3");
        assertEquals(new SpaceForCommand(3), command);
        try {
            parser.parseCommand(Mode.TABLE_MODE,
                    SpaceForCommand.COMMAND_WORD + " 3");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE,
                    SpaceForCommand.COMMAND_WORD + " 3");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_clearTable() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                ClearTablesCommand.COMMAND_WORD) instanceof ClearTablesCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, "clear") instanceof ClearTablesCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                ClearTablesCommand.COMMAND_WORD + " 3") instanceof ClearTablesCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, "clear" + " 3") instanceof ClearTablesCommand);
        try {
            parser.parseCommand(Mode.TABLE_MODE, ClearTablesCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE, ClearTablesCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_addToOrder() throws Exception {
        OrderItem orderItem = new OrderItemBuilder().build();
        AddToOrderCommand command = (AddToOrderCommand) parser.parseCommand(Mode.TABLE_MODE,
                AddToOrderCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
        List<Code> codes = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        codes.add(orderItem.getMenuItemCode());
        quantities.add(orderItem.getQuantityOrdered());
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
        try {
            parser.parseCommand(Mode.BILL_MODE,
                    AddToOrderCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.STATISTICS_MODE,
                    AddToOrderCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_clearOrder() throws Exception {
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, ClearOrderCommand.COMMAND_WORD) instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, "clear") instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                ClearOrderCommand.COMMAND_WORD + " 3") instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE,
                "clear" + " 3") instanceof ClearOrderCommand);
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
        try {
            parser.parseCommand(Mode.BILL_MODE, ClearOrderCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.STATISTICS_MODE, ClearOrderCommand.COMMAND_WORD);
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
        try {
            parser.parseCommand(Mode.BILL_MODE, DeleteFromOrderCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.STATISTICS_MODE, DeleteFromOrderCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_serve() throws Exception {
        OrderItem orderItem = new OrderItemBuilder().build();
        ServeCommand command = (ServeCommand) parser.parseCommand(Mode.TABLE_MODE,
                ServeCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
        assertEquals(new ServeCommand(orderItem.getMenuItemCode(), orderItem.getQuantityOrdered()), command);

        try {
            parser.parseCommand(Mode.RESTAURANT_MODE,
                    ServeCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.MENU_MODE,
                    ServeCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.BILL_MODE,
                    ServeCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.STATISTICS_MODE,
                    ServeCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(orderItem));
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
    public void parseCommand_deleteFromMenu() throws Exception {
        DeleteFromMenuCommand command = (DeleteFromMenuCommand) parser.parseCommand(Mode.MENU_MODE,
                DeleteFromMenuCommand.COMMAND_WORD + " W09");
        assertEquals(new DeleteFromMenuCommand(new Code("W09")), command);

        try {
            parser.parseCommand(Mode.RESTAURANT_MODE, DeleteFromMenuCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
        try {
            parser.parseCommand(Mode.TABLE_MODE, DeleteFromMenuCommand.COMMAND_WORD);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_MODE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.BILL_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Mode.STATISTICS_MODE, ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(Mode.BILL_MODE, ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(Mode.STATISTICS_MODE, ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.TABLE_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.MENU_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.BILL_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Mode.STATISTICS_MODE, HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HistoryCommand.COMMAND_WORD)
                instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE,
                HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
        assertTrue(parser.parseCommand(Mode.RESTAURANT_MODE, HistoryCommand.COMMAND_ALIAS)
                instanceof HistoryCommand);
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
        assertTrue(parser.parseCommand(Mode.BILL_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.BILL_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.STATISTICS_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.STATISTICS_MODE,
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
        command = (TableModeCommand) parser.parseCommand(Mode.BILL_MODE, TableModeCommand.COMMAND_WORD + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.BILL_MODE, TableModeCommand.COMMAND_ALIAS + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.STATISTICS_MODE, TableModeCommand.COMMAND_WORD + " 3");
        assertEquals(new TableModeCommand(new TableNumber("3")), command);
        command = (TableModeCommand) parser.parseCommand(Mode.STATISTICS_MODE, TableModeCommand.COMMAND_ALIAS + " 3");
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
        assertTrue(parser.parseCommand(Mode.BILL_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.BILL_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.STATISTICS_MODE,
                RestaurantModeCommand.COMMAND_WORD) instanceof RestaurantModeCommand);
        assertTrue(parser.parseCommand(Mode.STATISTICS_MODE,
                RestaurantModeCommand.COMMAND_ALIAS) instanceof RestaurantModeCommand);
    }

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
