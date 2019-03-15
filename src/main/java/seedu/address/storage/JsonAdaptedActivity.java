package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityDateTime;
import seedu.address.model.activity.ActivityName;


/**
 * Jackson-friendly version of {@link Activity}.
 */
public class JsonAdaptedActivity {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";

    private final String name;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedActivity} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("name") String name, @JsonProperty("time") String time) {
        this.name = name;
        this.time = time;
    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        name = source.getName().fullActivityName;
        time = "To be updated when time class written";
    }

    /**
     * Converts this Jackson-friendly adapted activity object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Activity toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ActivityName.class.getSimpleName()));
        }
        if (!ActivityName.isValidActivityName(name)) {
            throw new IllegalValueException(ActivityName.MESSAGE_CONSTRAINTS);
        }
        final ActivityName modelName = new ActivityName(name);

        /*ActivityDateTime to be updated
        *         if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
            ActivityDateTime.class.getSimpleName()));
        }
        if (!ActivityDateTime.isValidActivityTime(time)) {
            throw new IllegalValueException(ActivityTime.MESSAGE_CONSTRAINTS);
        }*/

        final ActivityDateTime modelDateTime = new ActivityDateTime(time);

        return new Activity(modelName, modelDateTime);
    }

}
