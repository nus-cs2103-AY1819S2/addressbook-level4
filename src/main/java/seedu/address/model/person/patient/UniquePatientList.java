package seedu.address.model.person.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * @author Rohan
 * <p>
 * A list of Patient objects that enforces uniqueness between its elements
 * and does not allow nulls. A person is considered unique by comparing using
 * {@code Patient#isSamePatient(Patient)}. As such, adding and
 * updating of Patients uses Patient#isSamePatient(Patient)
 * for equality so as to ensure that the Patient being added or updated is
 * unique in terms of identity in the UniquePatientList. However, the
 * removal of a person uses Patient#equals(Patient) so
 * as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 * @see Patient#isSamePatient(Patient)
 */
public class UniquePatientList implements Iterable<Patient> {

    private final ObservableList<Patient> internalList = FXCollections
        .observableArrayList();
    private final ObservableList<Patient> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Patient as the
     * given argument.
     */
    public boolean contains(Patient toCheck) {
        requireNonNull(toCheck);
        return this.internalList.stream().anyMatch(toCheck::isSamePatient);
    }

    /**
     * Adds a Patient to the list.
     * The person must not already exist in the list.
     */
    public void add(Patient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        this.internalList.add(toAdd);
    }

    /**
     * Replaces the Patient {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list.
     * The person identity of {@code edited} must not be the same as another
     * existing Patient in the list.
     */
    public void setPatient(Patient target, Patient edited) {
        requireAllNonNull(target, edited);

        int index = this.internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePatient(edited) && contains(edited)) {
            throw new DuplicatePersonException();
        }

        this.internalList.set(index, edited);
    }

    /**
     * Removes the equivalent Patient from the list.
     * The Patient must exist in the list.
     */
    public void remove(Patient toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPatients(UniquePatientList replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code patients}.
     * {@code patients} must not contain duplicate persons.
     */
    public void setPatients(List<Patient> patients) {
        requireAllNonNull(patients);
        if (!patientsAreUnique(patients)) {
            throw new DuplicatePersonException();
        }

        this.internalList.setAll(patients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Patient> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public Iterator<Patient> iterator() {
        return this.internalList.iterator();
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniquePatientList // instanceof handles nulls
            && this.internalList.equals(((UniquePatientList) other).internalList));
    }

    /**
     * Returns true if {@code persons} contains only unique Patients.
     */
    private boolean patientsAreUnique(List<Patient> patients) {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(i).isSamePatient(patients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Patient getAt(int index) {
        assert (index < this.internalList.size() - 1);
        return this.internalList.get(index);
    }

}
