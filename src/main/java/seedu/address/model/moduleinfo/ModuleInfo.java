package seedu.address.model.moduleinfo;

/**
 * Represents Module Information about a particular module available in NUS.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModuleInfo {
    private ModuleInfoCode code;
    private ModuleInfoTitle title;
    private ModuleInfoCredits credits;
    private ModuleInfoDescription description;
    private String workLoad;
    private String preclusions;
    private ModuleInfoDepartment department;
    private String prerequisites;

    public ModuleInfo(String code, String title, double credits, String description, String workLoad,
                      String preclusions, String department, String prerequisites) {
        this.code = new ModuleInfoCode(code);
        this.title = new ModuleInfoTitle(title);
        this.credits = new ModuleInfoCredits(credits);
        this.description = new ModuleInfoDescription(description);
        this.workLoad = workLoad;
        this.preclusions = preclusions;
        this.department = new ModuleInfoDepartment(department);
        this.prerequisites = prerequisites;

        System.out.println("Module:" + code + " has been created");
    }

    public String getCode() {
        return this.code.toString();
    }

    public String getTitle() {
        return this.title.toString();
    }

    public double getCredits() {
        return this.credits.getCredits();
    }

    public String getDescription() {
        return this.description.toString();
    }

    public String getWorkLoad() {
        return this.workLoad;
    }

    public String getPreclusions() {
        return this.preclusions;
    }

    public String getDepartment() {
        return this.department.toString();
    }

    public String getPrerequisites() {
        return this.prerequisites;
    }
}
