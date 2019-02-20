package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.medicine.Medicine;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the medicines list.
     * This list will not contain any duplicate medicines.
     */
    ObservableList<Medicine> getMedicineList();

}
