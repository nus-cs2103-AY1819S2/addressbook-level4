package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPrescriptionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRESCRIPTION;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalDocX_prescription;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.prescription.ListPrescriptionCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPrescriptionCommand.
 */
public class ListPrescriptionCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDocX_prescription(), new UserPrefs());
        expectedModel = new ModelManager(model.getDocX(), new UserPrefs());
    }

    /*@Test
    public void execute_zeroKeywords_listAllPrescriptions() {
        String expectedMessage = MESSAGE_SUCCESS;
        ListPrescriptionCommand command = new ListPrescriptionCommand(null, null);
        expectedModel.updateFilteredPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_prescriptionListIsNotFiltered_showsSamePrescriptionList() {
        assertCommandSuccess(new ListPrescriptionCommand(null, null),
                model, commandHistory, ListPrescriptionCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_prescriptionListIsFiltered_showsEverything() {
        showPrescriptionAtIndex(model, INDEX_FIRST_PRESCRIPTION);
        assertCommandSuccess(new ListPrescriptionCommand(null, null),
                model, commandHistory, ListPrescriptionCommand.MESSAGE_SUCCESS, expectedModel);
    }



}
