package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddToOrderCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.AddToOrderCommand.MESSAGE_INVALID_ITEM_CODE;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.logic.commands.ClearOrderCommand;
import seedu.address.model.Model;
import seedu.address.model.menu.Code;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.OrderItemStatus;
import seedu.address.testutil.OrderItemBuilder;
import seedu.address.testutil.RestOrRantUtil;

public class AddToOrderCommandSystemTest extends RestOrRantSystemTest {

    public static final String INVALID_ITEM_CODE_FORMAT = "23M";
    public static final String INVALID_ITEM_CODE_NOT_IN_MENU = "W23";
    public static final String INVALID_QUANTITY = "A";
    public static final String VALID_ITEM_CODE_SALAD = "A05";
    public static final String VALID_ITEM_CODE_CHEESE_NACHOS = "M17";
    public static final String VALID_QUANTITY = "3";
    public static final String VALID_ORDER_ITEM_DESC = "  M17  3 ";

    @Test
    public void add() {

        goToTableMode("1");

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add an order item to a non-empty order -> added */
        OrderItem salad = new OrderItemBuilder().withCode(VALID_ITEM_CODE_SALAD).withName("Salad").build();
        assertCommandSuccess(salad);

        /* Case: add an order item to a non-empty order, command with leading spaces and trailing spaces -> added */
        String command = "   " + AddToOrderCommand.COMMAND_WORD + VALID_ORDER_ITEM_DESC;
        OrderItem nachos = new OrderItemBuilder().withCode(VALID_ITEM_CODE_CHEESE_NACHOS)
                .withName("Cheese Nachos").build();
        assertCommandSuccess(command, nachos);

        executeCommand(ClearOrderCommand.COMMAND_WORD);

        /* Case: add an order item to any empty order -> added */
        assertCommandSuccess(TABLE1_W09);

        /* Case: add multiple order items -> added */
        command = AddToOrderCommand.COMMAND_WORD + " " + RestOrRantUtil.getOrderItemDetails(salad)
                + " " + RestOrRantUtil.getOrderItemDetails(nachos);
        List<OrderItem> toAdd = new ArrayList<>();
        toAdd.add(salad);
        toAdd.add(nachos);
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: missing arguments -> rejected */
        command = AddToOrderCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToOrderCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + RestOrRantUtil.getOrderItemDetails(salad);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid code format -> rejected */
        command = AddToOrderCommand.COMMAND_WORD + " " + INVALID_ITEM_CODE_FORMAT + " " + VALID_QUANTITY;
        assertCommandFailure(command, Code.MESSAGE_CONSTRAINTS);

        /* Case: invalid quantity format -> rejected */
        command = AddToOrderCommand.COMMAND_WORD + " " + VALID_ITEM_CODE_SALAD + " " + INVALID_QUANTITY;
        assertCommandFailure(command, OrderItemStatus.MESSAGE_CONSTRAINTS);

        /* Case: invalid code not in menu -> rejected */
        command = AddToOrderCommand.COMMAND_WORD + " " + INVALID_ITEM_CODE_NOT_IN_MENU + " " + VALID_QUANTITY;
        assertCommandFailure(command, String.format(MESSAGE_FAILURE, INVALID_ITEM_CODE_NOT_IN_MENU,
                MESSAGE_INVALID_ITEM_CODE));
    }

    /**
     * Executes the {@code AddTableCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTableCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code TableListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(OrderItem toAdd) {
        assertCommandSuccess(RestOrRantUtil.getAddToOrderCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(OrderItem)}. Executes {@code command}
     * instead.
     * @see AddToOrderCommandSystemTest#assertCommandSuccess(OrderItem)
     */
    private void assertCommandSuccess(String command, OrderItem toAdd) {
        Model expectedModel = getModel();
        expectedModel.addOrderItem(toAdd);
        expectedModel.updateFilteredOrderItemList(item -> item.getTableNumber().equals(toAdd.getTableNumber()));
        String expectedResultMessage = String.format(AddToOrderCommand.MESSAGE_SUCCESS,
                Collections.singletonList(toAdd));

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(OrderItem)}. Executes {@code command}
     * instead and allows multiple items to be added.
     * @see AddToOrderCommandSystemTest#assertCommandSuccess(OrderItem)
     */
    private void assertCommandSuccess(String command, List<OrderItem> toAdd) {
        Model expectedModel = getModel();
        for (OrderItem item : toAdd) {
            expectedModel.addOrderItem(item);
        }
        expectedModel.updateFilteredOrderItemList(item -> item.getTableNumber().equals(toAdd.get(0).getTableNumber()));
        String expectedResultMessage = String.format(AddToOrderCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, OrderItem)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code OrderItemListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddToOrderCommandSystemTest#assertCommandSuccess(String, List)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code OrderItemListPanel} remain unchanged.<br>
     * 5. Status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
