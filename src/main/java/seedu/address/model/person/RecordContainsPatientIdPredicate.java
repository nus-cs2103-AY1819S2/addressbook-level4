/* @@author wayneswq */

package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Patient}'s {@code id} matches the given id.
 */
public class RecordContainsPatientIdPredicate implements Predicate<Patient> {
    private final int id;

    public RecordContainsPatientIdPredicate(int id) {
        this.id = id;
    }

    @Override
    public boolean test(Patient patient) {
        return patient.getId() == id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsPatientIdPredicate // instanceof handles nulls
                && id == (((RecordContainsPatientIdPredicate) other).id)); // state check
    }

}
