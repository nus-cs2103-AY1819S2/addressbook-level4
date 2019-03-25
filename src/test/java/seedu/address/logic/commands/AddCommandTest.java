package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.address.model.Cap;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.course.Course;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;
import seedu.address.testutil.ModuleTakenBuilder;

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
        ModuleTaken validModuleTaken = new ModuleTakenBuilder().build();

        CommandResult commandResult = new AddCommand(validModuleTaken).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validModuleTaken), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModuleTaken), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModuleTaken validModuleTaken = new ModuleTakenBuilder().build();
        AddCommand addCommand = new AddCommand(validModuleTaken);
        ModelStub modelStub = new ModelStubWithPerson(validModuleTaken);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        ModuleTaken cs2103t = new ModuleTakenBuilder().withModuleInfoCode("CS2103T").build();
        ModuleTaken cs1010 = new ModuleTakenBuilder().withModuleInfoCode("CS1010").build();
        AddCommand addCS2103T = new AddCommand(cs2103t);
        AddCommand addCS1010 = new AddCommand(cs1010);

        // same object -> returns true
        assertTrue(addCS2103T.equals(addCS2103T));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(cs2103t);
        assertTrue(addCS2103T.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addCS2103T.equals(1));

        // null -> returns false
        assertFalse(addCS2103T.equals(null));

        // different moduleTaken -> returns false
        assertFalse(addCS2103T.equals(addCS1010));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Course getCourse() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Semester getCurrentSemester() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Cap computeCumulativeCap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Cap computeExpectedMinimumCap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Cap computeExpectedMaximumCap() {
            throw new AssertionError("This method should not be called.");
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
        public void addPerson(ModuleTaken moduleTaken) {
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
        public boolean hasPerson(ModuleTaken moduleTaken) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(ModuleTaken target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(ModuleTaken target, ModuleTaken editedModuleTaken) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ModuleTaken> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<ModuleTaken> predicate) {
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
        public ReadOnlyProperty<ModuleTaken> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ModuleTaken getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(ModuleTaken moduleTaken) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ModuleInfo> getDisplayList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateDisplayList(Predicate<ModuleInfo> predicate) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<ModuleInfoCode> getRecModuleListSorted() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateRecModuleList() {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single moduleTaken.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final ModuleTaken moduleTaken;

        ModelStubWithPerson(ModuleTaken moduleTaken) {
            requireNonNull(moduleTaken);
            this.moduleTaken = moduleTaken;
        }

        @Override
        public boolean hasPerson(ModuleTaken moduleTaken) {
            requireNonNull(moduleTaken);
            return this.moduleTaken.isSamePerson(moduleTaken);
        }
    }

    /**
     * A Model stub that always accept the moduleTaken being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<ModuleTaken> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(ModuleTaken moduleTaken) {
            requireNonNull(moduleTaken);
            return personsAdded.stream().anyMatch(moduleTaken::isSamePerson);
        }

        @Override
        public void addPerson(ModuleTaken moduleTaken) {
            requireNonNull(moduleTaken);
            personsAdded.add(moduleTaken);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
