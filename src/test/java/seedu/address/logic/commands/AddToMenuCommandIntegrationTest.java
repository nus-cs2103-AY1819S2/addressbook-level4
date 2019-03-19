package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.MenuItem;
import seedu.address.testutil.MenuItemBuilder;


public class AddToMenuCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    }

    @Test
    public void execute_newMenuItem_success() {
        MenuItem validMenuItem = new MenuItemBuilder().build();

        Model expectedModel = new ModelManager(model.getRestOrRant(), new UserPrefs());
        expectedModel.addMenuItem(validMenuItem);
        expectedModel.updateMode();

        assertCommandSuccess(Mode.MENU_MODE, new AddToMenuCommand(validMenuItem), model, commandHistory,
                String.format(AddToMenuCommand.MESSAGE_SUCCESS, validMenuItem), expectedModel);
    }

    @Test
    public void execute_duplicateMenuItem_throwsCommandException() {
        MenuItem menuItemInList = model.getRestOrRant().getMenu().getMenuItemList().get(0);
        assertCommandFailure(Mode.MENU_MODE, new AddToMenuCommand(menuItemInList), model, commandHistory,
                AddToMenuCommand.MESSAGE_DUPLICATE_MENU_ITEM);
    }

}
