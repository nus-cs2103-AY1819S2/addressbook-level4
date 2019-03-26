package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityDateTime;
import seedu.address.model.activity.ActivityDescription;
import seedu.address.model.activity.ActivityLocation;
import seedu.address.model.activity.ActivityName;
import seedu.address.model.activity.ActivityStatus;
import seedu.address.model.person.Person;

/**
 * Lists all activities in the club manager to the user.
 */
public class ActivityListCommand extends ActivityCommand {
    public static final String COMMAND_WORD = "activityList";

    public static final String MESSAGE_SUCCESS = "Listed all activities";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
        List<Activity> activityList = model.getFilteredActivityList();
        for (Activity activity: activityList) {
            ActivityStatus old = activity.getStatus();
            ActivityStatus current = activity.getCurrentStatus();
            if (!old.equals(current)) {
                Activity updated = updateActivity(activity);
                model.setActivity(activity, updated);
            }
        }
        model.commitAddressBook();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Creates and returns an updated {@code Activity} with the details of {@code activity}
     */
    private static Activity updateActivity(Activity toUpdate) {
        ActivityName name = toUpdate.getName();
        ActivityDateTime dateTime = toUpdate.getDateTime();
        ActivityLocation location = toUpdate.getLocation();
        ActivityDescription description = toUpdate.getDescription();
        Optional<Person> inCharge = toUpdate.getInCharge();
        Map<Person, Boolean> attendance = toUpdate.getAttendance();
        return new Activity(name, dateTime, location, description, inCharge, attendance);
    }

}
