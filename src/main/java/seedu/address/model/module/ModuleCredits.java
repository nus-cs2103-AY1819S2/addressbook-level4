package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class ModuleCredits {

    public static final String MESSAGE_CONSTRAINTS =
            "Module credits is at most 2 digits with first digit starting with 1.";
    public static final String VALIDATION_REGEX  = "1?\\d";
    public final String value;

    public ModuleCredits(String moduleCredits) {
        requireNonNull(moduleCredits);
        checkArgument(isValidModuleCredits(moduleCredits), MESSAGE_CONSTRAINTS);
        value = moduleCredits;
    }

    public boolean isValidModuleCredits(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCredits// instanceof handles nulls
                && value.equals(((ModuleCredits) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
