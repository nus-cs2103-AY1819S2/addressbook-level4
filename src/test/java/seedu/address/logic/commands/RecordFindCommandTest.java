package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_RECORDS_LISTED_OVERVIEW;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecords.FIFTH;
import static seedu.address.testutil.TypicalRecords.FOURTH;
import static seedu.address.testutil.TypicalRecords.SIXTH;
import static seedu.address.testutil.TypicalRecords.THIRD;
import static seedu.address.testutil.TypicalRecords.getTypicalRecords;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedAddressBook;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.model.task.Task;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DescriptionRecordContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ProcedureContainsKeywordsPredicate;

public class RecordFindCommandTest {
    private ModelRecordStub model = new ModelRecordStub(getTypicalAddressBook(), new UserPrefs());
    private ModelRecordStub expectedModel = new ModelRecordStub(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Patient testPatient = ((Patient) ALICE);

    @Before
    public void setUp() {
        testPatient.setRecords(getTypicalRecords());
        model.getFilteredRecordList(testPatient);
        expectedModel.getFilteredRecordList(testPatient);
    }

    @After
    public void cleanUp() {
        testPatient.setRecords(new ArrayList<>());
    }

    @Test
    public void execute_procedureParameter() throws Exception {
        //No user input
        execute_parameterPredicate_test(0, " ", "procedure", true, false, Collections.emptyList());
        //Single keyword, ignore case, record found.
        execute_parameterPredicate_test(1, "CrOwN", "procedure",
            true, false, Arrays.asList(FOURTH));
        //Single keyword, case sensitive, no record found.
        execute_parameterPredicate_test(0, "CrOwN", "procedure", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple record found
        execute_parameterPredicate_test(2, "Crown FILLING", "procedure", true, false,
            Arrays.asList(THIRD, FOURTH));
        //Multiple keywords, ignore case, and condition, no record found
        execute_parameterPredicate_test(0, "Crown FILLING", "procedure", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, one record found
        execute_parameterPredicate_test(1, "Crown FILLING", "procedure", false, false,
            Arrays.asList(FOURTH));
        //Multiple keywords, case sensitive, and condition, no record found
        execute_parameterPredicate_test(0, "Crown FILLING", "procedure", false, true, Collections.emptyList());
    }

    @Test
    public void execute_descParameter() throws Exception {
        //No user input
        execute_parameterPredicate_test(0, " ", "desc", true, false, Collections.emptyList());
        //Single keyword, ignore case, record found.
        execute_parameterPredicate_test(1, "ReCROWNED", "desc",
            true, false, Arrays.asList(FOURTH));
        //Single keyword, case sensitive, no record found.
        execute_parameterPredicate_test(0, "ReCROWNED", "desc", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple record found
        execute_parameterPredicate_test(2, "roTten infection", "desc", true, false,
            Arrays.asList(FIFTH, SIXTH));
        //Multiple keywords, ignore case, and condition, no record found
        execute_parameterPredicate_test(0, "roTten infection", "desc", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, one record found
        execute_parameterPredicate_test(1, "roTten infection", "desc", false, false,
            Arrays.asList(FIFTH));
        //Multiple keywords, case sensitive, and condition, no record found
        execute_parameterPredicate_test(0, "roTten infection", "desc", false, true, Collections.emptyList());
    }



    /**
     * Wrapper function to test PatientFindCommand through each attribute
     * @param expectedNum expected number of returned Persons after predicate
     * @param userInput predicate to test
     * @param parameter attribute to test
     * @param isIgnoreCase flag for case sensitivity
     * @param isAnd flag for AND operator
     * @param expectedList expected list after predicate has been applied
     */
    private void execute_parameterPredicate_test(int expectedNum, String userInput, String parameter,
                                                 boolean isIgnoreCase, boolean isAnd,
                                                 List<Record> expectedList) throws ParseException {
        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, expectedNum);
        ContainsKeywordsPredicate predicate = prepareRecordPredicate(userInput, parameter, isIgnoreCase, isAnd);
        RecordFindCommand command = new RecordFindCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getFilteredRecordList());
    }

    /**
     * Parses {@code userInput} into a {@code ContainsKeywordsPredicate}.
     */
    private ContainsKeywordsPredicate prepareRecordPredicate(String userInput, String parameter, boolean isIgnoreCase,
                                                             boolean isAnd) throws ParseException {
        switch(parameter) {
        case "procedure":
            return new ProcedureContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        case "desc":
            return new DescriptionRecordContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        default:
            throw new ParseException("Invalid Sort Attribute.");
        }
    }

    /**
     * A custom model stub for recordFind testing.
     */
    private class ModelRecordStub implements Model {

        private final VersionedAddressBook versionedAddressBook;
        private final UserPrefs userPrefs;
        private final FilteredList<Person> filteredPersons;
        private final FilteredList<Task> filteredTasks;
        private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
        private final SimpleObjectProperty<Record> selectedRecord = new SimpleObjectProperty<>();
        private final SimpleObjectProperty<Task> selectedTask = new SimpleObjectProperty<>();

        private FilteredList<Record> filteredRecords;

        /**
         * Initializes a ModelManager with the given addressBook and userPrefs.
         */
        private ModelRecordStub(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
            super();
            requireAllNonNull(addressBook, userPrefs);

            versionedAddressBook = new VersionedAddressBook(addressBook);
            this.userPrefs = new UserPrefs(userPrefs);
            filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
            filteredTasks = new FilteredList<>(versionedAddressBook.getTaskList());
        }

        /**
         * Returns an unmodifiable view of the filtered record list
         */
        public ObservableList<Record> getFilteredRecordList(Patient patient) {
            if (patient != null) {
                versionedAddressBook.setRecords(patient.getRecords());
                if (filteredRecords == null) {
                    filteredRecords = new FilteredList<>(versionedAddressBook.getRecordList());
                }
                return filteredRecords;
            }
            return null;
        }

        @Override
        public ObservableList<Record> getFilteredRecordList() {
            return filteredRecords;
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            requireNonNull(predicate);
            filteredRecords.setPredicate(predicate);
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatientList(List<Person> persons) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecord(Record target, Record editedRecord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Record getSelectedRecord() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Record> selectedRecordProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTags(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setTask(Task task, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortAddressBook(Comparator<Patient> patientComparator, boolean isReverse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortRecordsBook(Comparator<Record> recordComparator, boolean isReverse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortTasks(Comparator<Task> c) {
            throw new AssertionError("This method should not be called");
        }

        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkNoCopy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof ModelRecordStub)) {
                return false;
            }

            // state check
            ModelRecordStub other = (ModelRecordStub) obj;
            return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredTasks.equals(other.filteredTasks)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
        }
    }
}
