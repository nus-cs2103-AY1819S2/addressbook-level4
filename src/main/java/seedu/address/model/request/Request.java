package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.model.person.Person;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.tag.Conditions;

/**
 * Represents a request made by a patient in the request book.
 */
public class Request {
    private final String id;
    private final Patient patient;
    private final RequestDate requestDate;
    private final Conditions conditions;
    private Optional<HealthWorker> healthWorker;
    private RequestStatus requestStatus;

    /**
     *
     * Minimally requires the following parameters to be non-null.
     *
     * @param id The id of the request
     * @param patient The patient requesting for services.
     * @param requestDate The date of the request.
     * @param conditions The set of the conditions the patient is requesting treatment for.
     * @param requestStatus The state of the request.
     */
    public Request(String id, Patient patient, RequestDate requestDate, Conditions conditions,
                   RequestStatus requestStatus) {
        requireAllNonNull(id, patient, requestDate, conditions, requestStatus);
        this.id = id;
        this.patient = patient;
        this.requestDate = requestDate;
        this.conditions = conditions;
        this.requestStatus = requestStatus;
        this.healthWorker = Optional.empty();
    }

    /*
     * Requires all the properties of a request to be non-null.
     */
    public Request(String id, Patient patient, HealthWorker healthStaff, RequestDate requestDate, Conditions conditions,
                   RequestStatus requestStatus) {
        this(id, patient, requestDate, conditions, requestStatus);
        requireNonNull(healthStaff);
        this.healthWorker = Optional.of(healthStaff);
    }

    public void setHealthStaff(HealthWorker healthStaff) {
        requireNonNull(healthStaff);
        this.healthWorker = Optional.of(healthStaff);
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

        return otherRequest != null
                && otherRequest.getId().equals(this.id)
                && otherRequest.getRequestDate().equals(this.requestDate)
                && ((otherRequest.getPatient().equals(this.patient)) || otherRequest
                .getConditions().equals(this.conditions));
    }

    @Override
    public String toString() {

        String healthStaff = this.healthWorker.map(Person::toString)
                .orElse("Unassigned");

        return "----------Request----------\n"
                + "ID: " + this.id + "\n"
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

        return otherRequest.getId().equals(this.id)
                && otherRequest.getPatient().equals(this.patient)
                && (otherRequest.getRequestDate().equals(this.requestDate))
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

    public String getId() {
        return this.id;
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

}
