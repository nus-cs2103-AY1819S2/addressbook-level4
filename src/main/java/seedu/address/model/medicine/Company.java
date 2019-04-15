package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Company that the Medicine in the inventory was purchased from.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company implements Comparable<Company> {
    public static final int MAX_LENGTH_COMPANY = 40;
    public static final String MESSAGE_CONSTRAINTS = "Company names can take any values, and it should not be blank\n"
            + "Max length: " + MAX_LENGTH_COMPANY + " characters.";

    /*
     * The first character of the company name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String companyName;

    /**
     * Constructs a {@code Company}.
     *
     * @param company A valid company name.
     */
    public Company(String company) {
        requireNonNull(company);
        checkArgument(isValidCompany(company), MESSAGE_CONSTRAINTS);
        companyName = company;
    }

    /**
     * Returns true if a given string is a valid company name.
     */
    public static boolean isValidCompany(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH_COMPANY;
    }

    @Override
    public int compareTo(Company other) {
        return this.companyName.compareToIgnoreCase(other.companyName);
    }

    @Override
    public String toString() {
        return companyName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Company // instanceof handles nulls
                && companyName.equals(((Company) other).companyName)); // state check
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }

}
