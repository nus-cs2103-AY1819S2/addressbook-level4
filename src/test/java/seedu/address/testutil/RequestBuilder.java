package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.request.Request;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Request objects.
 */
public class RequestBuilder {
    public static final String DEFAULT_ID = "1233";
    public static final String DEFAULT_PATIENT_NAME = "Amanda Tan";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Far East Ave 3, #04-123, 123456";
    public static final boolean DEFAULT_ISCOMPLETE = false;
    public static final String DEFAULT_DATE = "01-01-2019 08:00:00";
    public static final String DEFAULT_REQUEST = "Physiotherapy";
    public static final String DEFAULT_EMAIL = "amandatan@test.com";
    public static final String DEFAULT_STAFF_NAME = "John Doe";
    public static final String DEFAULT_STAFF_PHONE = "81237822";
    public static final String DEFAULT_STAFF_ADDRESS = "Health Hub Ave 3, 129834";
    public static final String DEFAULT_STAFF_EMAIL = "healthstaff@health.com";

    private String id;
    private String requestDate;
    private boolean isComplete;
    private Set<Tag> conditions;
    private Optional<Person> healthWorker;
    private Person patient;

    public RequestBuilder() {
        this.id = DEFAULT_ID;
        this.patient = new Person(new Name(DEFAULT_PATIENT_NAME), new Phone(DEFAULT_PHONE), new Email(DEFAULT_EMAIL),
                new Address(DEFAULT_ADDRESS), new HashSet<>(Collections.singleton(new Tag(DEFAULT_REQUEST))));
        this.healthWorker = Optional.of(new Person(new Name(DEFAULT_STAFF_NAME), new Phone(DEFAULT_STAFF_PHONE),
                new Email(DEFAULT_STAFF_EMAIL), new Address(DEFAULT_STAFF_ADDRESS), Collections.emptySet()));
        this.requestDate = DEFAULT_DATE;
        this.conditions = new HashSet<>(Collections.singleton(new Tag(DEFAULT_REQUEST)));
        this.isComplete = DEFAULT_ISCOMPLETE;
    }

    /*
     * Initializes the RequestBuilder with the data of {@code requestToCopy}.
     */
    public RequestBuilder(Request requestToCopy) {
        this.id = requestToCopy.getId();
        this.patient = requestToCopy.getPatient();
        this.healthWorker = requestToCopy.getHealthStaff();
        this.conditions = requestToCopy.getConditions();
        this.requestDate = requestToCopy.getRequestDate();
        this.isComplete = requestToCopy.isComplete();
    }

    /**
     * Sets the {@code id} of the {@code Request} that we are building.
     * @param id The id of the request.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code requestDate} of the {@code Request} that we are building
     * @param date the date of the request.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withDate(String date) {
        this.requestDate = date;
        return this;
    }

    /**
     * Sets the {@code patient} of the {@code Request} we are building.
     * @param person The patient making the request.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withPatient(Person person) {
        this.patient = person;
        return this;
    }

    /**
     * Sets the {@code healthStaff} of the {@code Request} we are building.
     * @param healthStaff The healthStaff attending to the request.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withHealthStaff(Person healthStaff) {
        requireNonNull(healthStaff);
        this.healthWorker = Optional.of(healthStaff);
        return this;
    }

    /**
     * Sets the {@code healthStaff} of the {@code Request} we are building.
     * @param conditions The conditions that need be attended to, by the healthworker.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withConditions(String... conditions) {
        this.conditions = SampleDataUtil.getTagSet(conditions);
        return this;
    }

    /**
     * Sets the {@code healthStaff} of the {@code Request} we are building.
     * @param isComplete The status of the request - true if complete, false otherwise.
     * @return The RequestBuilder object.
     */
    public RequestBuilder withStatus(boolean isComplete) {
        this.isComplete = isComplete;
        return this;
    }

    /*
     * Builds and returns the order.
     */
    public Request build() {
        return this.healthWorker.map(person -> new Request(this.id, this.patient, person, this.requestDate,
                this.conditions, this.isComplete))
                .orElseGet(() -> new Request(this.id, this.patient, this.requestDate, this.conditions, this.isComplete)
                );
    }

}
