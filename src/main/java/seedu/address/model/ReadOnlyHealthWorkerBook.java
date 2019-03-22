package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * Unmodifiable view of HealthWorkerBook.
 */
public interface ReadOnlyHealthWorkerBook extends Observable {

    /**
     * Returns an unmodifiable view of the healthworkers list.
     * This list will not contain any duplicate healthworkers
     */

    ObservableList<HealthWorker> getHealthWorkerList();
}
