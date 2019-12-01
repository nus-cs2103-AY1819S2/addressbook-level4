package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDoctorAtIndex;
import static seedu.address.testutil.TypicalDoctors.getTypicalDocX_doctor;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.doctor.SelectDoctorCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectDoctorCommand}.
 */
public class SelectDoctorCommandTest {
    private Model model = new ModelManager(getTypicalDocX_doctor(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalDocX_doctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastDoctorIndex = Index.fromOneBased(model.getFilteredDoctorList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastDoctorIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredDoctorList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);
        showDoctorAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showDoctorAtIndex(model, INDEX_FIRST_PERSON);
        showDoctorAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of docX list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getDocX().getDoctorList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectDoctorCommand selectFirstCommand = new SelectDoctorCommand(INDEX_FIRST_PERSON);
        SelectDoctorCommand selectSecondCommand = new SelectDoctorCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectDoctorCommand selectFirstCommandCopy = new SelectDoctorCommand(INDEX_FIRST_PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different doctor -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectDoctorCommand} with the given {@code index},
     * and checks that the model's selected doctor is set to the doctor at {@code index} in the filtered doctor list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectDoctorCommand selectDoctorCommand = new SelectDoctorCommand(index);
        String expectedMessage = String.format(SelectDoctorCommand.MESSAGE_SELECT_DOCTOR_SUCCESS, index.getOneBased());
        expectedModel.setSelectedDoctor(model.getFilteredDoctorList().get(index.getZeroBased()));

        assertCommandSuccess(selectDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectDoctorCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectDoctorCommand selectDoctorCommand = new SelectDoctorCommand(index);
        assertCommandFailure(selectDoctorCommand, model, commandHistory, expectedMessage);
    }
}
