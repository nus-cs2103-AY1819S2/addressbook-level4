package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    /**
     * With filtering, there should be 2 lists. One is the storage that holds all the persons in the address book
     * and the other one will only hold the ones that will be displayed. Thus, another list is created as the main
     * storage and the list persons is used as the current filtered list holder.
     */

    private boolean isFilterExist;
    private boolean sortingExist;
    private UniquePersonList allPersonsStorage;
    private UniquePersonList persons;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /**
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        allPersonsStorage = new UniquePersonList();
        persons = new UniquePersonList();
        isFilterExist = false;
        sortingExist = false;
    }

    public AddressBook() {
    }

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

        this.allPersonsStorage.setPersons(persons);
        this.persons.setPersons(persons);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        allPersonsStorage.setPersons(newData.getAllPersonsStorageList());
        persons.setPersons(newData.getPersonList());
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

        if (!sortingExist) {
            allPersonsStorage.add(p);
        }

        if (isFilterExist && !sortingExist) {
            clearFilter();
            isFilterExist = false;
        } else {
            persons.add(p);
        }

        indicateModified();
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPersonWithFilter(Person p) {

        allPersonsStorage.add(p);
        persons.add(p);

        indicateModified();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */

    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        allPersonsStorage.setPerson(target, editedPerson);
        persons.setPerson(target, editedPerson);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {

        if (!sortingExist) {
            allPersonsStorage.remove(key);
        }

        persons.remove(key);
        indicateModified();
    }

    /**
     * It removes all the persons in the list
     */
    public void removeAllPerson() {
        List<Person> listToRemove = new ArrayList();
        for (Person person : persons) {
            listToRemove.add(person);
        }
        allPersonsStorage.removeAll(listToRemove);

        persons.removeAll(listToRemove);

        indicateModified();
    }

    /**
     * Makes the filtering process according to and command
     */
    public void filterAnd(String name, String phone, String email, String address, String[] skillList,
                          String[] posList, String gpa, String education) {

        isFilterExist = true;
        List<Person> listToRemove = new ArrayList();

        for (Person person : persons) {

            boolean ifExcluded = false;

            if (name != null && !person.getName().toString().toLowerCase().contains(name)) {
                ifExcluded = true;
            }

            if (phone != null && !person.getPhone().toString().toLowerCase().contains(phone)) {
                ifExcluded = true;
            }

            if (email != null && !person.getEmail().toString().toLowerCase().contains(email)) {
                ifExcluded = true;
            }

            if (address != null && !person.getAddress().toString().toLowerCase().contains(address)) {
                ifExcluded = true;
            }

            if (skillList != null) {
                for (String skill : skillList) {
                    if (!person.isTagExist(skill)) {
                        ifExcluded = true;
                        break;
                    }
                }
            }

            if (posList != null) {
                for (String pos : posList) {
                    if (!person.isTagExist(pos)) {
                        ifExcluded = true;
                        break;
                    }
                }
            }

            if (education != null && !person.getEducation().toString().toLowerCase().contains(education)) {
                ifExcluded = true;
            }

            if (gpa != null && Float.parseFloat(gpa) > Float.parseFloat(person.getGpa().toString())) {
                ifExcluded = true;
            }

            if (ifExcluded) {
                listToRemove.add(person);
            }
        }

        persons.removeAll(listToRemove);
        indicateModified();
    }

    /**
     * Makes the filtering process according to or command
     */
    public void filterOr(String name, String phone, String email, String address, String[] skillList,
                         String[] posList, String gpa, String education) {

        isFilterExist = true;
        List<Person> listToRemove = new ArrayList();

        for (Person person : persons) {

            boolean ifIncluded = false;

            if (name != null && person.getName().toString().toLowerCase().contains(name)) {
                ifIncluded = true;
            }

            if (phone != null && person.getPhone().toString().toLowerCase().contains(phone)) {
                ifIncluded = true;
            }

            if (email != null && person.getEmail().toString().toLowerCase().contains(email)) {
                ifIncluded = true;
            }

            if (address != null && person.getAddress().toString().toLowerCase().contains(address)) {
                ifIncluded = true;
            }

            if (skillList != null) {
                for (String skill : skillList) {
                    if (person.isTagExist(skill)) {
                        ifIncluded = true;
                        break;
                    }
                }
            }

            if (posList != null) {
                for (String pos : posList) {
                    if (person.isTagExist(pos)) {
                        ifIncluded = true;
                        break;
                    }
                }
            }

            if (education != null && person.getEducation().toString().toLowerCase().contains(education)) {
                ifIncluded = true;
            }

            if (gpa != null) {
                System.out.println("Gpa will be printed!");
                System.out.println(Float.parseFloat(gpa));
                System.out.println(Float.parseFloat(person.getGpa().toString()));
            }

            if (gpa != null && Float.parseFloat(gpa) <= Float.parseFloat(person.getGpa().toString())) {
                ifIncluded = true;
            }

            if (!ifIncluded) {
                listToRemove.add(person);
            }
        }

        persons.removeAll(listToRemove);
        indicateModified();
    }

    /**
     * Clears the filter
     */
    public void clearFilter() {

        if (isFilterExist) {
            for (Person person : allPersonsStorage) {
                if (!persons.contains(person)) {
                    persons.add(person);
                }
            }

            indicateModified();
        }

        isFilterExist = false;
    }

    /**
     * gets filter info
     */
    public boolean getFilterInfo() {
        return isFilterExist;
    }

    /**
     * sets filter info
     */
    public void setFilterInfo(boolean status) {
        isFilterExist = status;
    }

    /**
     * gets sort info
     */
    public boolean getSortInfo() {
        return sortingExist;
    }

    /**
     * sets sort info
     */
    public void setSortInfo(boolean status) {
        sortingExist = status;
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
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getAllPersonsStorageList() {
        return allPersonsStorage.asUnmodifiableObservableList();
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
