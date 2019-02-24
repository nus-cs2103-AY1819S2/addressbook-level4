package seedu.address.model.module;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class ModuleCredits {

    public static final String MESSAGE_CONSTRAINTS =
            "Module credits is at least 0, at most 20";
    public static final int MAX_CREDITS = 20;
    public static final int MIN_CREDITS = 0;
    public final int credits;

    public ModuleCredits(int moduleCredits) {
        checkArgument(isValidModuleCredits(moduleCredits), MESSAGE_CONSTRAINTS);
        credits = moduleCredits;
    }

    public boolean isValidModuleCredits(int test) {
        return test <= MAX_CREDITS && test>= MIN_CREDITS;
    }

    public String toString() {
        return String.format("%d", credits);
    }

    public int getCredits() {
        return credits;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCredits// instanceof handles nulls
                && credits == ((ModuleCredits) other).credits); // state check
    }

    @Override
    public int hashCode() {
        return credits;
    }
}
