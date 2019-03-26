package seedu.equipment.logic.commands;

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
import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.ReadOnlyUserPrefs;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.testutil.WorkListBuilder;

public class AddWorkListCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullWorkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddWorkListCommand(null);
    }

    @Test
    public void execute_workListAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWorkListAdded modelStub = new ModelStubAcceptingWorkListAdded();
        WorkList validWorkList = new WorkListBuilder().build();

        CommandResult commandResult = new AddWorkListCommand(validWorkList).execute(modelStub, commandHistory);

        assertEquals(String.format(AddWorkListCommand.MESSAGE_SUCCESS, validWorkList),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWorkList), modelStub.workListAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateWorkList_throwsCommandException() throws Exception {
        WorkList validWorkList = new WorkListBuilder().build();
        AddWorkListCommand addWorkListCommand = new AddWorkListCommand(validWorkList);
        ModelStub modelStub = new ModelStubWithWorkList(validWorkList);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddWorkListCommand.MESSAGE_DUPLICATE_EQUIPMENT);
        addWorkListCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        WorkList lista = new WorkListBuilder().withAssignee("Alice").build();
        WorkList listb = new WorkListBuilder().withAssignee("Bob").build();
        AddWorkListCommand addAliceCommand = new AddWorkListCommand(lista);
        AddWorkListCommand addBobCommand = new AddWorkListCommand(listb);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddWorkListCommand addAliceCommandCopy = new AddWorkListCommand(lista);
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
        public void setEquipmentManagerFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWorkList(WorkList workList) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEquipment(Equipment equipment) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEquipmentManager(ReadOnlyEquipmentManager newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEquipmentManager getEquipmentManager() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWorkList(WorkList workList) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEquipment(Equipment equipment) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEquipment(Equipment target) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEquipment(Equipment target, Equipment editedEquipment) {
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
        public void updateFilteredPersonList(Predicate<Equipment> predicate) {
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
        public Equipment getSelectedEquipment() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedEquipment(Equipment equipment) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single equipment.
     */
    private class ModelStubWithWorkList extends ModelStub {
        private final WorkList workList;

        ModelStubWithWorkList(WorkList workList) {
            requireNonNull(workList);
            this.workList = workList;
        }

        @Override
        public boolean hasWorkList(WorkList workList) {
            requireNonNull(workList);
            return this.workList.isSameWorkList(workList);
        }
    }

    /**
     * A Model stub that always accept the WorkList being added.
     */
    private class ModelStubAcceptingWorkListAdded extends ModelStub {
        final ArrayList<WorkList> workListAdded = new ArrayList<>();

        @Override
        public boolean hasWorkList(WorkList workList) {
            requireNonNull(workList);
            return workListAdded.stream().anyMatch(workList::isSameWorkList);
        }

        @Override
        public void addWorkList(WorkList workList) {
            requireNonNull(workList);
            workListAdded.add(workList);
        }

        @Override
        public void commitEquipmentManager() {
            // called by {@code AddEquipmentCommand#execute()}
        }

        @Override
        public ReadOnlyEquipmentManager getEquipmentManager() {
            return new EquipmentManager();
        }
    }

}
