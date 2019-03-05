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
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Pdf;
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
        Pdf validPdf = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPdf).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPdf), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPdf), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Pdf validPdf = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPdf);
        ModelStub modelStub = new ModelStubWithPerson(validPdf);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Pdf alice = new PersonBuilder().withName("Alice").build();
        Pdf bob = new PersonBuilder().withName("Bob").build();
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

        // different pdf -> returns false
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
        public void addPerson(Pdf pdf) {
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
        public boolean hasPerson(Pdf pdf) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Pdf target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Pdf target, Pdf editedPdf) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Pdf> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Pdf> predicate) {
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
        public ReadOnlyProperty<Pdf> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Pdf getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Pdf pdf) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single pdf.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Pdf pdf;

        ModelStubWithPerson(Pdf pdf) {
            requireNonNull(pdf);
            this.pdf = pdf;
        }

        @Override
        public boolean hasPerson(Pdf pdf) {
            requireNonNull(pdf);
            return this.pdf.isSamePerson(pdf);
        }
    }

    /**
     * A Model stub that always accept the pdf being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Pdf> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Pdf pdf) {
            requireNonNull(pdf);
            return personsAdded.stream().anyMatch(pdf::isSamePerson);
        }

        @Override
        public void addPerson(Pdf pdf) {
            requireNonNull(pdf);
            personsAdded.add(pdf);
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
