package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookSortedByColor;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookSortedByName;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookSortedByNameCaseInsensitive;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookSortedByNameDesc;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookSortedByType;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookSortedByTypeDesc;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBookWithLowerCaseName;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.options.SortOption;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 *
 */
public class SortCommandTest {

    // Setup
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_seeSortOptions_success() {
        Model expectedModelSortedByType = new ModelManager(model.getAddressBook(), new UserPrefs());

        SortCommand sc = new SortCommand(SortOption.OPTIONS);
        String expectedMessage = SortOption.allOptions();

        assertCommandSuccess(sc, model, commandHistory, expectedMessage, expectedModelSortedByType);
    }

    @Test
    public void execute_sortedName_success() {
        Model expectedModelSortedByName = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModelSortedByName.setAddressBook(getTypicalAddressBookSortedByName());
        expectedModelSortedByName.commitAddressBook();

        SortCommand sc = new SortCommand(SortOption.NAME);
        String expectedMessage = SortCommand.MESSAGE_SORT_APPAREL_SUCCESS + " by name.";

        assertCommandSuccess(sc, model, commandHistory, expectedMessage, expectedModelSortedByName);
    }

    @Test
    public void execute_sortedNameCaseInsensitive_success() {
        Model modelLowerCase = new ModelManager(getTypicalAddressBookWithLowerCaseName(), new UserPrefs());

        Model expectedModel = new ModelManager(modelLowerCase.getAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(getTypicalAddressBookSortedByNameCaseInsensitive());
        expectedModel.commitAddressBook();

        SortCommand sc = new SortCommand(SortOption.NAME);
        String expectedMessage = SortCommand.MESSAGE_SORT_APPAREL_SUCCESS + " by name.";

        assertCommandSuccess(sc, modelLowerCase, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortedColor_success() {
        Model expectedModelSortedByColor = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModelSortedByColor.setAddressBook(getTypicalAddressBookSortedByColor());
        expectedModelSortedByColor.commitAddressBook();

        SortCommand sc = new SortCommand(SortOption.COLOR);
        String expectedMessage = SortCommand.MESSAGE_SORT_APPAREL_SUCCESS + " by color.";

        assertCommandSuccess(sc, model, commandHistory, expectedMessage, expectedModelSortedByColor);
    }

    @Test
    public void execute_sortedType_success() {
        Model expectedModelSortedByType = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModelSortedByType.setAddressBook(getTypicalAddressBookSortedByType());
        expectedModelSortedByType.commitAddressBook();

        SortCommand sc = new SortCommand(SortOption.TYPE);
        String expectedMessage = SortCommand.MESSAGE_SORT_APPAREL_SUCCESS + " by type.";

        assertCommandSuccess(sc, model, commandHistory, expectedMessage, expectedModelSortedByType);
    }

    @Test
    public void execute_sortedNameDesc_success() {
        Model expectedModelSortedByName = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelSortedByName.setAddressBook(getTypicalAddressBookSortedByNameDesc());
        expectedModelSortedByName.commitAddressBook();

        SortCommand sc = new SortCommand(SortOption.NAME, true);
        String expectedMessage = SortCommand.MESSAGE_SORT_APPAREL_SUCCESS + " by name.";

        assertCommandSuccess(sc, model, commandHistory, expectedMessage, expectedModelSortedByName);
    }

    @Test
    public void execute_sortedColorDesc_success() {
        /**
        Model expectedModelSortedByColor = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModelSortedByColor.setAddressBook(getTypicalAddressBookSortedByColorDesc());
        expectedModelSortedByColor.commitAddressBook();

        SortCommand sc = new SortCommand(SortOption.COLOR, true);
        String expectedMessage = SortCommand.MESSAGE_SORT_APPAREL_SUCCESS + " by color.";

        assertCommandSuccess(sc, model, commandHistory, expectedMessage, expectedModelSortedByColor);
         **/
    }

    @Test
    public void execute_sortedTypeDesc_success() {
        Model expectedModelSortedByType = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModelSortedByType.setAddressBook(getTypicalAddressBookSortedByTypeDesc());
        expectedModelSortedByType.commitAddressBook();

        SortCommand sc = new SortCommand(SortOption.TYPE, true);
        String expectedMessage = SortCommand.MESSAGE_SORT_APPAREL_SUCCESS + " by type.";

        assertCommandSuccess(sc, model, commandHistory, expectedMessage, expectedModelSortedByType);
    }
}
