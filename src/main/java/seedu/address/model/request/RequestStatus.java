package seedu.address.model.request;

/**
 * Represents an Request's status in the request book.
 * Guarantees: immutable
 *
 * @@author daviddl9
 */
public class RequestStatus {

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status should be either: PENDING, ONGOING or COMPLETED";

    /**
     * Represents the different possible statuses of a request.
     */
    private enum Status {
        PENDING,
        ONGOING,
        COMPLETED
    }

    private Status requestState = null;

    /**
     * Constructs a {@code RequestStatus}.
     */
    public RequestStatus() {
        this(Status.PENDING.name());
    }

    /**
     * Constructs a {@code RequestStatus} with a parameter.
     *
     * @param status a valid request status
     */
    public RequestStatus(String status) {
        this.requestState = Status.valueOf(status);
    }

    /**
     * @return true if the status is ongoing, false otherwise.
     */
    public boolean isOngoingStatus() {
        return this.requestState.equals(Status.ONGOING);
    }

    /**
     * @return true if status is completed, false otherwise.
     */
    public boolean isCompletedStatus() {
        return this.requestState.equals(Status.COMPLETED);
    }

    /**
     * @param requestStatus The status to be checked for validity.
     * @return true if the requestStatus is valid, false otherwise.
     */
    public static boolean isValidStatus(String requestStatus) {
        for (Status s : Status.values()) {
            if (requestStatus.equals(s.name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.requestState.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestStatus // instanceof handles nulls
                && this.requestState.equals(((RequestStatus) other).requestState)); // state check
    }

    @Override
    public int hashCode() {
        return this.requestState.hashCode();
    }
}
