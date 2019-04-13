package seedu.address.model.medicalhistory;

import static java.util.Objects.requireNonNull;

/**
 * Represents id of a medical history in the docX.
 */
public class MedHistId {

    public final String medHistId;

    /**
     * Constructs a {@code MedHistId}.
     *
     * @param medHistId A valid id containing pid, did, date info.
     */
    public MedHistId(String medHistId) {
        requireNonNull(medHistId);
        this.medHistId = medHistId;
    }

    @Override
    public String toString() {
        return medHistId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedHistId // instanceof handles nulls
                && medHistId.equals(((MedHistId) other).medHistId)); // state check
    }

    @Override
    public int hashCode() {
        return medHistId.hashCode();
    }

}

