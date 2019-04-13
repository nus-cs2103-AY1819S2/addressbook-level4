package seedu.address.model.prescription;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.PersonId;
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
     * Sort prescription list by date
     */
    public void sort(Comparator<Prescription> prescriptionComparator) {
        requireNonNull(prescriptionComparator);
        FXCollections.sort(internalList, prescriptionComparator);
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

    /**
     * Replaces the contents of this list with {@code medHists}.
     * {@code medHists} must not contain duplicate medical histories.
     */
    public void setPrescriptions(List<Prescription> medHists) {
        requireAllNonNull(medHists);
        if (!prescriptionsAreUnique(medHists)) {
            throw new DuplicatePrescriptionException();
        }

        internalList.setAll(medHists);
    }

    public void setDoctorToNull(PersonId deleted) {
        requireAllNonNull(deleted);
        FilteredList<Prescription> setToNull = internalList.filtered(x -> x.getDoctorId().equals(deleted));
        for (int index = 0; index < setToNull.size(); index++) {
            setToNull.get(index).setDoctor(null);
        }
    }

    public void setPatientToNull(PersonId deleted) {
        requireAllNonNull(deleted);
        FilteredList<Prescription> setToNull = internalList.filtered(x -> x.getPatientId().equals(deleted));
        for (int index = 0; index < setToNull.size(); index++) {
            setToNull.get(index).setPatient(null);
        }
    }

    @Override
    public Iterator<Prescription> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePrescriptionList // instanceof handles nulls
                && internalList.equals(((UniquePrescriptionList) other).internalList));
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

    /**
     * Returns true if {@code medHists} contains only unique medHists.
     */
    private boolean prescriptionsAreUnique(List<Prescription> prescriptions) {
        for (int i = 0; i < prescriptions.size() - 1; i++) {
            for (int j = i + 1; j < prescriptions.size(); j++) {
                if (prescriptions.get(i).isSamePrescription(prescriptions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}

