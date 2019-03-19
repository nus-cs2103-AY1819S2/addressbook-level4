package seedu.address.storage;

//import java.util.ArrayList;
import java.util.HashSet;
//import java.util.List;
import java.util.Optional;
import java.util.Set;
//import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;

/**
 * Jackson-friendly version of {@link Request}.
 */
class JsonAdaptedRequest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Request's %s field is missing!";

    private final String id;
    private final JsonAdaptedPatient patient;
    private final String requestDate;
    private final String conditions;
    private final JsonAdaptedHealthWorker healthWorker;

    private final String requestStatus;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedRequest(@JsonProperty("id") String id,
                              @JsonProperty("patient") JsonAdaptedPatient patient,
                              @JsonProperty("requestdate") String requestDate,
                              @JsonProperty("healthworker") JsonAdaptedHealthWorker healthWorker,
                              @JsonProperty("conditions") String conditions,
                              @JsonProperty("requestStatus") String requestStatus) {
        this.id = id;
        this.patient = patient;
        this.requestDate = requestDate;
        this.conditions = conditions;

        this.healthWorker = healthWorker;
        this.requestStatus = requestStatus;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedRequest(Request source) {
        this.id = source.getId();
        this.patient = new JsonAdaptedPatient(source.getPatient());

        this.requestDate = source.getRequestDate().toString();
        this.requestStatus = source.getRequestStatus().toString();
        this.conditions = source.getConditions().toString();
        Optional<HealthWorker> hw = source.getHealthStaff();
        this.healthWorker = hw.map(JsonAdaptedHealthWorker::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Request toModelType() throws IllegalValueException {


        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "id"));
        }

        final String modelId = this.id;


        if (patient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Patient.class.getSimpleName()));
        }

        final Patient modelPatient = this.patient.toModelType();
        final HealthWorker modelHealthStaff;
        if (healthWorker != null) {
            modelHealthStaff = this.healthWorker.toModelType();
        } else {
            modelHealthStaff = null;
        }
        if (requestDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RequestDate.class.getSimpleName()));
        }

        if (!RequestDate.isValidDate(requestDate)) {
            throw new IllegalValueException(RequestDate.MESSAGE_DATE_CONSTRAINTS);
        }

        final RequestDate modelrequestDate = new RequestDate(this.requestDate);

        if (requestStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RequestStatus.class.getSimpleName()));
        }

        if (!RequestStatus.isValidStatus(requestStatus)) {
            throw new IllegalValueException(RequestStatus.MESSAGE_STATUS_CONSTRAINTS);
        }

        final RequestStatus modelrequestStatus = new RequestStatus(this.requestStatus);

        Set<ConditionTag> set = new HashSet<>();
        String[] conditionsArr = this.conditions.split(" ");
        for (String condition : conditionsArr) {
            ConditionTag conditionTag = ConditionTag.parseString(condition);
            set.add(conditionTag);
        }
        final Conditions modelConditions = new Conditions(set);


        if (modelHealthStaff == null) {
            return new Request(modelId, modelPatient, modelrequestDate, modelConditions, modelrequestStatus);
        }
        return new Request(modelId, modelPatient, modelHealthStaff,
                modelrequestDate, modelConditions, modelrequestStatus);
    }

}
