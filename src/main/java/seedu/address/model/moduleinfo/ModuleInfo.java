package seedu.address.model.moduleinfo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.module.Module;

/**
 * Represents Module Information about a particular module available in NUS.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModuleInfo {

    private final String code;
    private final String title;
    private final double credits;
    private final String description;
    private final String workload;
    private final String preclusions;
    private final String department;
    private final String prerequisites;


    public ModuleInfo(String code, String title, double credits,
                      String description, String workload, String preclusions,
                      String department, String prerequisites) {
        requireAllNonNull(code, title, credits, description, department, prerequisites,
                preclusions, workload);
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.description = description;
        this.workload = workload;
        this.preclusions = preclusions;
        this.department = department;
        this.prerequisites = prerequisites;


        //System.out.println("Module:" + code + " has been created");
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public double getCredits() {
        return this.credits;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getPrerequisites() {
        return this.prerequisites;
    }

    public String getPreclusions() {
        return this.preclusions;
    }

    public String getWorkload() {
        return this.workload;
    }

    /**
     * Returns true if both modules have same module code or same module title.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(ModuleInfo other) {
        if (other == this) {
            return true;
        }

        return other != null
                && (other.code.equals(code) || other.title.equals(title));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder .append(" Module Code: ")
                .append(getCode())
                .append(" Module Title: ")
                .append(getTitle())
                .append(" Module Credits")
                .append(getCredits())
                .append(" Module Description: ")
                .append(getDescription())
                .append(" Department: ")
                .append(getDepartment())
                .append(" Prerequisites: ")
                .append(getPrerequisites())
                .append(" Preclusions: ")
                .append(getPreclusions())
                .append(" Workload: ")
                .append(getWorkload());

        return builder.toString();
    }
}
