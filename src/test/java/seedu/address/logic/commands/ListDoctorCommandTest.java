package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import org.junit.Before;
import org.junit.Test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDoctorCommand.
 */
public class ListDoctorCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPatientCommand(), model, commandHistory,
                ListDoctorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPatientAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListPatientCommand(), model, commandHistory,
                ListPatientCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
