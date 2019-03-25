package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.UniquePersonList;

/**
 * Todo: modify Address book to store module taken
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

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
     * Replaces the contents of the moduleTaken list with {@code moduleTakens}.
     * {@code moduleTakens} must not contain duplicate moduleTakens.
     */
    public void setPersons(List<ModuleTaken> moduleTakens) {
        this.persons.setPersons(moduleTakens);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// moduleTaken-level operations

    /**
     * Returns true if a moduleTaken with the same identity as {@code moduleTaken} exists in the address book.
     */
    public boolean hasPerson(ModuleTaken moduleTaken) {
        requireNonNull(moduleTaken);
        return persons.contains(moduleTaken);
    }

    /**
     * Adds a moduleTaken to the address book.
     * The moduleTaken must not already exist in the address book.
     */
    public void addPerson(ModuleTaken p) {
        persons.add(p);
        indicateModified();
    }

    /**
     * Replaces the given moduleTaken {@code target} in the list with {@code editedModuleTaken}.
     * {@code target} must exist in the address book.
     * The moduleTaken identity of {@code editedModuleTaken} must not be the same as another
     * existing moduleTaken in the address book.
     */
    public void setPerson(ModuleTaken target, ModuleTaken editedModuleTaken) {
        requireNonNull(editedModuleTaken);

        persons.setPerson(target, editedModuleTaken);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(ModuleTaken key) {
        persons.remove(key);
        indicateModified();
    }

    public List<ModuleInfoCode> getPassedModuleList() {
        List<ModuleInfoCode> codeList = new ArrayList<>();

        for (ModuleTaken module : getPersonList()) {
            if (module.isPassed()) {
                codeList.add(new ModuleInfoCode(module.getModuleInfo().toString())); // temporary
            }
        }

        return codeList;
    }

    /**
     * Returns true if the module list contains a passed module corresponding to the given module code string.
     */
    public boolean hasPassedModule(String code) {
        requireNonNull(code);
        for (ModuleTaken module : getPersonList()) {
            if (module.getModuleInfo().toString().equals(code)
                    && module.isPassed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the module list contains a planned (unfinished) module
     * corresponding to the given module code string.
     */
    public boolean hasPlannedModule(String code) {
        requireNonNull(code);
        for (ModuleTaken module : getPersonList()) {
            if (module.getModuleInfo().toString().equals(code)
                    && !module.isFinished()) {
                return true;
            }
        }
        return false;
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
    public ObservableList<ModuleTaken> getPersonList() {
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
