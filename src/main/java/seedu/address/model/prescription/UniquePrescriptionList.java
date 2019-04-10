package seedu.address.model.prescription;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.prescription.exceptions.DuplicatePrescriptionException;
import seedu.address.model.prescription.exceptions.PrescriptionNotFoundException;

/**
 * A list of unique prescriptions which also does not have null elements.
 * Currently add and remove operations are supported.
 */
public class UniquePrescriptionList implements Iterable<Prescription> {
    private final ObservableList<Prescription> internalList = FXCollections.observableArrayList();
    private final ObservableList<Prescription> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Add a new prescription to the ArrayList.
     * The new prescription to add must not exist in the current ArrayList.
     */
    public void addPrescription(Prescription toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePrescriptionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns true if the ArrayList contains an equivalent prescription as the input.
     */
    public boolean contains(Prescription other) {
        requireNonNull(other);
        return internalList.stream().anyMatch(other::equals);
    }

    /**
     * Remove the specified prescription from the list.
     * If the input prescription does not exist in the list, an exception is thrown.
     */
    public void remove(Prescription toRemove) {
        requireNonNull(toRemove);
        boolean result = internalList.remove(toRemove);
        if (!result) {
            throw new PrescriptionNotFoundException();
        }
    }

    /**
     * Replaces the prescription {@code target} in the list with {@code editedPrescription}.
     * {@code target} must exist in the list.
     * The prescription identity of {@code editedPrescription} must not be the same as another existing prescription
     * in the list.
     */
    public void setPrescription(Prescription target, Prescription editedPrescription) {
        requireAllNonNull(target, editedPrescription);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PrescriptionNotFoundException();
        }

        if (!target.isSamePrescription(editedPrescription) && contains(editedPrescription)) {
            throw new DuplicatePrescriptionException();
        }

        internalList.set(index, editedPrescription);
    }

    @Override
    public Iterator<Prescription> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Prescription> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

}

