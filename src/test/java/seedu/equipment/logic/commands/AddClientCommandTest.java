package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

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
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.testutil.EquipmentBuilder;

public class AddClientCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullClient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddClientCommand(null);
    }


    @Test
    public void execute_clientAcceptedByModel_addSuccessful() throws Exception {
        AddClientCommandTest.ModelStubAcceptingClientAdded modelStub =
                new AddClientCommandTest.ModelStubAcceptingClientAdded();
        Equipment validEquipment = new EquipmentBuilder().build();

        Name validName = new Name(validEquipment.getName().name);

        CommandResult commandResult = new AddClientCommand(validName).execute(modelStub, commandHistory);

        assertEquals(String.format(AddClientCommand.MESSAGE_SUCCESS, validName),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validName), modelStub.clientAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateName_throwsCommandException() throws Exception {
        Equipment validEquipment = new EquipmentBuilder().build();
        AddClientCommand addClientCommand = new AddClientCommand(validEquipment.getName());
        AddClientCommandTest.ModelStubWithClient modelStub =
                new AddClientCommandTest.ModelStubWithClient(validEquipment.getName());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_CLIENT);
        addClientCommand.execute(modelStub, commandHistory);
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
        public void setEquipmentManager(ReadOnlyEquipmentManager newData) {
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
        public ReadOnlyProperty<WorkList> selectedWorkListProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Equipment getSelectedEquipment() {

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
        public void setSelectedWorkList(WorkList workList) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single equipment.
     */
    private class ModelStubWithClient extends AddClientCommandTest.ModelStub {
        private final Name equipment;

        ModelStubWithClient(Name equipment) {
            requireNonNull(equipment);
            this.equipment = equipment;
        }

        @Override
        public boolean hasClient(Name equipment) {
            requireNonNull(equipment);
            return this.equipment.isSameName(equipment);
        }
    }

    /**
     * A Model stub that always accept the equipment being added.
     */
    private class ModelStubAcceptingClientAdded extends AddClientCommandTest.ModelStub {
        final ArrayList<Name> clientAdded = new ArrayList<>();

        @Override
        public boolean hasClient(Name name) {
            requireNonNull(name);
            return clientAdded.stream().anyMatch(name::isSameName);
        }

        @Override
        public void addClient(Name name) {
            requireNonNull(name);
            clientAdded.add(name);
        }

        @Override
        public void commitEquipmentManager() {
            // called by {@code AddClientCommand#execute()}
        }

        @Override
        public ReadOnlyEquipmentManager getEquipmentManager() {
            return new EquipmentManager();
        }
    }
}
