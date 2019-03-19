package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.person.patient.Patient;

/**
 * @author Hui Chun
 * Unmodifiable view of PatientBook.
 */
public interface ReadOnlyPatientBook extends Observable {

    /**
     * Returns an unmodifiable view of the patients list.
     * This list will not contain any duplicate patients.
     */
    ObservableList<Patient> getPatientList();
}
