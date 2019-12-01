package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.person.patient.Patient;

/**
 * Tests that a {@code Patient}'s {@code id} matches the given id.
 */
public class RecordContainsPatientIdPredicate implements Predicate<Patient> {
    private final PersonId id;

    public RecordContainsPatientIdPredicate(PersonId id) {
        this.id = id;
    }

    @Override
    public boolean test(Patient patient) {
        return patient.getId().equals(id);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsPatientIdPredicate // instanceof handles nulls
                && this.id.equals(((RecordContainsPatientIdPredicate) other).id)); // state check
    }

}
