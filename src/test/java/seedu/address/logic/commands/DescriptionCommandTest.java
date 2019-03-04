package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Description;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with Model)and unit tests for DescriptionCommand.
 */
public class DescriptionCommandTest {

    private static final String DESCRIPTION_STUB = "Some description";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addDescriptionUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDescription(new Description(DESCRIPTION_STUB)).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(editedPerson.getDescription().value));

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_ADD_DESCRIPTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(descriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeDescriptionUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDescription(new Description("")).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(editedPerson.getDescription().value));

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_REMOVE_DESCRIPTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(descriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDescription(new Description(DESCRIPTION_STUB)).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(editedPerson.getDescription().value));

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_ADD_DESCRIPTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(descriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }




    @Test
    public void execute_invalidPersonIndexUnfilteredIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DescriptionCommand descriptionCommand = new DescriptionCommand(outOfBoundIndex,
                new Description(VALID_DESCRIPTION_BOB));

        assertCommandFailure(descriptionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // see if outOfBoundIndex is still smaller than max size of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DescriptionCommand descriptionCommand = new DescriptionCommand(outOfBoundIndex,
                new Description(VALID_DESCRIPTION_BOB));

        assertCommandFailure(descriptionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit)
                .withDescription(new Description(DESCRIPTION_STUB)).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(DESCRIPTION_STUB));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        // description -> first person description changed
        descriptionCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DescriptionCommand descriptionCommand = new DescriptionCommand(outOfBoundIndex, new Description(""));

        // execution failed -> address book state not added into model
        assertCommandFailure(descriptionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#description} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DescriptionCommand remarkCommand = new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(DESCRIPTION_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify)
                .withDescription(new Description(DESCRIPTION_STUB)).build();

        expectedModel.setPerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // remark -> modifies second person in unfiltered person list / first person in filtered person list
        remarkCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final DescriptionCommand standardCommand = new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(VALID_DESCRIPTION_AMY));

        // Object with same values -> returns true
        DescriptionCommand commandWithSameValues = new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(VALID_DESCRIPTION_AMY));

        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DescriptionCommand(INDEX_SECOND_PERSON,
                new Description(VALID_DESCRIPTION_AMY))));

        // different description -> returns false
        assertFalse(standardCommand.equals(new DescriptionCommand(INDEX_FIRST_PERSON,
                new Description(VALID_DESCRIPTION_BOB))));
    }
}
