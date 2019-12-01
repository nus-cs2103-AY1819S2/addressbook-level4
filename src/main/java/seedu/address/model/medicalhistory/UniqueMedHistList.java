//@@author Liuyy99
package seedu.address.model.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.medicalhistory.exceptions.DuplicateMedHistException;
import seedu.address.model.medicalhistory.exceptions.MedHistNotFoundException;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;


/**
 * A list of medical histories that enforces uniqueness between its elements and does not allow nulls.
 * A medical history is considered unique by comparing using {@code MedicalHistory#isSameMedHist(MedHist)}.
 * As such, adding and updating of medical histories uses MedicalHistory#isSameMedHist(MedHist) for equality so as to
 * ensure that the medical history being added or updated is unique in terms of identity in the UniqueMedHistList.
 * However, the removal of a medical history uses MedicalHistory#equals(Object) so as to ensure that the medical history
 * with exactly the same fields will be removed. Supports a minimal set of list operations.
 *
 * @see MedicalHistory#isSameMedHist(MedicalHistory)
 */

public class UniqueMedHistList implements Iterable<MedicalHistory> {

    private final ObservableList<MedicalHistory> internalList = FXCollections.observableArrayList();
    private final ObservableList<MedicalHistory> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent medical history as the given argument.
     */
    public boolean contains(MedicalHistory toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMedHist);
    }

    /**
     * Sort medical history list by date
     */
    public void sort(Comparator<MedicalHistory> medHistComparator) {
        requireNonNull(medHistComparator);
        FXCollections.sort(internalList, medHistComparator);
    }

    /**
     * Adds a medical history to the list.
     * The medical history must not already exist in the list.
     */
    public void add(MedicalHistory toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMedHistException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the medical history {@code target} in the list with {@code editedMedHist}.
     * {@code target} must exist in the list.
     * The medical history identity of {@code editedMedHist} must not be the same as another existing medical history
     * in the list.
     */
    public void setMedHist(MedicalHistory target, MedicalHistory editedMedHist) {
        requireAllNonNull(target, editedMedHist);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MedHistNotFoundException();
        }

        if (!target.isSameMedHist(editedMedHist) && contains(editedMedHist)) {
            throw new DuplicateMedHistException();
        }

        internalList.set(index, editedMedHist);
    }

    /**
     * When patient is modified, update patient info in medical history
     */
    public void setEditedPatient(PersonId toEdit, Patient editedPatient) {
        requireAllNonNull(toEdit);
        requireAllNonNull(editedPatient);

        FilteredList<MedicalHistory> medHistNeedToEdit = internalList.filtered(x -> x.getPatientId().equals(toEdit));
        MedicalHistory modifiedMedHist;

        int numberOfOccurrence = medHistNeedToEdit.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedMedHist = medHistNeedToEdit.get(i);
            int indexToReplace = internalList.indexOf(modifiedMedHist);
            modifiedMedHist.setPatient(editedPatient);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedMedHist);
        }
    }

    /**
     * When doctor is modified, update doctor info in medical history
     */
    public void setEditedDoctor(PersonId toEdit, Doctor editedDoctor) {
        requireAllNonNull(toEdit);
        requireAllNonNull(editedDoctor);

        FilteredList<MedicalHistory> medHistNeedToEdit = internalList.filtered(x -> x.getDoctorId().equals(toEdit));
        MedicalHistory modifiedMedHist;

        int numberOfOccurrence = medHistNeedToEdit.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedMedHist = medHistNeedToEdit.get(i);
            int indexToReplace = internalList.indexOf(modifiedMedHist);
            modifiedMedHist.setDoctor(editedDoctor);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedMedHist);
        }
    }

    /**
     * When patient is deleted, set patient to null in medical history
     */
    public void setPatientToNull(PersonId deleted) {
        requireAllNonNull(deleted);

        FilteredList<MedicalHistory> medHistToSet = internalList.filtered(x -> x.getPatientId().equals(deleted));
        MedicalHistory modifiedMedHist;

        int numberOfOccurrence = medHistToSet.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedMedHist = medHistToSet.get(i);
            int indexToReplace = internalList.indexOf(modifiedMedHist);
            modifiedMedHist.setPatient(null);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedMedHist);
        }
    }

    /**
     * When doctor is deleted, set doctor to null in medical history
     */
    public void setDoctorToNull(PersonId deleted) {
        requireAllNonNull(deleted);
        FilteredList<MedicalHistory> medHistToSet = internalList.filtered(x -> x.getDoctorId().equals(deleted));
        MedicalHistory modifiedMedHist;

        int numberOfOccurrence = medHistToSet.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedMedHist = medHistToSet.get(i);
            int indexToReplace = internalList.indexOf(modifiedMedHist);
            modifiedMedHist.setDoctor(null);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedMedHist);
        }
    }

    /**
     * Removes the equivalent medical history from the list.
     * The medical history must exist in the list.
     */
    public void remove(MedicalHistory toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MedHistNotFoundException();
        }
    }

    public void setMedHists(UniqueMedHistList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code medHists}.
     * {@code medHists} must not contain duplicate medical histories.
     */
    public void setMedHists(List<MedicalHistory> medHists) {
        requireAllNonNull(medHists);
        if (!medHistsAreUnique(medHists)) {
            throw new DuplicateMedHistException();
        }

        internalList.setAll(medHists);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<MedicalHistory> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<MedicalHistory> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMedHistList // instanceof handles nulls
                && internalList.equals(((UniqueMedHistList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code medHists} contains only unique medHists.
     */
    private boolean medHistsAreUnique(List<MedicalHistory> medHists) {
        for (int i = 0; i < medHists.size() - 1; i++) {
            for (int j = i + 1; j < medHists.size(); j++) {
                if (medHists.get(i).isSameMedHist(medHists.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
