package seedu.address.model.moduleinfo;

import java.util.Objects;

/**
 * Represents a moduleInfo's Workload
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleInfoWorkload(String)}
 */
public class ModuleInfoWorkload {
    public static final String MESSAGE_CONSTRAINTS =
            "Module Workload has 5 fields of Integers";
    public static final String MESSAGE_NOWORKLOAD =
            "No work load information provided";
    public static final String VALIDATION_INTEGER_REGEX = "\\d\\.?\\d?-"
            + "\\d\\.?\\d?-"
            + "\\d\\.?\\d?-"
            + "\\d\\.?\\d?-"
            + "\\d\\.?\\d?";

    public final String workload;
    public final double lecture;
    public final double tutorial;
    public final double lab;
    public final double project;
    public final double preparation;

    public ModuleInfoWorkload(String moduleInfoWorkload) {
        this.workload = moduleInfoWorkload;
        if (!isValidModuleInfoWorkload(moduleInfoWorkload)) {
            this.lecture = 0;
            this.tutorial = 0;
            this.lab = 0;
            this.project = 0;
            this.preparation = 0;
        } else {
            String[] attribute = splitWorkload(moduleInfoWorkload);
            this.lecture = Double.valueOf(attribute[0]);
            this.tutorial = Double.valueOf(attribute[1]);
            this.lab = Double.valueOf(attribute[2]);
            this.project = Double.valueOf(attribute[3]);
            this.preparation = Double.valueOf(attribute[4]);
        }

    }

    public static boolean isValidModuleInfoWorkload(String test) {
        return test.matches(VALIDATION_INTEGER_REGEX);
    }

    public String toString() {
        return workload;
    }

    public double getLecture() {
        return lecture;
    }

    public double getTutorial() {
        return tutorial;
    }

    public double getLab() {
        return lab;
    }

    public double getProject() {
        return project;
    }

    public double getPreparation() {
        return preparation;
    }

    public double getTotal() {
        return lecture + tutorial + lab + project + preparation;
    }

    /**
     *  Splits the moduleinfo workload into 5 attribute and returns a string array
     * @param moduleInfoWorkload
     * @return String[] attributes
     */
    public String[] splitWorkload(String moduleInfoWorkload) {
        String[] attributes = moduleInfoWorkload.split("-");
        return attributes;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleInfoWorkload// instanceof handles nulls
                && lecture == ((ModuleInfoWorkload) other).lecture
                && tutorial == ((ModuleInfoWorkload) other).tutorial
                && lab == ((ModuleInfoWorkload) other).lab
                && project == ((ModuleInfoWorkload) other).project
                && preparation == ((ModuleInfoWorkload) other).preparation); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(lecture, tutorial, lab, project, preparation);
    }
}
