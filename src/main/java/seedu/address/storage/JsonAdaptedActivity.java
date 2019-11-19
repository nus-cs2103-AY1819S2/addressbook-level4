package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityDateTime;
import seedu.address.model.activity.ActivityDescription;
import seedu.address.model.activity.ActivityLocation;
import seedu.address.model.activity.ActivityName;
import seedu.address.model.person.MatricNumber;


/**
 * Jackson-friendly version of {@link Activity}.
 */
public class JsonAdaptedActivity {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";

    private final String name;
    private final String time;
    private final String location;
    private final String description;
    private final List<JsonAdaptedMatric> attendance = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedActivity} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("name") String name, @JsonProperty("time") String time,
                               @JsonProperty("location") String location,
                               @JsonProperty("description") String description,
                               @JsonProperty("attendance") List<JsonAdaptedMatric> attendance) {
        this.name = name;
        this.time = time;
        this.location = location;
        this.description = description;
        if (attendance != null) {
            this.attendance.addAll(attendance);
        }
    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        name = source.getName().fullActivityName;
        time = source.getDateTime().fullDateTime;
        location = source.getLocation().value;
        description = source.getDescription().value;
        attendance.addAll(source.getAttendance().stream()
                .map(JsonAdaptedMatric::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted activity object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Activity toModelType() throws IllegalValueException {
        final List<MatricNumber> attending = new ArrayList<>();
        for (JsonAdaptedMatric matric : attendance) {
            attending.add(matric.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ActivityName.class.getSimpleName()));
        }
        if (!ActivityName.isValidActivityName(name)) {
            throw new IllegalValueException(ActivityName.MESSAGE_CONSTRAINTS);
        }
        final ActivityName modelName = new ActivityName(name);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
            ActivityDateTime.class.getSimpleName()));
        }
        if (!ActivityDateTime.isValidActivityDateTime(time)) {
            throw new IllegalValueException(ActivityDateTime.MESSAGE_CONSTRAINTS);
        }

        final ActivityDateTime modelDateTime = new ActivityDateTime(time);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ActivityLocation.class.getSimpleName()));
        }
        if (!ActivityLocation.isValidLocation(location)) {
            throw new IllegalValueException(ActivityLocation.MESSAGE_CONSTRAINTS);
        }

        final ActivityLocation modelLocation = new ActivityLocation(location);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ActivityDescription.class.getSimpleName()));
        }
        if (!ActivityDescription.isValidDescription(description)) {
            throw new IllegalValueException(ActivityDescription.MESSAGE_CONSTRAINTS);
        }

        final ActivityDescription modelDescription = new ActivityDescription(description);

        return new Activity(modelName, modelDateTime, modelLocation, modelDescription, attending);
    }
}
