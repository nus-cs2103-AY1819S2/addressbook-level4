package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.record.Statistics;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean duplicatePatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isPatientListEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkValidIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getPatientAtIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkDuplicatePatientAfterEdit(int index, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void replacePatient(int index, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String findPatientsByName(String searchSequence) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listFiftyPatients() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String findPatientsByNric(String searchSequence) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String findPatientsByTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getPatientByNric(String nric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createConsultation(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        public Optional<Patient> getPatientWithNric(Nric nric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void diagnosePatient(Diagnosis diagnosis) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkConsultation() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void prescribeMedicine(ArrayList<Prescription> prescriptions) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Consultation getCurrentConsultation() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void endConsultation() {
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
        public boolean duplicateApp(Appointment app) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addApp(Appointment app) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listApp() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean duplicateRem(Reminder rem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRem(Reminder rem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listRem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Reminder> selectedReminderProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Statistics getStatistics(String topic, YearMonth from, YearMonth to) {
            throw new AssertionError("This method should not be called.");
        }
        public void addMedicine(String medicineName, String[] path) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addMedicine(String medicineName, int quantity, String[] path) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addDirectory(String directoryName, String[] path) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Optional<Medicine> findMedicine(String medicineName) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Optional<Medicine> findMedicine(String[] path) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void purchaseMedicine(String[] path, int quantity) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void purchaseMedicine(String medicineName, int quantity) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Optional<Directory> findDirectory(String[] path) {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public Optional<Directory> findDirectory(String[] path) {
            return Optional.empty();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
