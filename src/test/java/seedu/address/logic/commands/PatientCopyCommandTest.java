package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class PatientCopyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void copy_onePerson_success() throws Exception {
        Person personToCopy = expectedModel.getFilteredPersonList().get(0).copy();
        expectedModel.addPerson(personToCopy);
        String expectedMessage = String.format(PatientCopyCommand.MESSAGE_SUCCESS, personToCopy);
        CommandResult commandResult = new PatientCopyCommand(INDEX_FIRST_PERSON, 1)
                .execute(model, commandHistory);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList().toString(),
                model.getFilteredPersonList().toString());
    }

    @Test
    public void copy_twoPersons_success() throws Exception {
        Person personToCopy1 = expectedModel.getFilteredPersonList().get(0).copy();
        Person personToCopy2 = expectedModel.getFilteredPersonList().get(0).copy();
        expectedModel.addPerson(personToCopy1);
        expectedModel.addPerson(personToCopy2);
        String expectedMessage = String.format(PatientCopyCommand.MESSAGE_SUCCESS, personToCopy1);
        CommandResult commandResult = new PatientCopyCommand(INDEX_FIRST_PERSON, 2)
                .execute(model, commandHistory);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList().toString(),
                model.getFilteredPersonList().toString());

    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PatientCopyCommand patientCopyCommand = new PatientCopyCommand(outOfBoundIndex, 1);

        assertCommandFailure(patientCopyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
