package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.address.model.person.Person;


/**
 * Represents an Acrivity in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Activity {
    // Identity fields
    private final ActivityName name;
    private final ActivityDateTime dateTime;
    private final ActivityLocation location;
    private final ActivityDescription description;

    // Data fields
    private Person inCharge;
    private final Map<Person, Boolean> attendance = new HashMap<>();

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
    }

    public Activity(ActivityName name, ActivityDateTime dateTime, ActivityLocation location) {
        requireAllNonNull(name, dateTime, location);
        this.name = name;
        this.dateTime = dateTime;
        this.location = location;
        this.description = new ActivityDescription();
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

    public Person getInCharge() {
        return inCharge;
    }

    public Map<Person, Boolean> getAttendance() {
        return attendance;
    }

    public void setInCharge(Person person) {
        this.inCharge = person;
    }

    public int getNumberAttending() {
        return this.attendance.size();
    }

    /**
     * Returns true if both activities of the same name have the same date.
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
                && otherActivity.getInCharge().equals(getInCharge())
                && otherActivity.getAttendance().equals(getAttendance());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dateTime, inCharge, attendance);
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
                .append(" In Charge: ")
                .append(getInCharge())
                .append(" Number Attending: ")
                .append(getNumberAttending());
        return builder.toString();
    }
}
