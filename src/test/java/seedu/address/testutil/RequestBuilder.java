package seedu.address.testutil;

import seedu.address.model.person.*;
import seedu.address.model.request.Request;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class RequestBuilder {
    public static final String DEFAULT_ID = "1233";
    public static final String DEFAULT_PATIENT_NAME = "Amanda Tan";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Far East Ave 3, #04-123," +
            " 123456";
    public static final boolean DEFAULT_ISCOMPLETE = false;
    public static final String DEFAULT_DATE = "01-01-2019 08:00:00";
    public static final String DEFAULT_REQUEST = "Physiotherapy";
    public static final String DEFAULT_EMAIL = "amandatan@test.com";
    public static final String DEFAULT_STAFF_NAME = "John Doe";
    public static final String DEFAULT_STAFF_PHONE = "81237822";
    public static final String DEFAULT_STAFF_ADDRESS = "Health Hub Ave 3, 129834";
    public static final String DEFAULT_STAFF_EMAIL = "healthstaff@health.com";
    public static final String DEFAULT_STAFF_TAG = "Specialisation: " +
            "Physiotherapy";
    private String id;
    private String requestDate;
    private boolean isComplete;
    private Set<Tag> conditions;
    private Optional<Person> healthStaff;
    private Person patient;

    /*
     * A utility class to help with building Request objects.
     */
    public RequestBuilder() {
        id = DEFAULT_ID;
        patient = new Person(new Name(DEFAULT_PATIENT_NAME), new Phone
                (DEFAULT_PHONE), new Email(DEFAULT_EMAIL), new Address
                (DEFAULT_ADDRESS), new HashSet<>(Collections.singleton(new Tag
                (DEFAULT_REQUEST))));
        healthStaff = Optional.of(new Person(new Name(DEFAULT_STAFF_NAME), new Phone
                (DEFAULT_STAFF_PHONE), new Email(DEFAULT_STAFF_EMAIL), new Address
                (DEFAULT_STAFF_ADDRESS), new HashSet<>(Collections.singleton(new Tag
                (DEFAULT_STAFF_TAG)))));
        requestDate = DEFAULT_DATE;
        conditions = new HashSet<>(Collections.singleton(new Tag(DEFAULT_REQUEST)));
        isComplete = DEFAULT_ISCOMPLETE;
    }

    /*
     * Initializes the RequestBuilder with the data of {@code requestToCopy}.
     */
    public RequestBuilder(Request requestToCopy) {
        id = requestToCopy.getId();
        patient = requestToCopy.getPatient();
        healthStaff = requestToCopy.getHealthStaff();
        conditions = requestToCopy.getConditions();
        requestDate = requestToCopy.getRequestDate();
    }

    /*
    -     * Sets the {@code id} of the {@code Request} that we are building.
    -     *
    -     * @param id The id of the order.
    -     */
    public RequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /*
     * Sets the {@code requestDate} of the {@code Request} that we are building.
     */
    public RequestBuilder withDate(String date) {
        this.requestDate = date;
        return this;
    }

    /*
     * Sets the {@code patient} of the {@code Request} we are building.
     */
    public RequestBuilder withPatient(Person person) {
        this.patient = person;
        return this;
    }

    /*
     * Sets the {@code healthStaff} of the {@code Request} we are building.
     */
    public RequestBuilder withHealthStaff(Person healthStaff) {
        requireNonNull(healthStaff);
        this.healthStaff = Optional.of(healthStaff);
        return this;
    }

    /*
     * Parses the {@code conditions} into a {@code Set<Tag} and set it to the {@code Request} that we are building.
     */
    public RequestBuilder withConditions(String... conditions) {
        this.conditions = SampleDataUtil.getTagSet(conditions);
        return this;
    }

    /*
     * Sets the {@code isComplete} status of the {@code Request} we are building.
     */
    public RequestBuilder withStatus(boolean isComplete) {
        this.isComplete = isComplete;
        return this;
    }

    /**
     * Builds and returns the order.
     */
    public Request build() {
        return this.healthStaff.map(person -> new Request(this.id, this.patient, person, this.requestDate, this.conditions, this.isComplete))
                .orElseGet(() -> new Request(this.id, this.patient, this.requestDate, this.conditions, this.isComplete));
    }


}