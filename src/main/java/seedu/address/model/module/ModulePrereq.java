package seedu.address.model.module;

import java.util.LinkedList;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class ModulePrereq {

    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of at least 1 word and cannot begin with whitespace";
    public static final String VALIDATION_REGEX  = "[^\\s].*";
    public final String prereqDescription;
    public final LinkedList<Module>  prereq;
    /*
    LinkedList of Modules is not the most best / accurate way of checking
    pre-requisite but is sufficient to act as a placeholder for v1.1
     */

    public ModulePrereq(String prereqDescription, LinkedList<Module> prereq) {
        requireNonNull(prereqDescription);
        requireNonNull(prereq);
        checkArgument(isValidModulePrereq(prereqDescription), MESSAGE_CONSTRAINTS);
        this.prereqDescription = prereqDescription;
        this.prereq = prereq;
    }


    public static boolean isValidModulePrereq(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String toString() {
        return prereqDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModulePrereq// instanceof handles nulls
                && prereqDescription.equals(((ModulePrereq) other).prereqDescription)); // state check
    }

    @Override
    public int hashCode() {
        return prereqDescription.hashCode();
    }
}
