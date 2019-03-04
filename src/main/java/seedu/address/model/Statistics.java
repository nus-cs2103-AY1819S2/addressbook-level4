package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.UniqueOrderItemList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

public class Statistics implements ReadOnlyStatistics {

    private final UniqueOrderItemList orderItems;
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
    }

    public Statistics() {}

    /**
     * Creates an RestOrRant using the Persons in the {@code toBeCopied}
     */
    public Statistics(ReadOnlyStatistics toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code RestOrRant} with {@code newData}.
     */
    public void resetData(ReadOnlyStatistics newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations
    

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

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
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
    public int hashCode() {
        return orderItems.hashCode();
    }
}
