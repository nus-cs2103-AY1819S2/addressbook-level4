package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.ReadOnlyUserPrefs;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.testutil.EquipmentBuilder;

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
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Equipment validEquipment = new EquipmentBuilder().build();
        AddCommand addCommand = new AddCommand(validEquipment);
        ModelStub modelStub = new ModelStubWithPerson(validEquipment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_EQUIPMENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Equipment alice = new EquipmentBuilder().withName("Alice").build();
        Equipment bob = new EquipmentBuilder().withName("Bob").build();
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

        // different equipment -> returns false
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
        public void resetData(ReadOnlyEquipmentManager newData) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getEquipmentManagerFilePath() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEquipmentManagerFilePath(Path equipmentManagerFilePath) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEquipment(Equipment equipment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Name equipment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWorkList(WorkList workList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void putEquipment(WorkListId id, SerialNumber sr) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeEquipment(WorkListId id, SerialNumber sr) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEquipmentManager(ReadOnlyEquipmentManager equipmentManager) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEquipmentManager getEquipmentManager() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEquipment(Equipment equipment) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEquipmentWithSerialNumber(SerialNumber serialNumber) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEquipmentInWorkList(WorkListId workListId, SerialNumber serialNumber) {

            throw new AssertionError("This method should not be called.");
        }

        public boolean hasClient(Name equipment) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWorkList(WorkList workList) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEquipment(Equipment target) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWorkList(WorkList target) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEquipment(Equipment target, Equipment editedEquipment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Name target, Name editedEquipment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEquipment(Equipment target, Equipment editedEquipment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Equipment> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Name> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Equipment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Name> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<WorkList> getFilteredWorkListList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWorkListList (Predicate<WorkList> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredEquipmentList(Comparator<Equipment> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoEquipmentManager() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoEquipmentManager() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoEquipmentManager() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoEquipmentManager() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEquipmentManager() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Equipment> selectedEquipmentProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Name> selectedClientProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<WorkList> selectedWorkListProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Equipment getSelectedEquipment() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Name getSelectedClient() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WorkList getSelectedWorkList() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedEquipment(Equipment equipment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unsetSelectedEquipment() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedWorkList(WorkList workList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedClient(Name name) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single equipment.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Equipment equipment;

        ModelStubWithPerson(Equipment equipment) {
            requireNonNull(equipment);
            this.equipment = equipment;
        }

        @Override
        public boolean hasEquipment(Equipment equipment) {
            requireNonNull(equipment);
            return this.equipment.isSameEquipment(equipment);
        }
    }

    /**
     * A Model stub that always accept the equipment being added.
     */
    private class ModelStubAcceptingEquipmentAdded extends ModelStub {
        final ArrayList<Equipment> equipmentAdded = new ArrayList<>();

        @Override
        public boolean hasEquipment(Equipment equipment) {
            requireNonNull(equipment);
            return equipmentAdded.stream().anyMatch(equipment::isSameEquipment);
        }

        @Override
        public void addEquipment(Equipment equipment) {
            requireNonNull(equipment);
            equipmentAdded.add(equipment);
        }

        @Override
        public void commitEquipmentManager() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyEquipmentManager getEquipmentManager() {
            return new EquipmentManager();
        }
    }

}
