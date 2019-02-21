package seedu.address.model.request;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Represents a request made by a patient in the request book.
 */
public class Request {
    private final String id;
    private final Person patient;
    private final String requestDate;
    private final Set<String> conditions;
    private Person healthStaff;
    private boolean isCompleted; // represents the state of the request

    /*
     * Minimally requires the following parameters to be non-null.
     */
    public Request(String id, Person patient, String requestDate, Set<String> conditions, boolean isCompleted) {
        requireAllNonNull(id, patient, requestDate, conditions, isCompleted);
        this.id = id;
        this.patient = patient;
        this.requestDate = requestDate;
        this.conditions = conditions;
        this.isCompleted = isCompleted;
    }

    public void setHealthStaff(Person healthStaff) {
        this.healthStaff = healthStaff;
    }

    /**
     * Returns true if both requests of the same ID have at least one other
     * property field that is the same.
     * This defines a weaker notion of equality between two requests.
     */
    public boolean isSameRequest(Request otherRequest) {
        if (otherRequest == this) {
            return true;
        }

        return otherRequest != null
                && otherRequest.getId().equals(this.id)
                && ((otherRequest.getPatient().equals(this.patient)) || otherRequest
                .getConditions().equals(this.conditions));
    }

    @Override
    public String toString() {
        String healthStaff = this.healthStaff == null ? "Unassigned" : this
                .healthStaff.toString();
        String status = isCompleted ? "Completed" : "Pending";

        return "----------Request----------\n" +
                "ID: " + this.id + "\n" +
                "Patient: " + this.patient + "\n" +
                "Assigned staff: " + healthStaff + "\n" +
                "Request Date: " + this.requestDate + "\n" +
                "Condition(s): " + this.conditions + "\n" +
                "Status: " + status + "\n" +
                "----------End of Request----------\n";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;

        if (!(other instanceof Request)) {
            return false;
        }

        Request otherRequest = (Request) other;
        return otherRequest.getId().equals(this.id)
                && otherRequest.getPatient().equals(this.patient)
                && (otherRequest.getRequestDate().equals(this.requestDate))
                && otherRequest.getHealthStaff().equals(this.healthStaff)
                && (otherRequest.isComplete() == this.isCompleted);
    }

    public Set<String> getConditions() {
        return this.conditions;
    }

    public String getId() {
        return this.id;
    }

    public Person getPatient() {
        return patient;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public Person getHealthStaff() {
        return healthStaff;
    }

    public boolean isComplete() {
        return isCompleted;
    }
}
