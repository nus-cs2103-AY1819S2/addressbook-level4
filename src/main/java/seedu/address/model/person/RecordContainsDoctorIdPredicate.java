/* @@author wayneswq */

package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Doctor}'s {@code id} matches the given id.
 */
public class RecordContainsDoctorIdPredicate implements Predicate<Doctor> {
    private final int id;

    public RecordContainsDoctorIdPredicate(int id) {
        this.id = id;
    }

    @Override
    public boolean test(Doctor doctor) {
        return doctor.getId() == id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsDoctorIdPredicate // instanceof handles nulls
                && id == (((RecordContainsDoctorIdPredicate) other).id)); // state check
    }

}
