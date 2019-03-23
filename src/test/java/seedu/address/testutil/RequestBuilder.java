package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.tag.Condition;

/**
 * A utility class to help with building Request objects.
 */
public class RequestBuilder {
    public static final String DEFAULT_ID = null;
    public static final String DEFAULT_PATIENT_NAME = "Amanda Tan";
    public static final String DEFAULT_PATIENT_PHONE = "85355255";
    public static final String DEFAULT_PATIENT_EMAIL = "amandatan@test.com";
    public static final String DEFAULT_PATIENT_NRIC = "S9670515H";
    public static final String DEFAULT_PATIENT_ADDRESS = "123, Far East Ave 3, #04-123, 123456";
    public static final String DEFAULT_STATUS = "PENDING";
    public static final String DEFAULT_DATE = "01-01-2019 08:00:00";
    public static final String DEFAULT_REQUEST = "Physiotherapy";
    public static final String DEFAULT_STAFF_NAME = "John Doe";
    public static final String DEFAULT_STAFF_PHONE = "81237822";
    public static final String DEFAULT_STAFF_ADDRESS = "Health Hub Ave 3, 129834";
    public static final String DEFAULT_STAFF_EMAIL = "healthstaff@health.com";
    public static final String DEFAULT_STAFF_NRIC = "S9123742I";
    public static final String DEFAULT_ORGANISATION = "NUH";

    private RequestDate requestDate;
    private RequestStatus requestStatus;
    private Set<Condition> conditions;
    private String healthWorker;
    private Name name;
    private Phone phone;
    private Nric nric;
    private Address address;


    public RequestBuilder() {
        this.name = new Name(DEFAULT_PATIENT_NAME);
        this.phone = new Phone(DEFAULT_PATIENT_PHONE);
        this.nric = new Nric(DEFAULT_PATIENT_NRIC);
        this.address = new Address(DEFAULT_PATIENT_ADDRESS);
        this.healthWorker = DEFAULT_STAFF_NAME;
        this.requestDate = new RequestDate(DEFAULT_DATE);
        this.requestStatus = new RequestStatus(DEFAULT_STATUS);
        this.conditions = new HashSet<>(Arrays.asList(new Condition(DEFAULT_REQUEST)));
    }

    /*
     * Initializes the RequestBuilder with the data of {@code requestToCopy}.
     */
    public RequestBuilder(Request requestToCopy) {
        this.name = new Name(DEFAULT_PATIENT_NAME);
        this.phone = new Phone(DEFAULT_PATIENT_PHONE);
        this.nric = new Nric(DEFAULT_PATIENT_NRIC);
        this.address = new Address(DEFAULT_PATIENT_ADDRESS);
        this.healthWorker = requestToCopy.getHealthStaff();
        this.conditions = requestToCopy.getConditions();
        this.requestDate = requestToCopy.getRequestDate();
        this.requestStatus = requestToCopy.getRequestStatus();
    }

    /**
     * Sets the {@code id} of the {@code Request} that we are building.
     *
     * @param id The id of the request.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withId(String id) {
        return this;
    }

    /**
     * Sets the {@code nric} of the {@code Patient} object in the request that we are building.
     *
     * @param nric the nric of the patient to set
     * @return The RequestBuilder object
     */
    public RequestBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code requestDate} of the {@code Request} that we are building
     *
     * @param date the date of the request.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withDate(String date) {
        this.requestDate = new RequestDate(date);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} in the request that we are building.
     *
     * @param phone the phone number of the patient
     * @return The RequestBuilder object
     */
    public RequestBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code address} of the {@code patient} that we are building
     *
     * @param address the address of the patient
     * @return The RequestBuilder object
     */
    public RequestBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code healthStaff} of the {@code Request} we are building.
     */
    public RequestBuilder withHealthStaff(HealthWorker healthStaff) {
        requireNonNull(healthStaff);
        this.healthWorker = healthStaff.getName().fullName;
        return this;
    }

    /**
     * Sets the {@code healthStaff} of the {@code Request} that we are building
     */
    public RequestBuilder withHealthWorker(String healthWorker) {
        requireNonNull(healthWorker);
        this.healthWorker = healthWorker;
        return this;
    }

    /**
     * Sets the {@code conditions} of the patient in the {@code Request} we are building.
     */
    public RequestBuilder withConditions(Set<Condition> conditions) {
        requireNonNull(conditions);
        this.conditions = conditions;
        return this;
    }

    /**
     * Sets the {@code healthStaff} of the {@code Request} we are building.
     */
    public RequestBuilder withStatus(String status) {
        this.requestStatus = new RequestStatus(status);
        return this;
    }

    /**
     * Sets the {@code name} of the patient in the {@code Request} we are building
     */
    public RequestBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Builds and returns the request.
     */
    public Request build() {
        return new Request(name, nric, phone, address, requestDate, conditions, requestStatus,
            healthWorker);
    }

}
