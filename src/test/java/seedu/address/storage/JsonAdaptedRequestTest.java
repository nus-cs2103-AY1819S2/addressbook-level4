package seedu.address.storage;

//import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
//import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
//import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.testutil.Assert;

public class JsonAdaptedRequestTest {
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_NRIC = "2193213";
    private static final String INVALID_DATE = "1st Jan";
    private static final String INVALID_STATUS = "Busy";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ADDRESS = " ";

    //TODO: methods need reviewing
    /*
    @Test
    public void toModelType_validRequestDetails_returnsRequest() throws Exception {
        JsonAdaptedRequest request = new JsonAdaptedRequest(BENSON_REQUEST);
        Request req = request.toModelType();
        assertEquals(req, BENSON_REQUEST);
    }

    @Test
    public void toModelType_validRequestStrings_returnsRequest() throws Exception {
        JsonAdaptedRequest request = new JsonAdaptedRequest(ALICE_REQUEST.getName().fullName,
            ALICE_REQUEST.getNric().toString(), ALICE_REQUEST.getPhone().value,
            ALICE_REQUEST.getAddress().value,
            ALICE_REQUEST.getRequestDate().toString(), ALICE_REQUEST.getConditions().toString(),
            new RequestStatus("PENDING").toString(), ALICE_REQUEST.getHealthStaff());
        Request req = request.toModelType();
        assertEquals(req, ALICE_REQUEST);
    }
    */

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(INVALID_NAME,
            ALICE_REQUEST.getNric().toString(), ALICE_REQUEST.getPhone().value,
            ALICE_REQUEST.getAddress().value,
            ALICE_REQUEST.getRequestDate().toString(), ALICE_REQUEST.getConditions().toString(),
            new RequestStatus("PENDING").toString(), ALICE_REQUEST.getHealthStaff());
        Assert.assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, request::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(ALICE_REQUEST.getName().toString(),
            INVALID_NRIC, ALICE_REQUEST.getPhone().value,
            ALICE_REQUEST.getAddress().value,
            ALICE_REQUEST.getRequestDate().toString(), ALICE_REQUEST.getConditions().toString(),
            new RequestStatus("PENDING").toString(), ALICE_REQUEST.getHealthStaff());
        Assert.assertThrows(IllegalValueException.class, Nric.MESSAGE_CONSTRAINTS,
            request::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(ALICE_REQUEST.getName().toString(),
            INVALID_NRIC, INVALID_PHONE,
            ALICE_REQUEST.getAddress().value,
            ALICE_REQUEST.getRequestDate().toString(), ALICE_REQUEST.getConditions().toString(),
            new RequestStatus("PENDING").toString(), ALICE_REQUEST.getHealthStaff());
        Assert.assertThrows(IllegalValueException.class, Phone.MESSAGE_CONSTRAINTS,
            request::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(ALICE_REQUEST.getName().toString(),
            ALICE_REQUEST.getNric().getNric(), ALICE_REQUEST.getPhone().value,
            INVALID_ADDRESS,
            ALICE_REQUEST.getRequestDate().toString(), ALICE_REQUEST.getConditions().toString(),
            new RequestStatus("PENDING").toString(), ALICE_REQUEST.getHealthStaff());
        Assert.assertThrows(IllegalValueException.class, Address.MESSAGE_CONSTRAINTS,
            request::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(ALICE_REQUEST.getName().toString(),
            ALICE_REQUEST.getNric().getNric(), ALICE_REQUEST.getPhone().value,
            ALICE_REQUEST.getAddress().value,
            INVALID_DATE, ALICE_REQUEST.getConditions().toString(),
            new RequestStatus("PENDING").toString(), ALICE_REQUEST.getHealthStaff());
        Assert.assertThrows(IllegalValueException.class, RequestDate.MESSAGE_DATE_CONSTRAINTS,
            request::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedRequest request = new JsonAdaptedRequest(ALICE_REQUEST.getName().toString(),
            ALICE_REQUEST.getNric().getNric(), ALICE_REQUEST.getPhone().value,
            ALICE_REQUEST.getAddress().value,
            ALICE_REQUEST.getRequestDate().toString(), ALICE_REQUEST.getConditions().toString(),
            INVALID_STATUS, ALICE_REQUEST.getHealthStaff());
        Assert.assertThrows(IllegalValueException.class, RequestStatus.MESSAGE_STATUS_CONSTRAINTS,
            request::toModelType);
    }
}
