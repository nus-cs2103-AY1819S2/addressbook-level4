package seedu.address.storage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.MenuItem;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalMenuItems;

public class JsonSerializableMenuTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMenuTest");
    private static final Path TYPICAL_MENU_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalMenuItems.json");
    private static final Path INVALID_MENU_ITEM_FILE = TEST_DATA_FOLDER.resolve("InvalidMenuItem.json");
    private static final Path DUPLICATE_MENU_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateMenuItem.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

        @Test
        public void toModelType_typicalMenuItemsFile_success() throws Exception {
            JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(TYPICAL_MENU_ITEMS_FILE,
                    JsonSerializableMenu.class).get();
            Menu menuFromFile = dataFromFile.toModelType();
            Menu typicalMenuItems = new Menu();
            for (MenuItem menuItem : getTypicalMenuItems()) {
                typicalMenuItems.addMenuItem(menuItem);
            }
            assertEquals(menuFromFile.getMenuItemList(), typicalMenuItems.getMenuItemList());
        }

        @Test
        public void toModelType_invalidMenuItemFile_throwsIllegalValueException() throws Exception {
            JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(INVALID_MENU_ITEM_FILE,
                    JsonSerializableMenu.class).get();
            thrown.expect(IllegalValueException.class);
            dataFromFile.toModelType();
        }

    @Test
    public void toModelType_duplicateMenuItems_throwsIllegalValueException() throws Exception {
        JsonSerializableMenu dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MENU_ITEM_FILE,
                JsonSerializableMenu.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableMenu.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }

}
