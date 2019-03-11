package seedu.address.model.moduleinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a moduleInfo's department
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleInfoDepartment(String)}
 */
public class ModuleInfoDepartment {
    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of only characters and white spaces and cannot begin with whitespace.";
    public static final String VALIDATION_REGEX = "[^\\s1-9][\\p{Graph} ]{0,}";
    public final String department;

    public ModuleInfoDepartment(String moduleDepartment) {
        requireNonNull(moduleDepartment);
        checkArgument(isValidModuleInfoDepartment(moduleDepartment), MESSAGE_CONSTRAINTS);
        department = moduleDepartment;
    }

    public static boolean isValidModuleInfoDepartment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String toString() {
        return department;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleInfoDepartment// instanceof handles nulls
                && department.equals(((ModuleInfoDepartment) other).department)); // state check
    }

    @Override
    public int hashCode() {
        return department.hashCode();
    }
}
