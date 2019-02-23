package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Health Worker's Organization.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrgName(String)}
 */
public class Organization {

    public static final String MESSAGE_CONSTRAINTS = "Organization name "
            + "should contain at least 2 characters";
    public static final String VALIDATION_REGEX = "[a-zA-z0-9,.\\-\\s]{2}";

    private String orgName;

    public Organization(String orgName) {
        requireNonNull(orgName);
        checkArgument(isValidOrgName(orgName), MESSAGE_CONSTRAINTS);
        this.orgName = orgName;
    }

    public boolean isValidOrgName(String test) {
        return test.matches(VALIDATION_REGEX);
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
