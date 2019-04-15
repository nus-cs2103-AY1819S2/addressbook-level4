package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.ProjectName;

/**
 * Jackson-friendly version of {@link seedu.address.model.project.ProjectName}.
 */
class JsonAdaptedProjectName {

    private final String projectName;

    /**
     * Constructs a {@code JsonAdaptedProjectName} with the given {@code projectName}.
     */
    @JsonCreator
    public JsonAdaptedProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Converts a given {@code ProjectName} into this class for Jackson use.
     */
    public JsonAdaptedProjectName(ProjectName source) {
        projectName = source.projectName;
    }

    @JsonValue
    public String getProjectName() {
        return projectName;
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public ProjectName toModelType() throws IllegalValueException {
        if (!ProjectName.isValidName(projectName)) {
            throw new IllegalValueException(ProjectName.MESSAGE_CONSTRAINTS);
        }
        return new ProjectName(projectName);
    }

}
