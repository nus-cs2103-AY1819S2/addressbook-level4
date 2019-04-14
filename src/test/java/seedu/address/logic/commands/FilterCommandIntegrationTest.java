package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.sortmethods.SortMethod;
import seedu.address.logic.commands.sortmethods.SortName;
import seedu.address.logic.commands.sortmethods.SortUtil;
import seedu.address.logic.parser.SortWord;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.SkillsTag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for FilterCommand.
 * Checks the usage of filter command with edit, undo, redo, add, delete, sort
 * For each test except the last one the order is as follows:
 * 1- filter and/or
 * 2- filter and/or
 * 3- tested other command
 * 4- filter reverse
 * 5- tested other command
 * 6- filter clear
 * 7- tested other command
 * 8- filter reverse
 */
public class FilterCommandIntegrationTest {

    private static final int TYPE_CLEAR = 0;
    private static final int TYPE_OR = 1;
    private static final int TYPE_AND = 2;
    private static final int TYPE_REVERSE = 3;

    /**
     * Indexes in the criterion array:
     * 1- Name
     * 2- Phone
     * 3- Email
     * 4- Address
     * 5- Skills
     * 6- Positions
     * 7- Gpa
     * 8- Education
     * 9- Endorsement
     */

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private String[] criterionClearAndReverse = {null, null, null, null, null, null, null, null, null, null};
    private String[] criterion1 = {null, null, null, null, "Java, C++", null, "2.6", "NUS", null, null};
    private String[] criterion2 = {"e", "5", null, null, null, null, null, null, "2", "2"};
    private String[] criterion3 = {null, null, "a@", "street", null, "Manager, Developer", null, null, null, null};
    private String[] criterion4 = {null, null, null, null, "Java", null, "2.6", null, null, null};
    private String[] criterion5 = {null, null, "h", "street", null, null, null, null, null, "2"};

    /**
     * Divides the tags string to an array with separated tags
     */
    private String[] divideTagsFromString(String tags) {
        if (tags == null) {
            return null;
        }
        return tags.trim().split(", ");
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    @Test
    public void execute_filterWithUndoAndRedo() {

        // filtering takes place 2 times
        FilterCommand commandFilter = new FilterCommand(criterion1, TYPE_AND);
        expectedModel.filterAnd(criterion1[0], criterion1[1], criterion1[2], criterion1[3],
                divideTagsFromString(criterion1[4]), divideTagsFromString(criterion1[5]),
                criterion1[8], criterion1[6], criterion1[7], criterion1[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        commandFilter = new FilterCommand(criterion2, TYPE_OR);
        expectedModel.filterOr(criterion2[0], criterion2[1], criterion2[2], criterion2[3],
                divideTagsFromString(criterion1[4]), divideTagsFromString(criterion1[5]),
                criterion2[8], criterion2[6], criterion2[7], criterion2[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // undoing takes place 3 times
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);

        // redoing takes place
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // filter reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, expectedModel);

        // undo takes place
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo takes place
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // filter clear takes place 2 times
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        expectedModel.clearFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, expectedModel);

        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);

        // undo takes place
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo takes place
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // filter reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_REVERSE);
    }

    @Test
    public void execute_filterWithEdit() {
        // filtering takes place
        FilterCommand commandFilter = new FilterCommand(criterion3, TYPE_OR);
        expectedModel.filterOr(criterion3[0], criterion3[1], criterion3[2], criterion3[3],
                divideTagsFromString(criterion3[4]), divideTagsFromString(criterion3[5]),
                criterion3[8], criterion3[6], criterion3[7], criterion3[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // edit all fields specified successful
        Person editedPerson = new PersonBuilder().build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

        // edit outOfBoundsException failure
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        editCommand = new EditCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // filter reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, expectedModel);

        // edit no fields specified successful
        editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditPersonDescriptor());
        editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

        // edit duplicate person failure
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);
        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);

        // filter clear takes place 2 times
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        expectedModel.clearFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, expectedModel);

        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);

        // edit some fields specified successful
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        PersonBuilder personInList = new PersonBuilder(lastPerson);
        editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withDegree("Bachelors")
                .withSkills(VALID_SKILL_JAVA).build();

        String[] skillsToBuild = new String[5];
        int i = 0;
        for (SkillsTag tag : editedPerson.getTags()) {
            skillsToBuild[i] = tag.toString().substring(1, tag.toString().length() - 1);
            i++;
        }

        descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSkills(skillsToBuild[0], skillsToBuild[1], skillsToBuild[2],
                        skillsToBuild[3], skillsToBuild[4]).build();
        editCommand = new EditCommand(indexLastPerson, descriptor);
        expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
        expectedModel.setPerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

        // filter reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_REVERSE);

    }

    @Test
    public void execute_filterWithDelete() {

        // filtering takes place
        FilterCommand commandFilter = new FilterCommand(criterion3, TYPE_OR);
        expectedModel.filterOr(criterion3[0], criterion3[1], criterion3[2], criterion3[3],
                divideTagsFromString(criterion3[4]), divideTagsFromString(criterion3[5]),
                criterion3[8], criterion3[6], criterion3[7], criterion3[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // delete takes place 3 times
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);

        personToDelete = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        deleteCommand = new DeleteCommand(INDEX_THIRD_PERSON);
        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        deleteCommand = new DeleteCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // filter reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, expectedModel);

        // delete takes place 2 times
        personToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        deleteCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);

        outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        deleteCommand = new DeleteCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);


        // filter clear takes place 2 times
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        expectedModel.clearFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, expectedModel);

        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);

        // delete takes place 2 times
        personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);

        outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        deleteCommand = new DeleteCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);


        // filter reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_REVERSE);

    }

    @Test
    public void execute_filterWithAdd() {

        // filtering takes place 2 times
        FilterCommand commandFilter = new FilterCommand(criterion2, TYPE_AND);
        expectedModel.filterAnd(criterion2[0], criterion2[1], criterion2[2], criterion2[3],
                divideTagsFromString(criterion2[4]), divideTagsFromString(criterion2[5]),
                criterion2[8], criterion2[6], criterion2[7], criterion2[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // filtering reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, expectedModel);

        // valid addition takes place
        Person validPerson = HOON;

        expectedModel.addPerson(validPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);

        // invalid addition takes place
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);

        // filtering reverse takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_REVERSE);

        // filter clear takes place
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);
    }

    @Test
    public void execute_filterWithSortName() {

        // filter takes place - success
        FilterCommand commandFilter = new FilterCommand(criterion1, TYPE_OR);
        expectedModel.filterOr(criterion1[0], criterion1[1], criterion1[2], criterion1[3],
                divideTagsFromString(criterion1[4]), divideTagsFromString(criterion1[5]),
                criterion1[8], criterion1[6], criterion1[7], criterion1[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        commandFilter = new FilterCommand(criterion3, TYPE_AND);
        expectedModel.filterAnd(criterion3[0], criterion3[1], criterion3[2], criterion3[3],
                divideTagsFromString(criterion3[4]), divideTagsFromString(criterion3[5]),
                criterion3[8], criterion3[6], criterion3[7], criterion3[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS,
                expectedModel);

        // sorting by names takes place - success
        String expectedMessage = "Sorted all persons by name";
        SortWord type = new SortWord("name");
        SortCommand commandSort = new SortCommand(type);
        SortMethod sortName = new SortName();
        sortName.execute(expectedModel.getAddressBook().getPersonList());
        List<Person> sortedPersons = sortName.getList();
        expectedModel.deleteAllPerson();
        for (Person newPerson : sortedPersons) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandSort, model, commandHistory, expectedMessage, expectedModel);

        // filter reverse takes place - success
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory, FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS,
                expectedModel);

        // sorting by reverse names takes place - success
        expectedMessage = "Sorted all persons by reverse name";
        SortWord typeReverse = new SortWord("reverse name");
        SortCommand commandReverseSort = new SortCommand(typeReverse);
        SortMethod sortReverseNameCommand = new SortName();
        sortReverseNameCommand.execute(expectedModel.getAddressBook().getPersonList(), "reverse name");
        sortedPersons = SortUtil.reversePersonList(sortReverseNameCommand.getList());
        expectedModel.deleteAllPerson();
        for (Person newPerson : sortedPersons) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandReverseSort, model, commandHistory, expectedMessage, expectedModel);

        // filter clear takes place - success
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        expectedModel.clearFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, expectedModel);

        // filter clear takes place - failure
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);

        // sort by names takes place
        expectedMessage = "Sorted all persons by name";
        SortMethod sortNameCommand = new SortName();
        sortNameCommand.execute(expectedModel.getAddressBook().getPersonList(), "name");
        sortedPersons = sortNameCommand.getList();
        expectedModel.deleteAllPerson();
        for (Person newPerson : sortedPersons) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandSort, model, commandHistory, expectedMessage, expectedModel);

        // filter reverse takes place - failure
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_REVERSE);
    }

    @Test
    public void execute_filterWithFindCommand() {

        // filtering called - success
        FilterCommand commandFilter = new FilterCommand(criterion1, TYPE_OR);
        expectedModel.filterOr(criterion1[0], criterion1[1], criterion1[2], criterion1[3],
                divideTagsFromString(criterion1[4]), divideTagsFromString(criterion1[5]),
                criterion1[8], criterion1[6], criterion1[7], criterion1[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // finding called - success
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand commandFind = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(commandFind, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());

        // finding called - failure
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        predicate = preparePredicate(" ");
        commandFind = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(commandFind, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // filtering reverse called - success
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory, FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS,
                expectedModel);

        // finding called - success
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate("George");
        commandFind = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(commandFind, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(GEORGE), model.getFilteredPersonList());

        // finding called - failure
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        predicate = preparePredicate(" ");
        commandFind = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(commandFind, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // filtering clear called - success
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        expectedModel.clearFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, expectedModel);

        // finding called - success
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        predicate = preparePredicate("Alice Benson");
        commandFind = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(commandFind, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());

        // finding called - failure
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        predicate = preparePredicate(" ");
        commandFind = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(commandFind, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());

        // filtering clear called - failure
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);

    }

    @Test
    public void execute_filterWithSelectCommand() {

        // filtering called - success
        FilterCommand commandFilter = new FilterCommand(criterion2, TYPE_AND);
        expectedModel.filterAnd(criterion2[0], criterion2[1], criterion2[2], criterion2[3],
                divideTagsFromString(criterion2[4]), divideTagsFromString(criterion2[5]),
                criterion2[8], criterion2[6], criterion2[7], criterion2[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // selecting called - success
        assertExecutionSuccess(INDEX_FIRST_PERSON);

        // selecting called - failure
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // filtering reverse called - success
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory, FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS,
                expectedModel);

        // selecting called - success
        assertExecutionSuccess(INDEX_THIRD_PERSON);

        // selecting called - failure
        outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 2);
        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // filtering clear called - success
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        expectedModel.clearFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, expectedModel);

        // selecting called - success
        assertExecutionSuccess(INDEX_SECOND_PERSON);

        // selecting called - failure
        outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 3);
        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // filtering clear called - failure
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);
    }

    @Test
    public void execute_allCommands() {

        // filter and called
        FilterCommand commandFilter = new FilterCommand(criterion4, TYPE_AND);
        expectedModel.filterAnd(criterion4[0], criterion4[1], criterion4[2], criterion4[3],
                divideTagsFromString(criterion4[4]), divideTagsFromString(criterion4[5]),
                criterion4[8], criterion4[6], criterion4[7], criterion4[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // filter or called
        commandFilter = new FilterCommand(criterion5, TYPE_OR);
        expectedModel.filterOr(criterion5[0], criterion5[1], criterion5[2], criterion5[3],
                divideTagsFromString(criterion5[4]), divideTagsFromString(criterion5[5]),
                criterion5[8], criterion5[6], criterion5[7], criterion5[9]);
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, expectedModel);

        // sort name called
        String expectedMessage = "Sorted all persons by name";
        SortWord type = new SortWord("name");
        SortCommand commandSort = new SortCommand(type);
        SortMethod sortName = new SortName();
        sortName.execute(expectedModel.getAddressBook().getPersonList());
        List<Person> sortedPersons = sortName.getList();
        expectedModel.deleteAllPerson();
        for (Person newPerson : sortedPersons) {
            expectedModel.addPersonWithFilter(newPerson);
        }
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandSort, model, commandHistory, expectedMessage, expectedModel);

        // delete 1 called
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);

        // undo called 2 times
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo called
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // filter reverse called
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, expectedModel);

        // add called
        Person validPerson = HOON;
        expectedModel.addPerson(validPerson);
        expectedModel.commitAddressBook();
        assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);

        // filter clear called - failure
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);

        // filter reverse called - failure
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_REVERSE);

        // undo called
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // filter reverse called
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_REVERSE);
        expectedModel.reverseFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, expectedModel);

        // find called
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("Carl Kunz");
        FindCommand commandFind = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(commandFind, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());

        // list called
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);

        // select called
        assertExecutionSuccess(INDEX_SECOND_PERSON);

        // filter clear called
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        expectedModel.clearFilter();
        expectedModel.commitAddressBook();
        assertCommandSuccess(commandFilter, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, expectedModel);

        // filter clear called - failure
        commandFilter = new FilterCommand(criterionClearAndReverse, TYPE_CLEAR);
        assertCommandFailure(commandFilter, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);
    }


    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected person is set to the person at {@code index} in the filtered person list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());
        expectedModel.setSelectedPerson(model.getFilteredPersonList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
