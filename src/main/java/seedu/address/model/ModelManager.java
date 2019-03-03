package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.exceptions.MenuItemNotFoundException;
import seedu.address.model.order.OrderItem;
import seedu.address.model.person.Person; // TODO: remove once the other components stop relying on person methods
import seedu.address.model.person.exceptions.PersonNotFoundException; // TODO: remove

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RestOrRant restOrRant;
    private final UserPrefs userPrefs;
    private final FilteredList<OrderItem> filteredOrderItems;
    private final FilteredList<MenuItem> filteredMenuItems;
    private final SimpleObjectProperty<OrderItem> selectedOrderItem = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<MenuItem> selectedMenuItem = new SimpleObjectProperty<>();
    private final FilteredList<Person> filteredPersons; // TODO: remove
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>(); // TODO: remove

    /**
     * Initializes a ModelManager with the given restOrRant and userPrefs.
     */
    public ModelManager(ReadOnlyRestOrRant restOrRant, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(restOrRant, userPrefs);

        logger.fine("Initializing with RestOrRant: " + restOrRant + " and user prefs " + userPrefs);

        this.restOrRant = new RestOrRant(restOrRant);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOrderItems = new FilteredList<>(this.restOrRant.getOrderItemList());
        filteredMenuItems = new FilteredList<>(this.restOrRant.getMenuItemList());
        filteredMenuItems.addListener(this::ensureSelectedMenuItemIsValid);

        filteredPersons = new FilteredList<>(this.restOrRant.getPersonList()); // TODO: remove
        filteredPersons.addListener(this::ensureSelectedPersonIsValid); // TODO: remove
    }

    public ModelManager() {
        this(new RestOrRant(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getMenuFilePath() {
        return userPrefs.getMenuFilePath();
    }
    public Path getRestOrRantFilePath() {
        return userPrefs.getRestOrRantFilePath();
    } // TODO: remove

    @Override
    public void setMenuFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        userPrefs.setMenuFilePath(menuFilePath);
    }
    // TODO: remove
    public void setRestOrRantFilePath(Path restOrRantFilePath) {
        requireNonNull(restOrRantFilePath);
        userPrefs.setRestOrRantFilePath(restOrRantFilePath);
    }

    //=========== RestOrRant ================================================================================

    @Override
    public void setRestOrRant(ReadOnlyRestOrRant restOrRant) {
        this.restOrRant.resetData(restOrRant);
    }

    @Override
    public ReadOnlyRestOrRant getRestOrRant() {
        return restOrRant;
    }

    @Override
    public void updateRestOrRant() {
        restOrRant.indicateModified();
    }

    @Override
    public boolean hasMenuItem(MenuItem menuItem) {
        requireNonNull(menuItem);
        return restOrRant.hasMenuItem(menuItem);
    }
    // TODO: remove
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return restOrRant.hasPerson(person);
    }

    @Override
    public void deleteMenuItem(MenuItem target) {
        restOrRant.removeMenuItem(target);
    }
    // TODO: remove
    public void deletePerson(Person target) {
        restOrRant.removePerson(target);
    }

    @Override
    public void addMenuItem(MenuItem menuItem) {
        restOrRant.addMenuItem(menuItem);
        updateFilteredMenuItemList(PREDICATE_SHOW_ALL_MENU_ITEMS);
    }
    // TODO: remove
    public void addPerson(Person person) {
        restOrRant.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setMenuItem(MenuItem target, MenuItem editedItem) {
        requireAllNonNull(target, editedItem);
        
        restOrRant.setMenuItem(target, editedItem);
    }
    // TODO: remove
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        restOrRant.setPerson(target, editedPerson);
    }

    //=========== Filtered MenuItem List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code MenuItem} backed by the internal list of
     * {@code restOrRant}
     */
    @Override
    public ObservableList<MenuItem> getFilteredMenuItemList() {
        return filteredMenuItems;
    }
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    } // TODO: remove

    @Override
    public void updateFilteredMenuItemList(Predicate<MenuItem> predicate) {
        requireNonNull(predicate);
        filteredMenuItems.setPredicate(predicate);
    }
    // TODO: remove
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Selected menu item ===========================================================================

    @Override
    public ReadOnlyProperty<MenuItem> selectedMenuItemProperty() {
        return selectedMenuItem;
    }
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    } // TODO: remove

    @Override
    public MenuItem getSelectedMenuItem() {
        return selectedMenuItem.getValue();
    }
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    } // TODO: remove

    @Override
    public void setSelectedMenuItem(MenuItem menuItem) {
        if (menuItem != null && !filteredMenuItems.contains(menuItem)) {
            throw new MenuItemNotFoundException();
        }
        selectedMenuItem.setValue(menuItem);
    }
    // TODO: remove
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    /**
     * Ensures {@code selectedMenuItem} is a valid menu item in {@code filteredMenuItems}.
     */
    private void ensureSelectedMenuItemIsValid(ListChangeListener.Change<? extends  MenuItem> change) {
        while (change.next()) {
            if (selectedMenuItem.getValue() == null) {
                // null is always a valid selected menu item, so we do not need to check that it is valid anymore.
                return;
            }
            
            boolean wasSelectedMenuItemReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedMenuItem.getValue());
            if (wasSelectedMenuItemReplaced) {
                // Update selectedMenuItem to its new value.
                int index = change.getRemoved().indexOf(selectedMenuItem.getValue());
                selectedMenuItem.setValue(change.getAddedSubList().get(index));
                continue;
            }
            
            boolean wasSelectedMenuItemRemoved = change.getRemoved().stream()
                    .anyMatch(removedMenuItem -> selectedMenuItem.getValue().isSameMenuItem(removedMenuItem));
            if (wasSelectedMenuItemRemoved) {
                // Select the menu item that came before it in the list,
                // or clear the selection if there is no such menu item.
                selectedMenuItem.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }
    // TODO: remove
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedOrderItem to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return restOrRant.equals(other.restOrRant)
                && userPrefs.equals(other.userPrefs)
                && filteredOrderItems.equals(other.filteredOrderItems)
                && Objects.equals(selectedOrderItem.get(), other.selectedOrderItem.get())
                && filteredMenuItems.equals(other.filteredMenuItems)
                && Objects.equals(selectedMenuItem.get(), other.selectedMenuItem.get());
    }

    @Override
    public void changeMode() {
        restOrRant.changeMode();
    }
}
