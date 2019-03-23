package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Condition;
import seedu.address.model.util.SampleDataUtil;

/**
 * Represents a request made by a patient in the request book.
 */
public class Request {

    private final Name name;
    private final Nric nric;
    private final Address address;
    private final Phone phone;
    private final RequestDate requestDate;
    private Set<Condition> conditions = new HashSet<>();
    private String healthWorker = null;
    private RequestStatus requestStatus;

    /**
     * Overloaded constructor that takes in differing arguments for the patient.
     */
    public Request(Name name, Nric nric, Phone phone, Address address, RequestDate requestDate,
                   Set<Condition> conditions, RequestStatus status) {
        requireAllNonNull(name, phone, nric, address, requestDate, conditions, status);
        this.phone = phone;
        this.conditions = SampleDataUtil.getConditionsFromConditionSet(conditions);
        this.requestStatus = status;
        this.requestDate = requestDate;
        this.name = name;
        this.nric = nric;
        this.address = address;
    }

    /**
     * Simplified constructor that takes in the minimal arguments to form a {@code Request}
     * <p>
     * Sets the {@code requestStatus} to pending
     */
    public Request(Name name, Nric nric, Phone phone, Address address, RequestDate requestDate,
                   Set<Condition> conditions) {
        requireAllNonNull(name, nric, address, requestDate, conditions, phone);
        this.phone = phone;
        this.conditions = SampleDataUtil.getConditionsFromConditionSet(conditions);
        this.requestStatus = new RequestStatus("PENDING");
        this.name = name;
        this.nric = nric;
        this.requestDate = requestDate;
        this.address = address;
    }

    /**
     * Overloaded constructor to represent a request.
     */
    public Request(Name name, Nric nric, Phone phone, Address address, RequestDate requestDate,
                   Set<Condition> conditions, RequestStatus status, String healthWorker) {
        requireAllNonNull(name, address, phone, requestDate, conditions, status);
        this.phone = phone;
        this.conditions = conditions;
        this.requestStatus = status;
        this.requestDate = requestDate;
        this.healthWorker = healthWorker;
        this.name = name;
        this.nric = nric;
        this.address = address;
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

        return otherRequest.nric.equals(this.nric)
            && otherRequest.getRequestDate().equals(this.requestDate)
            && otherRequest.getConditions().equals(this.conditions)
            && otherRequest.address.equals(this.address);
    }

    public Name getName() {
        return this.name;
    }

    public Address getAddress() {
        return this.address;
    }

    public Nric getNric() {
        return this.nric;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("----------Request----------\n")
                .append("Name: ")
                .append(getName() + "\n")
                .append("Nric: ")
                .append(getNric() + "\n")
                .append("Phone: ")
                .append(getPhone() + "\n")
                .append("Address: ")
                .append(getAddress() + "\n")
                .append("Assigned staff: ")
                .append(getHealthStaff() + "\n")
                .append("Request Date: ")
                .append(getRequestDate() + "\n")
                .append("Condition(s): ");
        getConditions().forEach(builder::append);
        builder.append(getConditions() + "\n")
                .append("Status: ")
                .append(getRequestStatus() + "\n")
                .append("----------End of Request----------\n");
        return builder.toString();
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
            && (otherRequest.name.equals(this.name))
            && (otherRequest.address.equals(this.address))
            && (otherRequest.phone.equals(this.phone))
            && (otherRequest.nric.equals(this.nric))
            && (otherRequest.getConditions().equals(this.conditions))
            && otherRequest.getHealthStaff().equals(this.healthWorker)
            && (otherRequest.getRequestStatus().equals(this.requestStatus));
    }

    public Set<Condition> getConditions() {
        return this.conditions;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public RequestDate getRequestDate() {
        return this.requestDate;
    }

    public String getHealthStaff() {
        return healthWorker;
    }

    public void setHealthStaff(String healthStaff) {
        requireNonNull(healthStaff);
        this.healthWorker = healthStaff;
    }

    public boolean isOngoingStatus() {
        return this.requestStatus.isOngoingStatus();
    }

    public Phone getPhone() {
        return phone;
    }

}
