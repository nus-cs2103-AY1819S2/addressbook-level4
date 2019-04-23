package seedu.address.model.person.healthworker;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Health Worker's Organization.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrgName(String)}
 */
public class Organization {

    public static final String MESSAGE_CONSTRAINTS = "Organization name "
            + "should contain only alphanumeric characters and spaces";
    public static final String VALIDATION_REGEX =
            "^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$"; // Organization name should
    // contain only alphanumeric character strings with spaces in between.

    private String orgName;

    public Organization(String orgName) {
        requireNonNull(orgName);
        checkArgument(isValidOrgName(orgName), MESSAGE_CONSTRAINTS);
        this.orgName = orgName;
    }

    public static boolean isValidOrgName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Method that returns true if organization name contains the specified substring.
     */
    public boolean contains(String substring) {
        return this.orgName.toLowerCase().contains(substring.toLowerCase());
    }

    public String getOrgName() {
        return orgName;
    }

    @Override
    public String toString() {
        return getOrgName();
    }

    @Override
    public int hashCode() {
        return this.orgName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Organization && this
                .orgName.equals(((Organization) other).orgName));
    }
}
