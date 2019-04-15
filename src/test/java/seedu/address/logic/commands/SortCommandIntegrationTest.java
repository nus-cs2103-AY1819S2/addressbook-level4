package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.SortWord;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SortCommandIntegrationTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Method for modifying the expected model from a list sorted as per input
     */
    private void expectedModelFromSort(List<Person> correctPersonOrder) {
        expectedModel.deleteAllPerson();
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
    }

    /**
     * Use The sort by name method, since they are already ordered, after a trivial edit
     */
    @Test
    public void executeSortNamesAfterEditSuccess() {
        // NOTE: method naming style change is since it is demanded by codacy coding standard test
        // First make trivial edit to the first person
        Person editedPerson = new PersonBuilder().build();
        EditCommand.EditPersonDescriptor descriptorOne = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptorOne);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
        // Perform sorting process
        String expectedMessageTwo = String.format("Sorted all persons by name");
        SortWord type = new SortWord("name");
        SortCommand command = new SortCommand(type);
        expectedModelFromSort(Arrays.asList(editedPerson, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        assertCommandSuccess(command, model, commandHistory, expectedMessageTwo, expectedModel);
    }

}
