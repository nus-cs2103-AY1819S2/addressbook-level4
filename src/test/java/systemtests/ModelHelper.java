package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Patient> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Doctor> PREDICATE_MATCHING_NO_DOCTORS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Patient> toDisplay) {
        Optional<Predicate<Patient>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPatientList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Patient... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Patient} equals to {@code other}.
     */
    private static Predicate<Patient> getPredicateMatching(Patient other) {
        return patient -> patient.equals(other);
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredDoctorList(Model model, List<Doctor> toDisplay) {
        Optional<Predicate<Doctor>> predicate =
                toDisplay.stream().map(ModelHelper::getDoctorPredicateMatching).reduce(Predicate::or);
        model.updateFilteredDoctorList(predicate.orElse(PREDICATE_MATCHING_NO_DOCTORS));
    }

    /**
     * @see ModelHelper#setFilteredDoctorList(Model, List)
     */
    public static void setFilteredDoctorList(Model model, Doctor... toDisplay) {
        setFilteredDoctorList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Doctor} equals to {@code other}.
     */
    private static Predicate<Doctor> getDoctorPredicateMatching(Doctor other) {
        return doctor -> doctor.equals(other);
    }

}
