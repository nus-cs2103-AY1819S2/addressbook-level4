package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.medicine.exceptions.DuplicateMedicineException;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;

/**
 * A list of medicines that enforces uniqueness between its elements and does not allow nulls.
 * A medicine is considered unique by comparing using {@code Medicine#isSameMedicine(Medicine)}.
 * As such, adding and updating of medicines uses Medicine#isSameMedicine(Medicine) for equality so as to ensure that
 * the medicine being added or updated is unique in terms of identity in the UniqueMedicineList. However, the removal
 * of a medicine uses Medicine#equals(Object) so as to ensure that the medicine with exactly the same fields will be
 * removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Medicine#isSameMedicine(Medicine)
 */
public class UniqueMedicineList implements Iterable<Medicine> {

    private final ObservableList<Medicine> internalList = FXCollections.observableArrayList();
    private final ObservableList<Medicine> internalListSorted = new SortedList<>(internalList,
            Comparator.naturalOrder());
    private final ObservableList<Medicine> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent medicine as the given argument.
     */
    public boolean contains(Medicine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMedicine);
    }

    /**
     * Adds a medicine to the list.
     * The medicine must not already exist in the list.
     */
    public void add(Medicine toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMedicineException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the medicine {@code target} in the list with {@code editedMedicine}.
     * {@code target} must exist in the list.
     * The medicine identity of {@code editedMedicine} must not be the same as another existing medicine in the list.
     */
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        requireAllNonNull(target, editedMedicine);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MedicineNotFoundException();
        }

        if (!target.isSameMedicine(editedMedicine) && contains(editedMedicine)) {
            throw new DuplicateMedicineException();
        }

        internalList.set(index, editedMedicine);
    }

    /**
     * Removes the equivalent medicine from the list.
     * The medicine must exist in the list.
     */
    public void remove(Medicine toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MedicineNotFoundException();
        }
    }

    public void setMedicines(UniqueMedicineList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code medicines}.
     * {@code medicines} must not contain duplicate medicines.
     */
    public void setMedicines(List<Medicine> medicines) {
        requireAllNonNull(medicines);
        if (!medicinesAreUnique(medicines)) {
            throw new DuplicateMedicineException();
        }

        internalList.setAll(medicines);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Medicine> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Medicine> iterator() {
        return internalListSorted.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMedicineList // instanceof handles nulls
                        && internalListSorted.equals(((UniqueMedicineList) other).internalListSorted));
    }

    @Override
    public int hashCode() {
        return internalListSorted.hashCode();
    }

    /**
     * Returns true if {@code medicines} contains only unique medicines.
     */
    private boolean medicinesAreUnique(List<Medicine> medicines) {
        for (int i = 0; i < medicines.size() - 1; i++) {
            for (int j = i + 1; j < medicines.size(); j++) {
                if (medicines.get(i).isSameMedicine(medicines.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
