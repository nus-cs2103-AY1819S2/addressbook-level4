package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

/**
 * Supports a minimal set of list operations.
 *
 * @see Appointment#isSameAppointment(Appointment)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAppointment);
    }

    /**
     * Adds an appointment to the list.
     * The appointment must not already exist in the list.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code changedAppointment}.
     * {@code target} must exist in the list.
     * The appointment details of {@code changedAppointment} must not be
     * the same as another existing appointment in the list.
     */
    public void setAppointment(Appointment target, Appointment changedAppointment) {
        requireAllNonNull(target, changedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.isSameAppointment(changedAppointment) && contains(changedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, changedAppointment);
    }

    /**
     * Replace the content of the list with another list.
     *
     * @param replacement the new list to replace the current list
     */
    public void setAppointments(UniqueAppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        if (!appointmentsAreUnique(appointments)) {
            throw new DuplicateAppointmentException();
        }

        internalList.setAll(appointments);
    }

    /**
     * When patient is modified, update patient info in appointment
     */
    public void setEditedPatient(PersonId toEdit, Patient editedPatient) {
        requireAllNonNull(toEdit);
        requireAllNonNull(editedPatient);

        FilteredList<Appointment> appointmentsToSet = internalList
                .filtered(x -> x.getPatientId().patientId.equals(toEdit));
        Appointment modifiedAppointment;

        int numberOfOccurrence = appointmentsToSet.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedAppointment = appointmentsToSet.get(i);
            int indexToReplace = internalList.indexOf(modifiedAppointment);
            modifiedAppointment.setPatient(editedPatient);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedAppointment);
        }
    }

    /**
     * When doctor is modified, update doctor info in appointment
     */
    public void setEditedDoctor(PersonId toEdit, Doctor editedDoctor) {
        requireAllNonNull(toEdit);
        requireAllNonNull(editedDoctor);

        FilteredList<Appointment> appointmentsToSet = internalList
                .filtered(x -> x.getDoctorId().doctorId.equals(toEdit));
        Appointment modifiedAppointment;

        int numberOfOccurrence = appointmentsToSet.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedAppointment = appointmentsToSet.get(i);
            int indexToReplace = internalList.indexOf(modifiedAppointment);
            modifiedAppointment.setDoctor(editedDoctor);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedAppointment);
        }
    }

    /**
     * Set the underlying patient object of the appointments whose patient was deleted to null.
     *
     * @param deleted id of the deleted patient
     */
    public void setPatientToNull(PersonId deleted) {
        requireAllNonNull(deleted);
        FilteredList<Appointment> appointmentsToSet = internalList
                .filtered(x -> x.getPatientId().patientId.equals(deleted));
        Appointment modifiedAppointment;

        int numberOfOccurrence = appointmentsToSet.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedAppointment = appointmentsToSet.get(i);
            int indexToReplace = internalList.indexOf(modifiedAppointment);
            modifiedAppointment.setPatient(null);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedAppointment);
        }
    }

    /**
     * Set the underlying doctor object of the appointments whose doctor was deleted to null.
     *
     * @param deleted id of the deleted doctor
     */
    public void setDoctorToNull(PersonId deleted) {
        requireAllNonNull(deleted);
        FilteredList<Appointment> appointmentsToSet = internalList
                .filtered(x -> x.getDoctorId().doctorId.equals(deleted));
        Appointment modifiedAppointment;

        int numberOfOccurrence = appointmentsToSet.size();
        for (int i = 0; i < numberOfOccurrence; i++) {
            modifiedAppointment = appointmentsToSet.get(i);
            int indexToReplace = internalList.indexOf(modifiedAppointment);
            modifiedAppointment.setDoctor(null);
            // this approach forces the listeners to be notified.
            internalList.set(indexToReplace, modifiedAppointment);
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code appointments} contains only unique appointments.
     */
    private boolean appointmentsAreUnique(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = i + 1; j < appointments.size(); j++) {
                if (appointments.get(i).isSameAppointment(appointments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
