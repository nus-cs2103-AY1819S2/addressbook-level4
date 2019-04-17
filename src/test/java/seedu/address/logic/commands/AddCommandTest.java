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
import seedu.address.model.FashionMatch;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFashionMatch;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.apparel.Apparel;
import seedu.address.testutil.ApparelBuilder;

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
        Apparel validApparel = new ApparelBuilder().build();

        CommandResult commandResult = new AddCommand(validApparel).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validApparel), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validApparel), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Apparel validApparel = new ApparelBuilder().build();
        AddCommand addCommand = new AddCommand(validApparel);
        ModelStub modelStub = new ModelStubWithPerson(validApparel);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_APPAREL);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Apparel alice = new ApparelBuilder().withName("Alice").build();
        Apparel bob = new ApparelBuilder().withName("Bob").build();
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

        // different apparel -> returns false
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
        public void addApparel(Apparel apparel) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyFashionMatch newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFashionMatch getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasApparel(Apparel apparel) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteApparel(Apparel target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Apparel target, Apparel editedApparel) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Apparel> getFilteredApparelList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredApparelList(Predicate<Apparel> predicate) {
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
        public ReadOnlyProperty<Apparel> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Apparel getSelectedApparel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Apparel apparel) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFavColor() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFavApparel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getTotalColor() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getLeastFavApparel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getCleanOrDirty() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single apparel.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Apparel apparel;

        ModelStubWithPerson(Apparel apparel) {
            requireNonNull(apparel);
            this.apparel = apparel;
        }

        @Override
        public boolean hasApparel(Apparel apparel) {
            requireNonNull(apparel);
            return this.apparel.isSameApparel(apparel);
        }
    }

    /**
     * A Model stub that always accept the apparel being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Apparel> personsAdded = new ArrayList<>();

        @Override
        public boolean hasApparel(Apparel apparel) {
            requireNonNull(apparel);
            return personsAdded.stream().anyMatch(apparel::isSameApparel);
        }

        @Override
        public void addApparel(Apparel apparel) {
            requireNonNull(apparel);
            personsAdded.add(apparel);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyFashionMatch getAddressBook() {
            return new FashionMatch();
        }
    }

}
