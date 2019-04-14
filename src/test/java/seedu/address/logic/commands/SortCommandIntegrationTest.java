package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.SortWord;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.*;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SortCommandIntegrationTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private List<Person> correctPersonOrder = new ArrayList<>();

    /**
     * Use The sort by name method since they are already ordered
     */
    @Test
    public void execute_sortNames_After_Edit_success() {
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
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(editedPerson, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessageTwo, expectedModel);
    }
}
