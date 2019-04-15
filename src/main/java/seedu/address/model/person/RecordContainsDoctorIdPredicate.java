package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.person.doctor.Doctor;

/**
 * Tests that a {@code Doctor}'s {@code id} matches the given id.
 */
public class RecordContainsDoctorIdPredicate implements Predicate<Doctor> {
    private final PersonId id;

    public RecordContainsDoctorIdPredicate(PersonId id) {
        this.id = id;
    }

    @Override
    public boolean test(Doctor doctor) {
        return doctor.getId().equals(id);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsDoctorIdPredicate // instanceof handles nulls
                && this.id.equals(((RecordContainsDoctorIdPredicate) other).id)); // state check
    }

}
