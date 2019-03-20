package seedu.equipment.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.equipment.model.equipment.Equipment;

/**
 * Unmodifiable view of an equipment book
 */
public interface ReadOnlyEquipmentManager extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Equipment> getPersonList();

}
