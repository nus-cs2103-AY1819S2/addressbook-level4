package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.JobsApply;

/**
 * Jackson-friendly version of {@link JobsApply}.
 */
class JsonAdaptedJobsApply {

    private final String jobsApplyName;

    /**
     * Constructs a {@code JsonAdaptedJobsApply} with the given {@code jobsApplyName}.
     */
    @JsonCreator
    public JsonAdaptedJobsApply(String jobsApplyName) {
        this.jobsApplyName = jobsApplyName;
    }

    /**
     * Converts a given {@code JobsApply} into this class for Jackson use.
     */
    public JsonAdaptedJobsApply(JobsApply source) {
        jobsApplyName = source.value;
    }

    @JsonValue
    public String getJobsApplyName() {
        return jobsApplyName;
    }

    /**
     * Converts this Jackson-friendly adapted jobs apply object into the model's {@code JobsApply} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted jobsApply.
     */
    public JobsApply toModelType() throws IllegalValueException {
        if (!JobsApply.isValidJobsApply(jobsApplyName)) {
            throw new IllegalValueException(JobsApply.MESSAGE_CONSTRAINTS);
        }
        return new JobsApply(jobsApplyName);
    }

}
