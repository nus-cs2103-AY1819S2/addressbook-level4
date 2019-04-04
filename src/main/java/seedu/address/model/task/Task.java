package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.patient.Nric;
import seedu.address.model.person.Name;


/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    protected final Title title;

    // Data fields
    protected final DateCustom startDate;
    protected final DateCustom endDate;
    protected final TimeCustom startTime;
    protected final TimeCustom endTime;
    protected final boolean isCopy;
    protected int copyCount;

    private LinkedPatient linkedPatient;
    private Priority priority;



    /**
     * Every field must be present and not null.
     */
    public Task(Title title, DateCustom startDate, DateCustom endDate, TimeCustom startTime,
                TimeCustom endTime, Priority priority, LinkedPatient linkedPatient) {
        requireAllNonNull(title, startDate, endDate, startTime, endTime, priority);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.linkedPatient = linkedPatient;
        this.isCopy = false;
        this.copyCount = 0;
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Task t) {
        requireAllNonNull(t);
        this.title = t.getTitle();
        this.startDate = t.getStartDate();
        this.endDate = t.getEndDate();
        this.startTime = t.getStartTime();
        this.endTime = t.getEndTime();
        this.priority = t.getPriority();
        this.linkedPatient = t.getLinkedPatient();
        this.isCopy = true;
        this.copyCount = 0;
    }

    public Task(Task t, boolean isClone) {
        requireAllNonNull(t);
        this.title = t.getTitle();
        this.startDate = t.getStartDate();
        this.endDate = t.getEndDate();
        this.startTime = t.getStartTime();
        this.endTime = t.getEndTime();
        this.priority = t.getPriority();
        this.linkedPatient = t.getLinkedPatient();
        this.isCopy = false;
        this.copyCount = 0;
    }


    public Title getTitle() {
        return title;
    }

    public DateCustom getStartDate() {
        return startDate;
    }

    public DateCustom getEndDate() {
        return endDate;
    }

    public TimeCustom getStartTime() {
        return startTime;
    }

    public TimeCustom getEndTime() {
        return endTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public LinkedPatient getLinkedPatient() {
        return linkedPatient;
    }

    public void setLinkedPatient(Name name, Nric nric) {
        linkedPatient = new LinkedPatient(name, nric);
    }

    public void setNullLinkedPatient() {
        linkedPatient = null;
    }

    public void setPriorityComplete() {
        this.priority = Priority.COMPLETED;
    }

    /**
     * Retuns true if the current Task has a start date that is after its end date
     */
    public boolean hasDateClash() {
        return DateCustom.dateCompare(endDate.toString(), startDate.toString());
    }

    /**
     *  Returns true if the current task has a start time that is after end time
     *  when the task has the same dates
     */
    public boolean hasTimeClash() {
        return startDate.isSameDate(endDate.toString())
                && TimeCustom.timeCompare(startTime.toString(), endTime.toString());
    }

    public boolean isCopy() {
        return isCopy;
    }

    /**
     * Returns a copy of the instance
     */
    public Task copy() {
        return new Task(this);
    }

    /**
     * Returns true if both tasks have the exact same title and data fields (subject to change)
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == null) {
            return false;
        } else if (isCopy || otherTask.isCopy) {
            return false;
        } else {
            return otherTask.getTitle().equals(getTitle())
                    && otherTask.getStartDate().equals(getStartDate())
                    && otherTask.getEndDate().equals(getEndDate())
                    && otherTask.getStartTime().equals(getStartTime())
                    && otherTask.getEndTime().equals(getEndTime())
                    && (otherTask.getLinkedPatient() == null ? getLinkedPatient() == null
                    : otherTask.getLinkedPatient().equals(getLinkedPatient()));
        }
    }

    /**
     * Returns true if both tasks have the exact same title and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        if (this.isCopy || ((Task) other).isCopy) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTitle().equals(getTitle())
                && otherTask.getStartDate().equals(getStartDate())
                && otherTask.getEndDate().equals(getEndDate())
                && otherTask.getStartTime().equals(getStartTime())
                && otherTask.getEndTime().equals(getEndTime())
                && (otherTask.getLinkedPatient() == null ? getLinkedPatient() == null
                : otherTask.getLinkedPatient().equals(getLinkedPatient()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, startDate, endDate, startTime, endTime, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title: ")
                .append(getTitle())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Linked Patient ")
                .append(getLinkedPatient() == null ? "None" : getLinkedPatient());
        return builder.toString();
    }

}
