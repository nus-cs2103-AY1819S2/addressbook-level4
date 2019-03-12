package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PastJob;

/**
 * Jackson-friendly version of {@link PastJob}.
 */
class JsonAdaptedPastJob {

    private final String pastjobName;

    /**
     * Constructs a {@code JsonAdaptedPastJob} with the given {@code pastjobName}.
     */
    @JsonCreator
    public JsonAdaptedPastJob(String pastjobName) {
        this.pastjobName = pastjobName;
    }

    /**
     * Converts a given {@code PastJob} into this class for Jackson use.
     */
    public JsonAdaptedPastJob(PastJob source) {
        pastjobName = source.value;
    }

    @JsonValue
    public String getPastJobName() {
        return pastjobName;
    }

    /**
     * Converts this Jackson-friendly adapted pastjob object into the model's {@code PastJob} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pastjob.
     */
    public PastJob toModelType() throws IllegalValueException {
        if (!PastJob.isValidPastJob(pastjobName)) {
            throw new IllegalValueException(PastJob.MESSAGE_CONSTRAINTS);
        }
        return new PastJob(pastjobName);
    }

}
