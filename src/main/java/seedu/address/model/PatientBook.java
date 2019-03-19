package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.person.patient.UniquePatientList;

/**
 * @author Rohan
 * Wrapper class for storing Patients and operations involving Patient objects.
 * Duplicates are not allowed (by .isSamePatient comparison)
 */
public class PatientBook implements ReadOnlyPatientBook {

    private final UniquePatientList uniquePatientList;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    {
        this.uniquePatientList = new UniquePatientList();
    }

    public PatientBook() {
    }

    public PatientBook(ReadOnlyPatientBook toCopy) {
        this();
        resetData(toCopy);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPatients(ObservableList<Patient> patients) {
        this.uniquePatientList.setPatients(patients);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code PatientBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPatientBook newData) {
        requireNonNull(newData);

        setPatients(newData.getPatientList());
    }

    /**
     * Returns true if a Patient with the same identity as {@code patient}
     * exists in the PatientBook.
     */
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return this.uniquePatientList.contains(patient);
    }

    /**
     * Adds a Patient to the address book.
     * Patient object must not already exist in the PatientBook.
     */
    public void addPatient(Patient patient) {
        this.uniquePatientList.add(patient);
        indicateModified();
    }

    /**
     * Replaces the given Patient {@code target} in the list with {@code editedPatient}.
     * {@code target} must exist in the PatientBook.
     * The person identity of {@code editedPatient} must not be the same as another
     * existing Patient in the PatientBook.
     */
    public void setPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);
        this.uniquePatientList.setPatient(target, editedPatient);

        indicateModified();
    }

    /**
     * Removes {@code patient} from this {@code PatientBook}.
     * {@code patient} must exist in the PatientBook.
     */
    public void removePatient(Patient patient) {
        this.uniquePatientList.remove(patient);
        indicateModified();
    }

    /**
     * Notifies listeners that PatientBook has been modified.
     */
    protected void indicateModified() {
        this.invalidationListenerManager.callListeners(this);
    }

    @Override
    public ObservableList<Patient> getPatientList() {
        return this.uniquePatientList.asUnmodifiableObservableList();
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        this.invalidationListenerManager.addListener(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        this.invalidationListenerManager.removeListener(invalidationListener);
    }

    @Override
    public String toString() {
        return uniquePatientList.asUnmodifiableObservableList().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        return other instanceof HealthWorkerBook && this.uniquePatientList
            .equals(((PatientBook) other).uniquePatientList);
    }

    @Override
    public int hashCode() {
        return this.uniquePatientList.hashCode();
    }


}
