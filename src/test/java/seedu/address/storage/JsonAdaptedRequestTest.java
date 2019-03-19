package seedu.address.storage;

//import static org.junit.Assert.assertEquals;
//import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
//import static seedu.address.testutil.TypicalPatients.BENSON;
//import static seedu.address.testutil.TypicalRequests.BENSON_ID;
//import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.junit.Test;
//
//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.model.person.Person;
//import seedu.address.model.person.Phone;
//import seedu.address.model.person.healthworker.Organization;
//import seedu.address.model.request.Request;
//import seedu.address.model.request.RequestDate;
//import seedu.address.model.request.RequestStatus;
//import seedu.address.testutil.Assert;

public class JsonAdaptedRequestTest {
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATE = "1st Jan";
    private static final String INVALID_STATUS = "Busy";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_DATE = BENSON_REQUEST.getRequestDate().toString();
    private static final String VALID_ID = BENSON_ID;
    private static final String VALID_STATUS = BENSON_REQUEST.getRequestStatus().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SKILLS = BETTY.getSkills().toString();
    private static final String VALID_NRIC = BETTY.getNric().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON_REQUEST.getConditions().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());


    @Test
    public void toModelType_validRequestDetails_returnsRequest() throws Exception {
        JsonAdaptedRequest request = new JsonAdaptedRequest(BENSON_REQUEST);
        Request req = request.toModelType();
        assertEquals(req, BENSON_REQUEST);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(null, new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS),
                VALID_DATE, new JsonAdaptedHealthWorker(BETTY), VALID_TAGS,
                VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedRequest.MISSING_FIELD_MESSAGE_FORMAT, "id");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }

    @Test
    public void toModelType_invalidPatient_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS),
                VALID_DATE, new JsonAdaptedHealthWorker(BETTY), VALID_TAGS,
                VALID_STATUS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }

    @Test
    public void toModelType_nullPatient_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, null, VALID_DATE,
                new JsonAdaptedHealthWorker(BETTY), VALID_TAGS, VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedRequest.MISSING_FIELD_MESSAGE_FORMAT,
                Person.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS),
                INVALID_DATE, new JsonAdaptedHealthWorker(BETTY), VALID_TAGS,
                VALID_STATUS);
        String expectedMessage = RequestDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_NRIC,
                VALID_ADDRESS, VALID_TAGS), null, new JsonAdaptedHealthWorker(BETTY),
                VALID_TAGS, VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedRequest.MISSING_FIELD_MESSAGE_FORMAT,
                RequestDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }

    @Test
    public void toModelType_invalidHealthWorker_throwsIllegalValueException() {
        JsonAdaptedHealthWorker person = new JsonAdaptedHealthWorker(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_ADDRESS, VALID_TAGS, null, VALID_SKILLS);
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS), VALID_DATE, person, VALID_TAGS,
                VALID_STATUS);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                Organization.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS),
                VALID_DATE, new JsonAdaptedHealthWorker(BETTY), invalidTags,
                VALID_STATUS);
        Assert.assertThrows(IllegalValueException.class, request::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS),
                VALID_DATE, new JsonAdaptedHealthWorker(BETTY), VALID_TAGS,
                INVALID_STATUS);
        String expectedMessage = RequestStatus.MESSAGE_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(VALID_ID, new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS), VALID_DATE, new JsonAdaptedHealthWorker(BETTY),
                VALID_TAGS, null);
        String expectedMessage = String.format(JsonAdaptedRequest.MISSING_FIELD_MESSAGE_FORMAT,
                RequestStatus.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, request::toModelType);
    }
}
