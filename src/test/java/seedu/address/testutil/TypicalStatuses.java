package seedu.address.testutil;

import seedu.address.model.project.Status;

/**
 * A utility class containing a list of {@code ProjectTask} objects to be used in tests.
 */
public class TypicalStatuses {
    public static final Status STATUS_ONGOING = new Status(Status.STATUS_ONGOING);
    public static final Status STATUS_COMPLETE = new Status(Status.STATUS_COMPLETE);
    public static final Status STATUS_ON_HOLD = new Status(Status.STATUS_ON_HOLD);

    private TypicalStatuses() {} // prevents instantiation
}
