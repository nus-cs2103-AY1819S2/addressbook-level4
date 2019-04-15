package seedu.address.model.task;

/**
 *  Represents a Task's priority in the addressbook
 *  Gurantees: immutable as it is implemented as an enum; is valid as declared in {@link #isValidPriority(String)}
 */
public enum Priority {
    HIGH("high", 3),
    MED("med", 2),
    LOW("low", 1),
    COMPLETED("completed", 0);

    public static final String MESSAGE_CONSTRAINTS =
            "Priority can either be high, med, low or completed in any case but with the exact same spelling. "
                    + "The default priority level for a task will be low if you do not specify a priority";

    private final String priorityType;
    private final int priorityLevel;


    Priority(String priorityType, int priorityLevel) {
        this.priorityType = priorityType;
        this.priorityLevel = priorityLevel;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        for (Priority p : Priority.values()) {
            if (test.equals(p.getPriorityType())) {
                return true;
            }
        }
        return false;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    public String getPriorityType() {
        return this.priorityType;
    }

    public static Priority returnPriority(String type) {
        return Priority.valueOf(type.toUpperCase());
    }

    @Override
    public String toString() {
        return getPriorityType();
    }
}
