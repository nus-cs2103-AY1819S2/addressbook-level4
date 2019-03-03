package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.UniqueOrderItemList;
import seedu.address.model.menu.UniqueMenuItemList;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.person.Person; // TODO: remove once the other components stop relying on person methods
import seedu.address.model.person.UniquePersonList; // TODO: remove once the other components stop relying on person methods

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class RestOrRant implements ReadOnlyRestOrRant {

    private final UniqueOrderItemList orderItems;
    // TODO: feel free to add more lists for menu items and tables
    private final UniqueMenuItemList menuItems;
    private final UniquePersonList persons; // TODO: remove once the other components stop relying on person methods
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        orderItems = new UniqueOrderItemList();
        menuItems = new UniqueMenuItemList();
        persons = new UniquePersonList(); // TODO: remove once the other components stop relying on person methods
    }

    public RestOrRant() {}

    /**
     * Creates an RestOrRant using the Persons in the {@code toBeCopied}
     */
    public RestOrRant(ReadOnlyRestOrRant toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the menu list with {@code menuItems}.
     * {@code menuItems} must not contain duplicate persons.
     */
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems.setMenuItems(menuItems);
        indicateModified();
    }
//    public void setPersons(List<Person> persons) {
//        this.persons.setPersons(persons);
//        indicateModified();
//    }

    /**
     * Resets the existing data of this {@code RestOrRant} with {@code newData}.
     */
    public void resetData(ReadOnlyRestOrRant newData) {
        requireNonNull(newData);

        setMenuItems(newData.getMenuItemList());
    }

    //// person-level operations

    /**
     * Returns true if a menu item with the same identity as {@code menuItem} exists in the address book.
     */
    public boolean hasMenuItem (MenuItem menuItem) {
        requireNonNull(menuItem);
        return menuItems.contains(menuItem);
    }
    // TODO: remove
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a menu item to the menu.
     * The menu item must not already exist in the address book.
     */
    public void addMenuItem (MenuItem item) {
        menuItems.add(item);
        indicateModified();
    }
    // TODO: remove
    public void addPerson(Person p) {
        persons.add(p);
        indicateModified();
    }
    
    /**
     * Replaces the given menu item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the address book.
     * The item identity of {@code editedItem} must not be the same as another existing menu item in the address book.
     */
    public void setMenuItem(MenuItem target, MenuItem editedItem) {
        requireNonNull(editedItem);
        
        menuItems.setMenuItem(target, editedItem);
    }
    // TODO: remove
    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestOrRant}.
     * {@code key} must exist in the menu.
     */
    public void removeMenuItem(MenuItem key) {
        menuItems.remove(key);
        indicateModified();
    }
    // TODO: remove
    /**
     * Removes {@code key} from this {@code RestOrRant}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        indicateModified();
    }

    public void changeMode() {
        indicateModified();
    }
    /**
     * Given the menu item's {@code String code}, returns the MenuItem with the corresponding code.
     */
    @Override
    public MenuItem checkItemExists(String code) {
        Iterator<MenuItem> iterator = menuItems.iterator();
        while (iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            if (menuItem.getCode().itemCode.equals(code)) {
                return menuItem;
            }
        }
        return null;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the restOrRant has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return orderItems.asUnmodifiableObservableList().size() + " order items";
        // TODO: refine later
    }

    @Override
    public ObservableList<OrderItem> getOrderItemList() {
        return orderItems.asUnmodifiableObservableList();
    }
    
    @Override
    public ObservableList<MenuItem> getMenuItemList() {
        return menuItems.asUnmodifiableObservableList();
    }

    // TODO: remove once the other components stop relying on person methods
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestOrRant // instanceof handles nulls
                && orderItems.equals(((RestOrRant) other).orderItems))
                && menuItems.equals(((RestOrRant) other).menuItems);
    }

    @Override
    public int hashCode() {
        return orderItems.hashCode();
    }
}
