package seedu.address.model;

import java.util.Comparator;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.medicine.Medicine;

/**
 * Unmodifiable view of an inventory
 */
public interface ReadOnlyInventory extends Observable {

    /**
     * Returns an unmodifiable view of the medicines list.
     * This list will not contain any duplicate medicines.
     */
    ObservableList<Medicine> getMedicineList();

    /**
     * Returns a sorted, unmodifiable view of the medicines list.
     * This list will not contain any duplicate medicines.
     */
    ObservableList<Medicine> getSortedMedicineList(Comparator<Medicine> comparator);

}
