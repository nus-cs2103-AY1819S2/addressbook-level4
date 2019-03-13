package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Job implements ReadOnlyJob {

    private String jobName;
    private ArrayList<UniquePersonList> persons_list;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    public Job() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public Job(String Name) {
        this();
        jobName = Name;
        persons_list = new ArrayList<>(4);
        for(int i = 0; i < 4; i++) {
            persons_list.add(new UniquePersonList());
        }
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons, int listNumber) {
        this.persons_list.get(listNumber).setPersons(persons);
        indicateModified();  
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the job.
     */
    public boolean hasPerson(Person person, int listNumber) {
        requireNonNull(person);
        return persons_list.get(listNumber).contains(person);
    }

    /**
     * Adds a person to the Job.
     * The person must not already exist in the Job.
     */
    public void addPerson(Person p, int listNumber) {
        persons_list.get(listNumber).add(p);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code Job}.
     * {@code key} must exist in the Job.
     */
    public void removePerson(Person key, int listNumber) {
        persons_list.get(listNumber).remove(key);
        indicateModified();
    }

    /**
     * Moves {@code key} from this {@code persons_list} to another (@code persons_list)
     * {@code key} must exist in the persons_list.
     */
    public void movePerson(Person key, int fromList, int toList) {
        removePerson(key, fromList);
        addPerson(key, toList);
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
    public ObservableList<Person> getPersonList(int listNumber) {
        return persons_list.get(listNumber).asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Job // instanceof handles nulls
                && persons_list.equals(((Job) other).persons_list));
    }

    @Override
    public int hashCode(int listNumber) {
        return persons_list.get(listNumber).hashCode();
    }
}

