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
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.medicine.Medicine;
import seedu.address.testutil.MedicineBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_medicineAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMedicineAdded modelStub = new ModelStubAcceptingMedicineAdded();
        Medicine validMedicine = new MedicineBuilder().build();

        CommandResult commandResult = new AddCommand(validMedicine).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validMedicine), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMedicine), modelStub.medicinesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateMedicine_throwsCommandException() throws Exception {
        Medicine validMedicine = new MedicineBuilder().build();
        AddCommand addCommand = new AddCommand(validMedicine);
        ModelStub modelStub = new ModelStubWithMedicine(validMedicine);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_MEDICINE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Medicine paracetamol = new MedicineBuilder().withName("Paracetamol").build();
        Medicine gabapentin = new MedicineBuilder().withName("Gabapentin").build();
        AddCommand addParactamolCommand = new AddCommand(paracetamol);
        AddCommand addGabapentinCommand = new AddCommand(gabapentin);

        // same object -> returns true
        assertTrue(addParactamolCommand.equals(addParactamolCommand));

        // same values -> returns true
        AddCommand addParacetamolCommandCopy = new AddCommand(paracetamol);
        assertTrue(addParactamolCommand.equals(addParacetamolCommandCopy));

        // different types -> returns false
        assertFalse(addParactamolCommand.equals(1));

        // null -> returns false
        assertFalse(addParactamolCommand.equals(null));

        // different medicine -> returns false
        assertFalse(addParactamolCommand.equals(addGabapentinCommand));
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
        public Path getInventoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventoryFilePath(Path inventoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventory(ReadOnlyInventory newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInventory getInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMedicine(Medicine target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMedicine(Medicine target, Medicine editedMedicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WarningPanelPredicateAccessor getWarningPanelPredicateAccessor() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Medicine> getFilteredMedicineList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Medicine> getExpiringMedicinesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Medicine> getLowQuantityMedicinesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpiringMedicineList(Predicate<Medicine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLowStockMedicineList(Predicate<Medicine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Medicine> selectedMedicineProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Medicine getSelectedMedicine() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single medicine.
     */
    private class ModelStubWithMedicine extends ModelStub {
        private final Medicine medicine;

        ModelStubWithMedicine(Medicine medicine) {
            requireNonNull(medicine);
            this.medicine = medicine;
        }

        @Override
        public boolean hasMedicine(Medicine medicine) {
            requireNonNull(medicine);
            return this.medicine.isSameMedicine(medicine);
        }
    }

    /**
     * A Model stub that always accept the medicine being added.
     */
    private class ModelStubAcceptingMedicineAdded extends ModelStub {
        final ArrayList<Medicine> medicinesAdded = new ArrayList<>();

        @Override
        public boolean hasMedicine(Medicine medicine) {
            requireNonNull(medicine);
            return medicinesAdded.stream().anyMatch(medicine::isSameMedicine);
        }

        @Override
        public void addMedicine(Medicine medicine) {
            requireNonNull(medicine);
            medicinesAdded.add(medicine);
        }

        @Override
        public void commitInventory() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyInventory getInventory() {
            return new Inventory();
        }
    }

}
