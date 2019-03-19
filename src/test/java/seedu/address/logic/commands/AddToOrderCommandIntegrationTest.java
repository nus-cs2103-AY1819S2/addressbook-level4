package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Before;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code AddToOrderCommand}.
 */
public class AddToOrderCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    }

    //    @Test TODO
    //    public void execute_newOrderItem_success() {
    //        List<Code> itemCodes = new ArrayList<>();
    //        itemCodes.add(new Code(VALID_CODE_CHICKEN));
    //        List<Integer> itemQuantities = new ArrayList<>();
    //        itemQuantities.add(3);
    //        List<OrderItem> orderItems = new ArrayList<>();
    //        orderItems.add(TABLE8_W09);
    //
    //        Model expectedModel = new ModelManager(model.getRestOrRant(), new UserPrefs());
    //        expectedModel.addOrderItem(TABLE8_W09);
    //        // expectedModel.updateMode(); TODO: Why?
    //
    //        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("8")).get());
    //
    //        assertCommandSuccess(Mode.TABLE_MODE, new AddToOrderCommand(itemCodes, itemQuantities), model, commandHistory,
    //                String.format(AddToOrderCommand.MESSAGE_SUCCESS, orderItems), expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_newOrderItems_success() {
    //        List<Code> itemCodes = new ArrayList<>();
    //        itemCodes.add(new Code(VALID_CODE_CHICKEN));
    //        itemCodes.add(new Code(VALID_CODE_FRIES));
    //        List<Integer> itemQuantities = new ArrayList<>();
    //        itemQuantities.add(3);
    //        itemQuantities.add(2);
    //        List<OrderItem> orderItems = new ArrayList<>();
    //        orderItems.add(TABLE8_W09);
    //        orderItems.add(TABLE8_W12);
    //
    //        Model expectedModel = new ModelManager(model.getRestOrRant(), new UserPrefs());
    //        expectedModel.addOrderItem(TABLE8_W09);
    //        expectedModel.addOrderItem(TABLE8_W12);
    //        // expectedModel.updateMode(); TODO: Why?
    //
    //        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("8")).get());
    //
    //        assertCommandSuccess(Mode.TABLE_MODE, new AddToOrderCommand(itemCodes, itemQuantities), model, commandHistory,
    //                String.format(AddToOrderCommand.MESSAGE_SUCCESS, orderItems), expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_invalidItemCode_throwsCommandException() {
    //        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("3")).get());
    //
    //        assertCommandFailure(Mode.TABLE_MODE,
    //                new AddToOrderCommand(Collections.singletonList(new Code("B99")), Collections.singletonList(5)), model,
    //                commandHistory, AddToOrderCommand.MESSAGE_INVALID_ITEM_CODE);
    //    }
    //
    //
    //    @Test
    //    public void execute_duplicateOrderItem_throwsCommandException() {
    //        OrderItem orderItemInList = model.getRestOrRant().getOrders().getOrderItemList().get(0);
    //        model.setSelectedTable(
    //                model.getRestOrRant().getTables().getTableFromNumber(orderItemInList.getTableNumber()).get());
    //
    //        assertCommandFailure(Mode.TABLE_MODE,
    //                new AddToOrderCommand(Collections.singletonList(orderItemInList.getMenuItemCode()),
    //                        Collections.singletonList(orderItemInList.getQuantity())), model, commandHistory,
    //                AddToOrderCommand.MESSAGE_DUPLICATE_ORDER_ITEM);
    //    }

}
