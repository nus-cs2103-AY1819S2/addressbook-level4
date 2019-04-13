package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_HEALTHWORKER_LISTED_OVERVIEW = "%1$d health workers listed";
    public static final String MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX = "The request index "
        + "provided is invalid";
    public static final String MESSAGE_INVALID_HEALTHWORKER_DISPLAYED_INDEX = "The healthworker "
        + "index provided is invalid.";
    public static final String MESSAGE_REQUESTS_LISTED_OVERVIEW = "%1$d requests listed!";
    public static final String MESSAGE_INVALID_REQUEST_COMMAND_FORMAT = "Invalid request command "
        + "format! \n%1$s";
    public static final String MESSAGE_REQUEST_ONGOING_CANNOT_CLEAR = "There is at least one "
        + "ongoing request in the list, request list cannot be cleared.";
    public static final String MESSAGE_REQUEST_COMPLETED_CANNOT_ASSIGN = "Completed request "
        + "cannot be assigned. To make changes to a completed request, use edit instead.";
    public static final String MESSAGE_HEALTHWORKER_ASSIGNED_CANNOT_DELETE = "Health Worker is currently assigned to "
            + "one or more request. Please close or reassigned these requests before deleting this Health Worker.";
    public static final String MESSAGE_HEALTHWORKER_OCCUPIED_CANNOT_ASSIGN = "Healthworker assigned"
        + " is busy with another request during one of the time intervals. Ensure that requests "
        + "are at least 2 hours apart.";
    public static final String MESSAGE_HEALTHWORKER_ALREADY_ASSIGNED = "One of the request(s) "
        + "selected is already assigned to this healthworker.";
    public static final String MESSAGE_EDITED_TIME_HEALTHWORKER_UNAVAILABLE = "The healthworker "
        + "assigned is not available during this specified time. Ensure that requests allocated to "
        + "a healthworker are at least 2 hours apart.";

}
