package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDoctorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalDocX;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDoctorCommand.
 */
public class ListDoctorCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDocX(), new UserPrefs());
        expectedModel = new ModelManager(model.getDocX(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListDoctorCommand(), model, commandHistory,
                ListDoctorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListDoctorCommand(), model, commandHistory,
                ListDoctorCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
