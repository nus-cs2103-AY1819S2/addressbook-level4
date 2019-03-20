package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.tag.Conditions;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Represents a request made by a patient in the request book.
 */
public class Request {

    private final Patient patient;
    private final RequestDate requestDate;
    private final Conditions conditions;
    private Optional<HealthWorker> healthWorker;
    private RequestStatus requestStatus;

    /**
     *
     * Minimally requires the following parameters to be non-null.
     *
     * @param patient The patient requesting for services.
     * @param requestDate The date of the request.
     * @param conditions The set of the conditions the patient is requesting treatment for.
     * @param requestStatus The state of the request.
     */
    public Request(Patient patient, RequestDate requestDate, Conditions conditions,
                   RequestStatus requestStatus) {
        requireAllNonNull(patient, requestDate, conditions, requestStatus);
        this.patient = patient;
        this.requestDate = requestDate;
        this.conditions = conditions;
        this.requestStatus = requestStatus;
        this.healthWorker = Optional.empty();
    }

    /*
     * Requires all the properties of a request to be non-null.
     */
    public Request(Patient patient, HealthWorker healthStaff, RequestDate requestDate, Conditions conditions,
                   RequestStatus requestStatus) {
        this(patient, requestDate, conditions, requestStatus);
        requireNonNull(healthStaff);
        this.healthWorker = Optional.of(healthStaff);
    }

    /**
     * Overloaded constructor that takes in differing arguments for the patient.
     */
    public Request(Name name, Phone phone, Address address, RequestDate requestDate,
                   Set<Tag> conditions, RequestStatus status) {
        requireAllNonNull(name, phone, address, requestDate, conditions, status);
        this.patient = new Patient(name, phone, address, conditions);
        this.conditions = SampleDataUtil.getConditionsFromTagSet(conditions);
        this.requestStatus = status;
        this.requestDate = requestDate;
        this.healthWorker = Optional.empty();
    }

    /**
     * Overloaded constructor that takes in differing arguments for the patient.
     */
    public Request(Name name, Phone phone, Address address, RequestDate requestDate,
                   Conditions conditions, RequestStatus status) {
        requireAllNonNull(name, phone, address, requestDate, conditions, status);
        HashSet<Tag> cond = new HashSet<>();
        conditions.getConditions().forEach(conditionTag -> cond.add(new Tag(conditionTag.conditionTagName)));
        this.patient = new Patient(name, phone, address, cond);
        this.conditions = conditions;
        this.requestStatus = status;
        this.requestDate = requestDate;
        this.healthWorker = Optional.empty();
    }

    /**
     * Overloaded constructor to represent a request.
     */
    public Request(Name name, Phone phone, Address address, RequestDate requestDate,
                   Conditions conditions, RequestStatus status, HealthWorker healthWorker) {
        requireAllNonNull(name, phone, address, requestDate, conditions, status, healthWorker);
        HashSet<Tag> cond = new HashSet<>();
        conditions.getConditions().forEach(conditionTag -> cond.add(new Tag(conditionTag.conditionTagName)));
        this.patient = new Patient(name, phone, address, cond);
        this.conditions = conditions;
        this.requestStatus = status;
        this.requestDate = requestDate;
        this.healthWorker = Optional.ofNullable(healthWorker);
    }

    /**
     * Overloaded constructor that takes in a {@code healthWorker}.
     */
    public Request(Name name, Phone phone, Address address, RequestDate requestDate,
                   Set<Tag> conditions, RequestStatus status, HealthWorker healthWorker) {
        requireAllNonNull(name, phone, address, requestDate, conditions, status);
        this.patient = new Patient(name, phone, address, conditions);
        this.conditions = SampleDataUtil.getConditionsFromTagSet(conditions);
        this.requestStatus = status;
        this.requestDate = requestDate;
        this.healthWorker = Optional.ofNullable(healthWorker);
    }

    /**
     * Returns true if both requests of the same ID and date have at least one other
     * property field that is the same.
     * This defines a weaker notion of equality between two requests.
     */
    public boolean isSameRequest(Request otherRequest) {
        if (otherRequest == this) {
            return true;
        }

        if (otherRequest == null) {
            return false;
        }

        return otherRequest.getRequestDate().equals(this.requestDate)
                && ((otherRequest.getPatient().equals(this.patient)) || otherRequest
                .getConditions().equals(this.conditions));
    }

    @Override
    public String toString() {

        String healthStaff = this.healthWorker.map(Person::toString)
                .orElse("Unassigned");

        return "----------Request----------\n"
                + "Patient: " + this.patient + "\n"
                + "Assigned staff: " + healthStaff + "\n"
                + "Request Date: " + this.requestDate + "\n"
                + "Condition(s): " + this.conditions + "\n"
                + "Status: " + this.requestStatus + "\n"
                + "----------End of Request----------\n";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Request)) {
            return false;
        }

        Request otherRequest = (Request) other;
        return (otherRequest.getRequestDate().equals(this.requestDate))
                && (otherRequest.getPatient().equals(this.patient))
                && (otherRequest.getConditions().equals(this.conditions))
                && otherRequest.getHealthStaff().equals(this.healthWorker)
                && (otherRequest.getRequestStatus().equals(this.requestStatus));
    }

    public Conditions getConditions() {
        return this.conditions;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public Patient getPatient() {
        return patient;
    }

    public RequestDate getRequestDate() {
        return this.requestDate;
    }

    public Optional<HealthWorker> getHealthStaff() {
        return healthWorker;
    }

    public void setHealthStaff(HealthWorker healthStaff) {
        requireNonNull(healthStaff);
        this.healthWorker = Optional.of(healthStaff);
    }

}
