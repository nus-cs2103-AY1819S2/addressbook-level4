package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Name;

/**
 * Represents a Employee's name in the Pocket Project.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EmployeeName extends Name {

    public final String fullName;

    /**
     * Constructs a {@code EmployeeName}.
     *
     * @param name A valid name.
     */
    public EmployeeName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     *  Returns a clone of this EmployeeName object.
     */
    public EmployeeName clone() {
        return new EmployeeName(this.fullName);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeName // instanceof handles nulls
                && fullName.equals(((EmployeeName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
