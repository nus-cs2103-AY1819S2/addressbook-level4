package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMedHistAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalDocX;

import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListMedHistCommand.
 */
public class ListMedHistCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDocX(), new UserPrefs());
        expectedModel = new ModelManager(model.getDocX(), new UserPrefs());
    }

    @Test
    public void execute_medHistListIsNotFiltered_showsSameMedHistList() {
        assertCommandSuccess(new ListMedHistCommand(null, null, null),
                model, commandHistory, ListMedHistCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_medHistListIsFiltered_showsEverything() {
        assertCommandSuccess(new ListMedHistCommand(null, null, null),
                model, commandHistory, ListMedHistCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
