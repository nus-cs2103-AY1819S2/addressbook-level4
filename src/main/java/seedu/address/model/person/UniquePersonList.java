package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A moduleTaken is considered unique by comparing using {@code ModuleTaken#isSamePerson(ModuleTaken)}.
 * As such, adding and updating of persons uses ModuleTaken#isSamePerson(ModuleTaken) for equality so as to ensure
 * that the moduleTaken being added or updated is unique in terms of identity in the UniquePersonList.
 * However, the removal of a moduleTaken uses ModuleTaken#equals(Object) so
 * as to ensure that the moduleTaken with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see ModuleTaken#isSamePerson(ModuleTaken)
 */
public class UniquePersonList implements Iterable<ModuleTaken> {

    private final ObservableList<ModuleTaken> internalList = FXCollections.observableArrayList();
    private final ObservableList<ModuleTaken> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent moduleTaken as the given argument.
     */
    public boolean contains(ModuleTaken toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a moduleTaken to the list.
     * The moduleTaken must not already exist in the list.
     */
    public void add(ModuleTaken toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the moduleTaken {@code target} in the list with {@code editedModuleTaken}.
     * {@code target} must exist in the list.
     * The moduleTaken identity of {@code editedModuleTaken} must not be the same as
     * another existing moduleTaken in the list.
     */
    public void setPerson(ModuleTaken target, ModuleTaken editedModuleTaken) {
        requireAllNonNull(target, editedModuleTaken);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedModuleTaken) && contains(editedModuleTaken)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedModuleTaken);
    }

    /**
     * Removes the equivalent moduleTaken from the list.
     * The moduleTaken must exist in the list.
     */
    public void remove(ModuleTaken toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code moduleTakens}.
     * {@code moduleTakens} must not contain duplicate moduleTakens.
     */
    public void setPersons(List<ModuleTaken> moduleTakens) {
        requireAllNonNull(moduleTakens);
        if (!personsAreUnique(moduleTakens)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(moduleTakens);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ModuleTaken> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ModuleTaken> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code moduleTakens} contains only unique moduleTakens.
     */
    private boolean personsAreUnique(List<ModuleTaken> moduleTakens) {
        for (int i = 0; i < moduleTakens.size() - 1; i++) {
            for (int j = i + 1; j < moduleTakens.size(); j++) {
                if (moduleTakens.get(i).isSamePerson(moduleTakens.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
