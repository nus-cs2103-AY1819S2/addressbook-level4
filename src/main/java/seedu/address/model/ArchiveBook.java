package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the archive-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ArchiveBook implements ReadOnlyArchiveBook {

    private final UniquePersonList archivedPersons;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        archivedPersons = new UniquePersonList();
    }

    public ArchiveBook() {}

    /**
     * Creates an ArchiveBook using the Persons in the {@code toBeCopied}
     */
    public ArchiveBook(ReadOnlyArchiveBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code archivedPersons}.
     * {@code archivedPersons} must not contain duplicate archivedPersons.
     */
    public void setArchivedPersons(List<Person> archivedPersons) {
        this.archivedPersons.setPersons(archivedPersons);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code ArchiveBook} with {@code newData}.
     */
    public void resetData(ReadOnlyArchiveBook newData) {
        requireNonNull(newData);

        setArchivedPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the archive book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return archivedPersons.contains(person);
    }

    /**
     * Adds a person to the archive book.
     * The person must not already exist in the archive book.
     */
    public void addPerson(Person p) {
        archivedPersons.add(p);
        indicateModified();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the archive book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the archive book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        archivedPersons.setPerson(target, editedPerson);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code ArchiveBook}.
     * {@code key} must exist in the archive book.
     */
    public void removePerson(Person key) {
        archivedPersons.remove(key);
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
     * Notifies listeners that the archive book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return archivedPersons.asUnmodifiableObservableList().size() + " archivedPersons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return archivedPersons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveBook // instanceof handles nulls
                && archivedPersons.equals(((ArchiveBook) other).archivedPersons));
    }

    @Override
    public int hashCode() {
        return archivedPersons.hashCode();
    }
}

