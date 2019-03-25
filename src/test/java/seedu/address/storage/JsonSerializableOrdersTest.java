package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalOrderItems;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.Orders;

public class JsonSerializableOrdersTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableOrdersTest");
    private static final Path TYPICAL_ORDER_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalOrderItemsOrders.json");
    private static final Path INVALID_ORDER_ITEM_FILE = TEST_DATA_FOLDER.resolve("InvalidOrderItemOrders.json");
    private static final Path DUPLICATE_ORDER_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateOrderItemOrders.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalOrderItemsFile_success() throws Exception {
        JsonSerializableOrders dataFromFile = JsonUtil.readJsonFile(TYPICAL_ORDER_ITEMS_FILE,
                JsonSerializableOrders.class).get();
        Orders restOrRantFromFile = dataFromFile.toModelType();
        Orders typicalOrderItemsOrders = new Orders();
        for (OrderItem orderItem : getTypicalOrderItems()) {
            typicalOrderItemsOrders.addOrderItem(orderItem);
        }
        assertEquals(restOrRantFromFile, typicalOrderItemsOrders);
    }

    @Test
    public void toModelType_invalidOrderItemFile_throwsIllegalValueException() throws Exception {
        JsonSerializableOrders dataFromFile = JsonUtil.readJsonFile(INVALID_ORDER_ITEM_FILE,
                JsonSerializableOrders.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateOrderItems_throwsIllegalValueException() throws Exception {
        JsonSerializableOrders dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ORDER_ITEM_FILE,
                JsonSerializableOrders.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableOrders.MESSAGE_DUPLICATE_ORDER_ITEMS);
        dataFromFile.toModelType();
    }

}
