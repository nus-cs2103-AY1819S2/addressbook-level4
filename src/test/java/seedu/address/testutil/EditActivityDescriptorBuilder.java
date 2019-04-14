package seedu.address.testutil;

import java.util.List;

import seedu.address.logic.commands.ActivityEditCommand;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityDateTime;
import seedu.address.model.activity.ActivityDescription;
import seedu.address.model.activity.ActivityLocation;
import seedu.address.model.activity.ActivityName;

import seedu.address.model.person.MatricNumber;


/**
 * A utility class to help with building EditActivityDescriptor objects.
 */
public class EditActivityDescriptorBuilder {

    private ActivityEditCommand.EditActivityDescriptor descriptor;

    public EditActivityDescriptorBuilder() {
        descriptor = new ActivityEditCommand.EditActivityDescriptor();
    }

    public EditActivityDescriptorBuilder(ActivityEditCommand.EditActivityDescriptor descriptor) {
        this.descriptor = new ActivityEditCommand.EditActivityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditActivityDescriptor} with fields containing {@code activity}'s details
     */
    public EditActivityDescriptorBuilder(Activity activity) {
        descriptor = new ActivityEditCommand.EditActivityDescriptor();
        descriptor.setActivityName(activity.getName());
        descriptor.setActivityDateTime(activity.getDateTime());
        descriptor.setActivityLocation(activity.getLocation());
        descriptor.setActivityDescription(activity.getDescription());
        descriptor.setAttendance(activity.getAttendance());
    }

    /**
     * Sets the {@code ActivityName} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withActivityName(String name) {
        descriptor.setActivityName(new ActivityName(name));
        return this;
    }

    /**
     * Sets the {@code ActivityDateTime} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withActivityDateTime(String dateTime) {
        descriptor.setActivityDateTime(new ActivityDateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code ActivityLocation} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withActivityLocation(String location) {
        descriptor.setActivityLocation(new ActivityLocation(location));
        return this;
    }

    /**
     * Sets the {@code ActivityDescription} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withActivityDescription(String description) {
        descriptor.setActivityDescription(new ActivityDescription(description));
        return this;
    }

    /**
     * Sets the {@code Attendance} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withAttendance(List<MatricNumber> attendance) {
        descriptor.setAttendance(attendance);
        return this;
    }


    public ActivityEditCommand.EditActivityDescriptor build() {
        return descriptor;
    }
}
