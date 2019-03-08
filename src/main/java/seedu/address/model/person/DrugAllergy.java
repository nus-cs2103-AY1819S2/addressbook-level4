package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's DrugAllergy if any in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDrugAllergy(String)}
 */
public class DrugAllergy {

    public static final String MESSAGE_CONSTRAINTS =
        "DrugAllergies should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //TODO: Discuss if full drug name or use short form instead
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String allergyName;

    /**
     * Constructs a {@code DrugAllergy}.
     *
     * @param allergy A valid DrugAllergy.
     */
    public DrugAllergy(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidDrugAllergy(allergy), MESSAGE_CONSTRAINTS);
        allergyName = allergy;
    }

    /**
     * Returns true if a given string is a valid DrugAllergy.
     */
    public static boolean isValidDrugAllergy(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return allergyName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DrugAllergy // instanceof handles nulls
            && allergyName.equals(((DrugAllergy) other).allergyName)); // state check
    }

    @Override
    public int hashCode() {
        return allergyName.hashCode();
    }

}
