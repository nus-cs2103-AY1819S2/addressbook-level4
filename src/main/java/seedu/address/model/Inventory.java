package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.UniqueMedicineList;

/**
 * Wraps all data at the inventory level
 * Duplicates are not allowed (by .isSameMedicine comparison)
 */
public class Inventory implements ReadOnlyInventory {

    private final UniqueMedicineList medicines;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        medicines = new UniqueMedicineList();
    }

    public Inventory() {}

    /**
     * Creates an Inventory using the Medicines in the {@code toBeCopied}
     */
    public Inventory(ReadOnlyInventory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the medicine list with {@code medicines}.
     * {@code medicines} must not contain duplicate medicines.
     */
    public void setMedicines(List<Medicine> medicines) {
        this.medicines.setMedicines(medicines);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newData}.
     */
    public void resetData(ReadOnlyInventory newData) {
        requireNonNull(newData);

        setMedicines(newData.getMedicineList());
    }

    //// medicine-level operations

    /**
     * Returns true if a medicine with the same identity as {@code medicine} exists in the inventory.
     */
    public boolean hasMedicine(Medicine medicine) {
        requireNonNull(medicine);
        return medicines.contains(medicine);
    }

    /**
     * Adds a medicine to the inventory.
     * The medicine must not already exist in the inventory.
     */
    public void addMedicine(Medicine p) {
        medicines.add(p);
        indicateModified();
    }

    /**
     * Replaces the given medicine {@code target} in the list with {@code editedMedicine}.
     * {@code target} must exist in the inventory.
     * The identity of {@code editedMedicine} must not be the same as another existing medicine in the inventory.
     */
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        requireNonNull(editedMedicine);

        medicines.setMedicine(target, editedMedicine);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the inventory.
     */
    public void removeMedicine(Medicine key) {
        medicines.remove(key);
        indicateModified();
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
     * Notifies listeners that the inventory has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return medicines.asUnmodifiableObservableList().size() + " medicines";
        // TODO: refine later
    }

    @Override
    public ObservableList<Medicine> getMedicineList() {
        return medicines.asUnmodifiableObservableList();
    }

    /**
     * Sorts medicine list according to given comparator.
     * @param comparator
     * @return unmodifiable sorted view of the medicine list.
     */
    @Override
    public ObservableList<Medicine> getSortedMedicineList(Comparator<Medicine> comparator) {
        return new SortedList<>(medicines.asUnmodifiableObservableList(), comparator);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && medicines.equals(((Inventory) other).medicines));
    }

    @Override
    public int hashCode() {
        return medicines.hashCode();
    }
}
