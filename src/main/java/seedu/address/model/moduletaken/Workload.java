package seedu.address.model.moduletaken;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.moduleinfo.ModuleInfoWorkload;

/**
 * Represents the expected Workload of module selected as defined by the user
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Workload {

    private final Hour lectureHour;
    private final Hour tutorialHour;
    private final Hour labHour;
    private final Hour projectHour;
    private final Hour preparationHour;

    /**
     * For constructing a Workload based on converted information from ModuleInfoWorkload
     */
    public Workload(ModuleInfoWorkload moduleInfoWorkload) {
        this.lectureHour = new Hour(String.valueOf(moduleInfoWorkload.getLecture()));
        this.tutorialHour = new Hour(String.valueOf(moduleInfoWorkload.getTutorial()));
        this.labHour = new Hour(String.valueOf(moduleInfoWorkload.getLab()));
        this.projectHour = new Hour(String.valueOf(moduleInfoWorkload.getProject()));
        this.preparationHour = new Hour(String.valueOf(moduleInfoWorkload.getPreparation()));
    }

    /**
     * For populating the Module Taken with a default workload when the information is not provided.
     */
    public Workload() {
        this.lectureHour = new Hour("0");
        this.tutorialHour = new Hour("0");
        this.labHour = new Hour("0");
        this.projectHour = new Hour("0");
        this.preparationHour = new Hour("0");
    }

    public Workload(Hour lectureHour, Hour tutorialHour,
                  Hour labHour, Hour projectHour,
                  Hour preparationHour) {
        requireAllNonNull(lectureHour, tutorialHour, labHour,
                projectHour, preparationHour);
        this.lectureHour = lectureHour;
        this.tutorialHour = tutorialHour;
        this.labHour = labHour;
        this.projectHour = projectHour;
        this.preparationHour = preparationHour;
    }

    public Hour getLectureHour() {
        return lectureHour;
    }

    public Hour getTutorialHour() {
        return tutorialHour;
    }

    public Hour getLabHour() {
        return labHour;
    }

    public Hour getProjectHour() {
        return projectHour;
    }

    public Hour getPreparationHour() {
        return preparationHour;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Workload)) {
            return false;
        }

        Workload otherWorkload = (Workload) other;
        return other != null
                && otherWorkload.getLectureHour().equals(getLectureHour())
                && otherWorkload.getTutorialHour().equals(getTutorialHour())
                && otherWorkload.getLabHour().equals(getLabHour())
                && otherWorkload.getProjectHour().equals(getProjectHour())
                && otherWorkload.getPreparationHour().equals(getPreparationHour());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(lectureHour, tutorialHour, labHour,
                projectHour, preparationHour);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Lecture hours expected: \n")
                .append(getLectureHour())
                .append(" Tutorial hours expected: \n")
                .append(getTutorialHour())
                .append(" Lab hours expected: \n")
                .append(getLabHour())
                .append(" Project hours expected: \n")
                .append(getProjectHour())
                .append(" Preparation hours expected: \n")
                .append(getPreparationHour());
        return builder.toString();
    }
}
