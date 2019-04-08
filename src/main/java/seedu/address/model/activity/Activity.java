package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.MatricNumber;


/**
 * Represents an Activity in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Activity implements Comparable<Activity> {
    // Identity fields
    private final ActivityName name;
    private final ActivityDateTime dateTime;
    private final ActivityLocation location;
    private final ActivityDescription description;
    private final ActivityStatus status;

    // Data fields
    private final List<MatricNumber> attendance;

    /**
     * Every field must be present and not null.
     */
    public Activity(ActivityName name, ActivityDateTime dateTime, ActivityLocation location,
                    ActivityDescription description) {
        requireAllNonNull(name, dateTime, location, description);
        this.name = name;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.status = setStatus(dateTime);
        this.attendance = new ArrayList<>();
    }

    public Activity(ActivityName name, ActivityDateTime dateTime, ActivityLocation location) {
        requireAllNonNull(name, dateTime, location);
        this.name = name;
        this.dateTime = dateTime;
        this.location = location;
        this.description = new ActivityDescription();
        this.status = setStatus(dateTime);
        this.attendance = new ArrayList<>();
    }

    public Activity(ActivityName name, ActivityDateTime dateTime, ActivityLocation location,
        ActivityDescription description, List<MatricNumber> attendance) {
        requireAllNonNull(name, dateTime, location, description);
        this.name = name;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.status = setStatus(dateTime);
        this.attendance = attendance;
    }


    public ActivityName getName() {
        return name;
    }

    public ActivityDateTime getDateTime() {
        return dateTime;
    }

    public ActivityLocation getLocation() {
        return location;
    }

    public ActivityDescription getDescription() {
        return description;
    }

    public List<MatricNumber> getAttendance() {
        return attendance;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public ActivityStatus getCurrentStatus() {
        return new ActivityStatus(dateTime.isPast());
    }

    /**
     * Adds member to attendance list
     * */
    public static Activity addMemberToActivity(Activity toReplace, MatricNumber matricNumber) {
        ActivityName copyName = toReplace.getName();
        ActivityDateTime copyDateTime = toReplace.getDateTime();
        ActivityLocation copyLocation = toReplace.getLocation();
        ActivityDescription copyDescription = toReplace.getDescription();
        List<MatricNumber> copyAttendance = new ArrayList<>();
        copyAttendance.addAll(toReplace.getAttendance());
        copyAttendance.add(matricNumber);

        return new Activity(copyName, copyDateTime, copyLocation, copyDescription, copyAttendance);
    }

    /**
     * Removes member from attendance list
     * */
    public static Activity removeMemberFromActivity(Activity toReplace, MatricNumber matricNumber) {
        ActivityName copyName = toReplace.getName();
        ActivityDateTime copyDateTime = toReplace.getDateTime();
        ActivityLocation copyLocation = toReplace.getLocation();
        ActivityDescription copyDescription = toReplace.getDescription();
        List<MatricNumber> copyAttendance = new ArrayList<>();
        copyAttendance.addAll(toReplace.getAttendance());
        copyAttendance.remove(matricNumber);

        return new Activity(copyName, copyDateTime, copyLocation, copyDescription, copyAttendance);
    }

    public boolean hasPersonInAttendance(MatricNumber matricNumber) {
        return attendance.contains(matricNumber);
    }

    public int getNumberAttending() {
        return this.attendance.size();
    }

    /**
     * Returns a activity status based on the ActivityDateTime input
     */
    private ActivityStatus setStatus(ActivityDateTime dateTime) {
        return new ActivityStatus(dateTime.isPast());
    }

    /**
     * Returns true if both activities are of the same name have the same date.
     * This defines a weaker notion of equality between two activities.
     */
    public boolean isSameActivity(Activity otherActivity) {
        if (otherActivity == this) {
            return true;
        }

        return otherActivity != null
                && otherActivity.getName().equals(getName())
                && otherActivity.getDateTime().equals(getDateTime());
    }

    /**
     * Returns true if both activities have the same datetime and location.
     * This is to check if there are clashes in location.
     */
    public boolean hasClashInTimeLocation(Activity otherActivity) {
        if (otherActivity == this) {
            return true;
        }

        return otherActivity != null
                && otherActivity.getDateTime().equals(getDateTime())
                && otherActivity.getLocation().equals(getLocation());
    }

    /**
     * Returns an updated activity with new Status
     */
    public Activity updateActivity() {
        ActivityName name = this.getName();
        ActivityDateTime dateTime = this.getDateTime();
        ActivityLocation location = this.getLocation();
        ActivityDescription description = this.getDescription();
        List<MatricNumber> attendance = this.getAttendance();
        return new Activity(name, dateTime, location, description, attendance);
    }

    /**
     * Returns whether a Person represented by the {@code Matric} is Attending the activity
     */
    public boolean isMatricAttending(MatricNumber matric) {
        return attendance.contains(matric);
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return otherActivity.getName().equals(getName())
                && otherActivity.getDateTime().equals(getDateTime())
                && otherActivity.getLocation().equals(getLocation())
                && otherActivity.getDescription().equals(getDescription())
                && otherActivity.getAttendance().equals(getAttendance());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dateTime, location, description, attendance);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date Time: ")
                .append(getDateTime())
                .append(" Location: ")
                .append(getLocation())
                .append(" Description: ")
                .append(getDescription())
                .append(" Number Attending: ")
                .append(getNumberAttending());
        return builder.toString();
    }

    @Override
    public int compareTo(Activity other) {
        //when both activity are ongoing or completed, compare by the time of the activity
        if (this.getStatus().equals(other.getStatus())) {
            return this.getDateTime().compareTo(other.getDateTime());
        }
        if (other.getStatus().isCompleted()) {
            return -1;
            //this activity is ongoing while the other is completed, this activity will come first in the list
        }
        return 1;
        // this activity is completed while the other is ongoing, the other activity will come first in the list
    }
}
