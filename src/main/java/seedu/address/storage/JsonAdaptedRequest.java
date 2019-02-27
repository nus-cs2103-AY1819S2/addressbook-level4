package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.request.Request;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedRequest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String id;
    private final String patient;
    private final String requestDate;
    private final List<String> conditions = new ArrayList<>();
    private final String healthStaff;
    private final String isCompleted;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedRequest(@JsonProperty("id") String id, @JsonProperty("patient") String patient,
                              @JsonProperty("requestdate") String requestDate,@JsonProperty("healthstaff") String healthStaff,
                              @JsonProperty("conditions") List<String> conditions , @JsonProperty("isCompleted") String isCompleted) {
        this.id = id;
        this.patient = patient;
        this.requestDate = requestDate;
        if (conditions != null) {
            this.conditions.addAll(conditions);
        }
        this.healthStaff = healthStaff;
        this.isCompleted = isCompleted;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedRequest(Request source) {
        this.id = source.getId();
        this.patient = source.getPatient().getNric().toString();
        this.conditions.addAll(source.getConditions());
        this.requestDate = source.getRequestDate();
        this.isCompleted = Boolean.toString(source.isComplete());
        this.healthStaff = source.getHealthStaff().getNric().toString();
        this.conditions.addAll(source.getConditions());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Request toModelType() throws IllegalValueException {
        final List<String> conditionsArray = new ArrayList<>();

        conditionsArray.addAll(conditions);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        final String modelID = this.id;


        if (patient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }

        final Person modelPatient = JsonSerializableAddressBook.personHashMap.get(patient);


        final Person modelHealthStaff = JsonSerializableAddressBook.personHashMap.get(healthStaff);

        if (requestDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }

        final String modelrequestDate = requestDate;

        if (isCompleted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        final Boolean modelIscompleted = Boolean.getBoolean(isCompleted);

        final Set<String> modelConditions = new HashSet<>(conditionsArray);

        return new Request(modelID,modelPatient,modelrequestDate,modelConditions,modelIscompleted);
    }

}
