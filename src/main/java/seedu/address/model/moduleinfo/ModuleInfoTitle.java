package seedu.address.model.moduleinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a moduleInfo's title
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleInfoTitle(String)}
 */
public class ModuleInfoTitle {
    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of at least 1 word and cannot start with whitespace";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private final String title;

    public ModuleInfoTitle(String moduleTitle) {
        requireNonNull(moduleTitle);
        checkArgument(isValidModuleInfoTitle(moduleTitle), MESSAGE_CONSTRAINTS);
        title = moduleTitle;
    }

    public static boolean isValidModuleInfoTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleInfoTitle// instanceof handles nulls
                && title.equals(((ModuleInfoTitle) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
