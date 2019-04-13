package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.RestOrRant;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;

import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;

import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_CHICKEN = "Chicken Wings";
    public static final String VALID_NAME_FRIES = "French Fries";
    public static final String VALID_NAME_TEA = "Honey Milk Tea";
    public static final String VALID_CODE_CHICKEN = "W09";
    public static final String VALID_CODE_FRIES = "W12";
    public static final String VALID_CODE_TEA = "D01";
    public static final String VALID_PRICE_CHICKEN = "4.50";
    public static final String VALID_PRICE_FRIES = "2.20";
    public static final String VALID_PRICE_TEA = "4.50";
    public static final String VALID_TABLE_NUMBER_1 = "1";
    public static final String VALID_TABLE_NUMBER_2 = "2";
    public static final String VALID_TABLE_STATUS_1 = "0/4";
    public static final String VALID_TABLE_STATUS_2 = "3/5";
    public static final String VALID_QUANTITY_3 = "3";
    public static final String VALID_QUANTITY_2 = "2";
    public static final String VALID_QUANTITY_8 = "8";
    public static final String VALID_DAY_1 = "01";
    public static final String VALID_DAY_29 = "29";
    public static final String VALID_MONTH_3 = "3";
    public static final String VALID_MONTH_2 = "2";
    public static final String VALID_YEAR_2019 = "2019";
    public static final String VALID_YEAR_1998 = "1998";

    public static final String NAME_DESC_CHICKEN = " " + PREFIX_NAME + VALID_NAME_CHICKEN;
    public static final String NAME_DESC_FRIES = " " + PREFIX_NAME + VALID_NAME_FRIES;
    public static final String CODE_DESC_CHICKEN = " " + PREFIX_CODE + VALID_CODE_CHICKEN;
    public static final String CODE_DESC_FRIES = " " + PREFIX_CODE + VALID_CODE_FRIES;
    public static final String PRICE_DESC_CHICKEN = " " + PREFIX_PRICE + VALID_PRICE_CHICKEN;
    public static final String PRICE_DESC_FRIES = " " + PREFIX_PRICE + VALID_PRICE_FRIES;
    public static final String ORDER_DESC_2_CHICKEN = " " + VALID_CODE_CHICKEN + " " + VALID_QUANTITY_2;
    public static final String ORDER_DESC_3_FRIES = " " + VALID_CODE_FRIES + " " + VALID_QUANTITY_3;
    public static final String DAY_DESC_1 = " " + PREFIX_DAY + VALID_DAY_1;
    public static final String MONTH_DESC_3 = " " + PREFIX_MONTH + VALID_MONTH_3;
    public static final String YEAR_DESC_2019 = " " + PREFIX_YEAR + VALID_YEAR_2019;
    public static final String DAY_DESC_29 = " " + PREFIX_DAY + VALID_DAY_29;
    public static final String MONTH_DESC_2 = " " + PREFIX_MONTH + VALID_MONTH_2;
    public static final String YEAR_DESC_1998 = " " + PREFIX_YEAR + VALID_YEAR_1998;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Ch!ck3n W!n95"; // non-alphanumeric characters
    public static final String INVALID_CODE_DESC = " " + PREFIX_CODE + "31A"; // first character should be a letter
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "a.50"; // 'a' not allowed in price
    public static final String INVALID_ORDER_CODE_DESC = " " + "31A" + " " + VALID_QUANTITY_2;
    public static final String INVALID_ORDER_QUANTITY_DESC = " " + VALID_CODE_CHICKEN + " " + "A";
    public static final String INVALID_ORDER_DESC = " " + VALID_QUANTITY_2 + " " + VALID_CODE_CHICKEN; // order swapped
    public static final String INVALID_DAY_DESC = " " + PREFIX_DAY + "32";
    public static final String INVALID_MONTH_DESC = " " + PREFIX_MONTH + "13";
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "1998";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Mode mode, Command command, Model actualModel,
            CommandHistory actualCommandHistory, CommandResult expectedCommandResult, Model expectedModel) {

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(mode, actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Mode mode, Command, Model, CommandHistory, CommandResult,
     * Model)} that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Mode mode, Command command, Model actualModel,
            CommandHistory actualCommandHistory, String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(mode, command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the RestOrRant, filtered order item list, filtered menu item list, filtered revenue list, filtered
     * - table list <br>
     * - and selected order item, selected menu item, selected revenue, selected table <br>
     * - in {@code actualModel} remain unchanged {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Mode mode, Command command, Model actualModel,
            CommandHistory actualCommandHistory, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RestOrRant expectedRestOrRant = new RestOrRant(actualModel.getRestOrRant());
        List<OrderItem> expectedFilteredOrderItemList = new ArrayList<>(actualModel.getFilteredOrderItemList());
        List<MenuItem> expectedFilteredMenuItemList = new ArrayList<>(actualModel.getFilteredMenuItemList());
        List<Revenue> expectedFilteredRevenueList =
                new ArrayList<>(actualModel.getFilteredRevenueList());
        List<Table> expectedFilteredTableList = new ArrayList<>(actualModel.getFilteredTableList());
        OrderItem expectedSelectedOrderItem = actualModel.getSelectedOrderItem();
        MenuItem expectedSelectedMenuItem = actualModel.getSelectedMenuItem();
        Table expectedSelectedTable = actualModel.getSelectedTable();
        Revenue expectedSelectedRevenue = actualModel.getSelectedRevenue();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(mode, actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedRestOrRant, actualModel.getRestOrRant());
            assertEquals(expectedFilteredOrderItemList, actualModel.getFilteredOrderItemList());
            assertEquals(expectedFilteredMenuItemList, actualModel.getFilteredMenuItemList());
            assertEquals(expectedFilteredTableList, actualModel.getFilteredTableList());
            assertEquals(expectedFilteredRevenueList, actualModel.getFilteredRevenueList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
            assertEquals(expectedSelectedOrderItem, actualModel.getSelectedOrderItem());
            assertEquals(expectedSelectedMenuItem, actualModel.getSelectedMenuItem());
            assertEquals(expectedSelectedTable, actualModel.getSelectedTable());
            assertEquals(expectedSelectedRevenue, actualModel.getSelectedRevenue());
        }
    }

    //    /**
    //     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
    //     * {@code model}'s address book.
    //     */
    //    public static void showPersonAtIndex(Model model, Index targetIndex) {
    //        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());
    //
    //        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
    //        final String[] splitName = person.getName().fullName.split("\\s+");
    //        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));
    //
    //        assertEquals(1, model.getFilteredPersonList().size());
    //    }

    /**
     * Updates {@code model}'s filtered list to show only the order item at the given {@code targetIndex} in the
     * {@code model}'s restaurant.
     */
    public static void showOrderItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOrderItemList().size());

        OrderItem orderItem = model.getFilteredOrderItemList().get(targetIndex.getZeroBased());
        final TableNumber tableNumber = orderItem.getTableNumber();

        final Code menuItemCode = orderItem.getMenuItemCode();
        model.updateFilteredOrderItemList(
            item -> tableNumber.equals(item.getTableNumber()) && menuItemCode.equals(item.getMenuItemCode()));

        assertEquals(1, model.getFilteredOrderItemList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the menu item at the given {@code targetIndex} in the
     * {@code model}'s restaurant.
     */
    public static void showMenuItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMenuItemList().size());

        MenuItem menuItem = model.getFilteredMenuItemList().get(targetIndex.getZeroBased());
        final Code code = menuItem.getCode();
        model.updateFilteredMenuItemList(item -> code.equals(item.getCode()));

        assertEquals(1, model.getFilteredMenuItemList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the table at the given {@code targetIndex} in the
     * {@code model}'s restaurant.
     */
    public static void showTableAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTableList().size());

        Table table = model.getFilteredTableList().get(targetIndex.getZeroBased());
        final TableNumber tableNumber = table.getTableNumber();
        model.updateFilteredTableList(item -> tableNumber.equals(table.getTableNumber()));

        assertEquals(1, model.getFilteredTableList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the revenue at the given {@code targetIndex} in the
     * {@code model}'s restaurant.
     */
    public static void showRevenueAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRevenueList().size());

        Revenue revenue = model.getFilteredRevenueList().get(targetIndex.getZeroBased());
        final Day day = revenue.getDay();
        final Month month = revenue.getMonth();
        final Year year = revenue.getYear();
        model.updateFilteredRevenueList(item -> day.equals(item.getDay()) && month.equals(item.getMonth())
                && year.equals(item.getYear()));
        assertEquals(1, model.getFilteredRevenueList().size());
    }

    //    /**
    //     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
    //     */
    //    public static void deleteFirstPerson(Model model) {
    //        Person firstPerson = model.getFilteredPersonList().get(0);
    //        model.deletePerson(firstPerson);
    //        model.updateMode();
    //    }

    /**
     * Deletes the first order item in {@code model}'s filtered list from {@code model}'s restaurant.
     */
    public static void deleteFirstOrderItem(Model model) {
        OrderItem firstOrderItem = model.getFilteredOrderItemList().get(0);
        model.deleteOrderItem(firstOrderItem);
    }

    /**
     * Deletes the first menu item in {@code model}'s filtered list from {@code model}'s restaurant.
     */
    public static void deleteFirstMenuItem(Model model) {
        MenuItem firstMenuItem = model.getFilteredMenuItemList().get(0);
        model.deleteMenuItem(firstMenuItem);
    }

    /**
     * Deletes the first table in {@code model}'s filtered list from {@code model}'s restaurant.
     */
    public static void deleteFirstTable(Model model) {
        Table firstTable = model.getFilteredTableList().get(0);
        model.deleteTable(firstTable);
    }

    /**
     * Deletes the first revenue in {@code model}'s filtered list from {@code model}'s restaurant.
     */
    public static void deleteFirstRevenue(Model model) {
        Revenue firstRevenue = model.getFilteredRevenueList().get(0);
        model.deleteRevenue(firstRevenue);
    }
}
