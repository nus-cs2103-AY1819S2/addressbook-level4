package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.HealthWorker;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueHealthWorkerList;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueHealthWorkerList healthWorkers;
    private final UniquePersonList persons;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        healthWorkers = new UniqueHealthWorkerList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        indicateModified();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        indicateModified();
    }

    // ==== Added methods to support operations on UniqueHealthWorkerList ====
    // @author: Lookaz

    /**
     * Returns true if a HealthWorker with the same identity as {@code worker}
     * exists in the address book.
     */
    public boolean hasHealthWorker(HealthWorker worker) {
        requireNonNull(worker);
        return this.healthWorkers.contains(worker);
    }

    /**
     * Adds a HealthWorker to the address book.
     * The HealthWorker must not already exist in the address book.
     */
    public void addHealthWorker(HealthWorker worker) {
        this.healthWorkers.add(worker);
        indicateModified();
    }

    /**
     * Replaces the given HealthWorker {@code target} in the list with {@code
     * edited}. {@code target} must exist in the address book.
     * The identity of {@code edited} must not be the same as
     * another existing HealthWorker in the address book.
     */
    public void setHealthWorker(HealthWorker target, HealthWorker edited) {
        requireNonNull(edited);

        this.healthWorkers.setHealthWorker(target, edited);
        indicateModified();
    }

    /**
     * Removes HealthWorker {@code worker} from this {@code AddressBook}.
     * HealthWorker {@code worker} must exist in the address book.
     */
    public void removeHealthWorker(HealthWorker worker) {
        this.healthWorkers.remove(worker);
        indicateModified();
    }

    // ======================================================================

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
    /**
     * Returns an unmodifiable view of the healthworkers list.
     * This list will not contain any duplicate healthworkers
     */
    public ObservableList<HealthWorker> getHealthWorkerList() {
        return healthWorkers.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
