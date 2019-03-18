package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A mapping of unique NRIC to person that enforces uniqueness between its elements and does not allow nulls.
 * A person is solely defined as unique by it's NRIC, regardless of update of other fields of a person.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueNricMap {

    private final ObservableMap<Nric, Person> internalMap = FXCollections.observableHashMap();
    private final ObservableMap<Nric, Person> internalUnmodifiableMap =
            FXCollections.unmodifiableObservableMap(internalMap);

    /**
     * Returns true if the list contains an equivalent NRIC as the given argument.
     */
    public boolean contains(Nric toCheck) {
        requireNonNull(toCheck);
        return internalMap.containsKey(toCheck);
    }

    /**
     * Adds a NRIC and corresponding person to the list.
     * The NRIC must not already exist in the list.
     */
    public void add(Nric nric, Person person) {
        requireNonNull(nric);
        requireNonNull(person);
        if (contains(nric)) {
            throw new DuplicatePersonException();
        }
        internalMap.put(nric, person);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list
     * Both {@code target} and {@code editedPerson} must have same NRIC.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        Nric targetNric = target.getNric();
        Nric editedPersonNric = editedPerson.getNric();
        if (!contains(targetNric)) {
            throw new PersonNotFoundException();
        }

        if (!targetNric.equals(editedPersonNric) && contains(editedPersonNric)) {
            throw new DuplicatePersonException();
        }

        if (!targetNric.equals(editedPersonNric)) {
            internalMap.remove(targetNric);
        }

        internalMap.put(editedPersonNric, editedPerson);
    }

    /**
     * Removes the NRIC and equivalent person from the list.
     * The NRIC must exist in the list.
     */
    public void remove(Nric toRemove) {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new PersonNotFoundException();
        } else {
            internalMap.remove(toRemove);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableMap<Nric, Person> asUnmodifiableObservableMap() {
        return internalUnmodifiableMap;
    }


    public void setNricMap(UniqueNricMap replacement) {
        requireNonNull(replacement);
        internalMap.clear();
        for (Nric nric : replacement.internalMap.keySet()) {
            internalMap.put(nric, replacement.internalMap.get(nric));
        }
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setNricMap(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalMap.clear();

        for (int i = 0; i < persons.size(); i++) {
            internalMap.put(persons.get(i).getNric(), persons.get(i));
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueNricMap // instanceof handles nulls
                && internalMap.equals(((UniqueNricMap) other).internalMap));
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}



