package seedu.address.model.moduleinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a moduleInfo's code
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleInfoCode(String)}
 */
public class ModuleInfoCode {
    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should start with at least 2 alphabets, followed by 4 numbers and (if) 0 to 3 letter suffix";
    public static final String VALIDATION_REGEX = "[A-Z]{2,3}\\d{4}[A-Z]{0,3}";
    public final String value;

    public ModuleInfoCode(String moduleInfoCode) {
        requireNonNull(moduleInfoCode);
        checkArgument(isValidModuleInfoCode(moduleInfoCode), MESSAGE_CONSTRAINTS);
        value = moduleInfoCode;
    }

    public static boolean isValidModuleInfoCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the level of the module represented by this {@code ModuleInfoCode}.
     * @return The first numerical digit of this {@code ModuleInfoCode}.
     */
    public int getLevel() {
        int codeNumber = Integer.parseInt(value.replaceAll("[^0-9]", ""));
        return codeNumber / 1000;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleInfoCode// instanceof handles nulls
                && value.equals(((ModuleInfoCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
