package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private static final String SORT_BY_NAME = "name";
    private static final String SORT_BY_GENDER = "gender";
    private static final String SORT_BY_MAJOR = "major";
    private static final String SORT_BY_YEAR_OF_STUDY = "yearofstudy";

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean containsMatricNumber (MatricNumber toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().map(x -> x.getMatricNumber()).anyMatch(toCheck::isSameMatricNumber);
    }

    /**
     * Returns the person if the list contains a person who has the matric number as the given argument.
     */
    public Person getPersonWithMatricNumber (MatricNumber toCheck) {
        requireNonNull(toCheck);
        Person person;
        for (int i = 0; i < internalList.size(); i++) {
            person = internalList.get(i);
            if (person.getMatricNumber().isSameMatricNumber(toCheck)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Sorts the Member's list based on a given predicate.
     */
    public void sortList(String input) {
        requireNonNull(input);

        FXCollections.sort(internalList, new Comparator <Person> () {
            @Override
            public int compare(Person o1, Person o2) {
                String sortCriteria = input.toString().toLowerCase();
                Pair<String, String> test = getCriteria(sortCriteria, o1, o2);
                int result = 1;
                result = test.getKey().compareTo(test.getValue());

                if (result != 0) {
                    return result;
                }
                return o1.getName().fullName.compareTo(o2.getName().fullName);
            }
        });
    }

    /**
     * [Add your comments]
     * @param sortCriteria
     * @param o1
     * @param o2
     * @return
     */
    public Pair<String, String> getCriteria(String sortCriteria, Person o1, Person o2) {
        switch (sortCriteria.toLowerCase()) {
        case SORT_BY_NAME:
            return new Pair<>(o1.getName().fullName, o2.getName().fullName);
        case SORT_BY_GENDER:
            return new Pair<>(o1.getGender().value, o2.getGender().value);
        case SORT_BY_MAJOR:
            return new Pair<>(o1.getMajor().value, o2.getMajor().value);
        case SORT_BY_YEAR_OF_STUDY:
            return new Pair<>(o1.getYearOfStudy().value, o2.getYearOfStudy().value);
        default:
            break;
        }
        return null;
    }


    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
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
