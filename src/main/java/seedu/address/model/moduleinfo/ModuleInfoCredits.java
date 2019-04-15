package seedu.address.model.moduleinfo;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a moduleInfo's credit
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleInfoCredits(double)}
 */
public class ModuleInfoCredits {
    public static final String MESSAGE_CONSTRAINTS =
            "Module credits is at least 0, at most 40";
    public final Double credits;

    public ModuleInfoCredits(double moduleInfoCredits) {
        checkArgument(isValidModuleInfoCredits(moduleInfoCredits), MESSAGE_CONSTRAINTS);
        credits = moduleInfoCredits;
    }

    public static boolean isValidModuleInfoCredits(double test) {
        return (test <= 40 && test >= 0);
    }

    public String toString() {
        return credits.toString();
    }

    public double getCredits() {
        return credits;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleInfoCredits// instanceof handles nulls
                && credits == ((ModuleInfoCredits) other).credits); // state check
    }

    @Override
    public int hashCode() {
        return credits.hashCode();
    }
}
