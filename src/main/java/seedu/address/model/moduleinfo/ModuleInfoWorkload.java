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
    public static final String VALIDATION_REGEX = "\\d{1,2}-\\d{1,2}-\\d{1,2}-\\d{1,2}-\\d{1,2}";
    public final String workload;
    public final int lecture;
    public final int tutorial;
    public final int lab;
    public final int project;
    public final int preparation;

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

            this.lecture = Integer.valueOf(attribute[0]);
            this.tutorial = Integer.valueOf(attribute[1]);
            this.lab = Integer.valueOf(attribute[2]);
            this.project = Integer.valueOf(attribute[3]);
            this.preparation = Integer.valueOf(attribute[4]);
        }

    }

    public static boolean isValidModuleInfoWorkload(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String toString() {
        return workload;
    }

    public int getLecture() {
        return lecture;
    }

    public int getTutorial() {
        return tutorial;
    }

    public int getLab() {
        return lab;
    }

    public int getProject() {
        return project;
    }

    public int getPreparation() {
        return preparation;
    }

    public int getTotal() {
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
