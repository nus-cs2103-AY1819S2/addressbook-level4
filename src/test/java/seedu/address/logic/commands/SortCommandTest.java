package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDORSEMENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
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
 * Contains tests for {@code SortCommand}.
 * The sort command is commonly used in collaboration with the edit command and so it should be ensured that the
 *     {@code execute_sortNames_After_Edit_success} test in {@code SortCommandIntegrationTest} class is passing
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private List<Person> correctPersonOrder = new ArrayList<>();

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
     * Sort by name.
     * i. Make two names similar, initially in wrong order. Check the sort orders them as desired.
     *        2nd person: Alex Meier
     * ii. Check the surname is subsequently ordered
     */
    @Test
    public void execute_sortNames_success() {
        // i.
        Person editedBenson = new PersonBuilder()
                .withName("Alex Meier").build();
        EditCommand.EditPersonDescriptor descriptorOne = new EditPersonDescriptorBuilder(editedBenson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptorOne);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedBenson);
        expectedModel.setPerson(model.getFilteredPersonList().get(1), editedBenson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

        String expectedMessageTwo = String.format("Sorted all persons by name");
        SortWord type = new SortWord("name");
        SortCommand sortCommand = new SortCommand(type);
        expectedModelFromSort(Arrays.asList(editedBenson, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE));
        assertCommandSuccess(sortCommand, model, commandHistory, expectedMessageTwo, expectedModel);
        // ii.
        Person editedAlex = new PersonBuilder()
                .withName("Fiona Kunzz").build();
        EditCommand.EditPersonDescriptor descriptorTwo = new EditPersonDescriptorBuilder(editedAlex).build();
        EditCommand editCommandTwo = new EditCommand(INDEX_FIRST_PERSON, descriptorTwo);
        String expectedMessageThree = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedAlex);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedAlex);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommandTwo, model, commandHistory, expectedMessageThree, expectedModel);

        SortCommand sortCommandTwo = new SortCommand(type);
        expectedModelFromSort(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, editedAlex, GEORGE));
        assertCommandSuccess(sortCommandTwo, model, commandHistory, expectedMessageTwo, expectedModel);
    }

    /**
     * Test for sorting names in reverse order
     */
    @Test
    public void execute_sortReverseNames_success() {
        //TODO: Look at situation for matching surnames
        String expectedMessage = String.format("Sorted all persons by reverse name");
        SortWord type = new SortWord("reverse name");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Sort by surname.
     * Edit typical persons so that three have same surname to see if subsequent first name sorting occurs
     */
    @Test
    public void execute_sortSurnames_success() {
        String Surname  = GEORGE.surnameToString();
        Person editedAlice = new PersonBuilder()
                .withName(model.getFilteredPersonList().get(0).firstNameToString() + " " + Surname).build();
        Person editedBenson = new PersonBuilder()
                .withName(model.getFilteredPersonList().get(1).firstNameToString() + " " + Surname).build();

        // Edit the first person to have George's surname
        EditCommand.EditPersonDescriptor descriptorOne = new EditPersonDescriptorBuilder(editedAlice).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptorOne);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedAlice);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedAlice);
        expectedModel.commitAddressBook();
        System.out.println(expectedModel.getFilteredPersonList());
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

        // Edit the second person to have George's surname
        EditCommand.EditPersonDescriptor descriptorTwo = new EditPersonDescriptorBuilder(editedBenson).build();
        EditCommand editCommandTwo = new EditCommand(INDEX_SECOND_PERSON, descriptorTwo);
        String expectedMessageTwo = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedBenson);
        expectedModel.setPerson(model.getFilteredPersonList().get(1), editedBenson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommandTwo, model, commandHistory, expectedMessageTwo, expectedModel);

        String expectedMessageThree = String.format("Sorted all persons by surname");
        SortWord type = new SortWord("surname");
        SortCommand command = new SortCommand(type);
        expectedModelFromSort(Arrays.asList(editedAlice, editedBenson, GEORGE, FIONA, CARL, DANIEL, ELLE));
        assertCommandSuccess(command, model, commandHistory, expectedMessageThree, expectedModel);
    }

    @Test
    public void execute_sortReverseSurnames_success() {
        //TODO: Look at situation for matching surnames
        String expectedMessage = String.format("Sorted all persons by reverse surname");
        SortWord type = new SortWord("reverse surname");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(ALICE, ELLE, DANIEL, BENSON, CARL, FIONA, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Sort by education.
     * Typical persons already has persons with duplicate education and so already demands subsequent sorting which
     *     can be checked.
     */
    @Test
    public void execute_sortEducation_success() {
        String expectedMessage = String.format("Sorted all persons by education");
        SortWord type = new SortWord("education");
        SortCommand command = new SortCommand(type);
        expectedModelFromSort(Arrays.asList(FIONA, ELLE, CARL, BENSON, ALICE, DANIEL, GEORGE));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortReverseEducation_success() {
        String expectedMessage = String.format("Sorted all persons by reverse education");
        SortWord type = new SortWord("reverse education");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(GEORGE, DANIEL, ALICE, BENSON, CARL, ELLE, FIONA);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortGpa_success() {
        String expectedMessage = String.format("Sorted all persons by gpa");
        SortWord type = new SortWord("gpa");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(FIONA, ALICE, CARL, BENSON, DANIEL, ELLE, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortReverseGpa_success() {
        String expectedMessage = String.format("Sorted all persons by reverse gpa");
        SortWord type = new SortWord("reverse gpa");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(GEORGE, ELLE, DANIEL, BENSON, CARL, ALICE, FIONA);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortSkills_success() {
        String expectedMessage = String.format("Sorted all persons by skills");
        SortWord type = new SortWord("skills");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(ELLE, ALICE, BENSON, DANIEL, FIONA, CARL, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortPositions_success() {
        String expectedMessage = String.format("Sorted all persons by positions");
        SortWord type = new SortWord("positions");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(ELLE, FIONA, GEORGE, ALICE, CARL, DANIEL, BENSON);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortEndorsements_success() {
        String expectedMessage = String.format("Sorted all persons by endorsements");
        SortWord type = new SortWord("endorsements");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(DANIEL, FIONA, ELLE, GEORGE, ALICE, BENSON, CARL);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortSkillNumber_success() {
        String expectedMessage = String.format("Sorted all persons by skill number");
        SortWord type = new SortWord("skill number");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(CARL, ELLE, BENSON, DANIEL, FIONA, ALICE, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortPositionNumber_success() {
        String expectedMessage = String.format("Sorted all persons by position number");
        SortWord type = new SortWord("position number");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(FIONA, CARL, GEORGE, ALICE, DANIEL, BENSON, ELLE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortEndorsementNumber_success() {
        String expectedMessage = String.format("Sorted all persons by endorsement number");
        SortWord type = new SortWord("endorsement number");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(DANIEL, FIONA, ELLE, GEORGE, ALICE, BENSON, CARL);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortDegreeNumber_success() {
        String expectedMessage = String.format("Sorted all persons by degree");
        SortWord type = new SortWord("degree");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(DANIEL, FIONA, ELLE, GEORGE, ALICE, BENSON, CARL);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
