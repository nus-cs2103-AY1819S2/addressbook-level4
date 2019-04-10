package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private List<Person> correctPersonOrder = new ArrayList<>();

    @Test
    public void execute_sortNames_success() {
        String expectedMessage = String.format("Sorted all persons by name");
        SortWord type = new SortWord("name");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

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

    @Test
    public void execute_sortSurnames_success() {
        //TODO: Look at situation for matching surnames
        String expectedMessage = String.format("Sorted all persons by surname");
        SortWord type = new SortWord("surname");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(GEORGE, FIONA, CARL, BENSON, DANIEL, ELLE, ALICE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
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

    @Test
    public void execute_sortEducation_success() {
        String expectedMessage = String.format("Sorted all persons by education");
        SortWord type = new SortWord("education");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(FIONA, ELLE, BENSON, CARL, ALICE, DANIEL, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortReverseEducation_success() {
        String expectedMessage = String.format("Sorted all persons by reverse education");
        SortWord type = new SortWord("reverse education");
        SortCommand command = new SortCommand(type);
        expectedModel.deleteAllPerson();
        correctPersonOrder = Arrays.asList(GEORGE, DANIEL, ALICE, CARL, BENSON, ELLE, FIONA);
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
        correctPersonOrder = Arrays.asList(GEORGE, ELLE, DANIEL, BENSON, CARL, ALICE, FIONA);
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
        correctPersonOrder = Arrays.asList(FIONA, ALICE, CARL, BENSON, DANIEL, ELLE, GEORGE);
        for (Person newPerson : correctPersonOrder) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
