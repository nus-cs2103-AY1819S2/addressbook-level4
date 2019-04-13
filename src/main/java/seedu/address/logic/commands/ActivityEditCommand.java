package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITYNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityDateTime;
import seedu.address.model.activity.ActivityDescription;
import seedu.address.model.activity.ActivityLocation;
import seedu.address.model.activity.ActivityName;

import seedu.address.model.person.MatricNumber;


/**
 * Edits the details of an existing activity in the address book.
 */
public class ActivityEditCommand extends ActivityCommand {

    public static final String COMMAND_WORD = "activityEdit";
    public static final String COMMAND_ALIAS = "aEdit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the displayed activity list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ACTIVITYNAME + "NAME] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_ADESCRIPTION + "DESCRIPTION]... \n "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ACTIVITYNAME + "Final Exam "
            + PREFIX_DATETIME + "30/04/2019 1700";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the address book.";


    private final Index index;
    private final EditActivityDescriptor editActivityDescriptor;

    /**
     * @param index of the activity in the filtered activity list to edit
     * @param editActivityDescriptor details to edit the activity with
     */
    public ActivityEditCommand(Index index, EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(index);
        requireNonNull(editActivityDescriptor);

        this.index = index;
        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToEdit = lastShownList.get(index.getZeroBased());
        Activity editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);

        if (!activityToEdit.isSameActivity(editedActivity) && model.hasActivity(editedActivity)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        model.setActivity(activityToEdit, editedActivity);
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity));
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                 EditActivityDescriptor editActivityDescriptor) {
        assert activityToEdit != null;

        ActivityName updatedActivityName = editActivityDescriptor.getActivityName().orElse(
                activityToEdit.getName());
        ActivityLocation updatedLocation = editActivityDescriptor.getActivityLocation().orElse(
                activityToEdit.getLocation());
        ActivityDateTime updatedDateTime = editActivityDescriptor.getActivityDateTime().orElse(
                activityToEdit.getDateTime());
        ActivityDescription updatedDescription = editActivityDescriptor.getActivityDescription().orElse(
                activityToEdit.getDescription());
        List<MatricNumber> updatedAttendance = editActivityDescriptor.getAttendance().orElse(
                activityToEdit.getAttendance());

        return new Activity(updatedActivityName, updatedDateTime, updatedLocation,
                updatedDescription, updatedAttendance);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityEditCommand)) {
            return false;
        }

        // state check
        ActivityEditCommand e = (ActivityEditCommand) other;
        return index.equals(e.index)
                && editActivityDescriptor.equals(e.editActivityDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditActivityDescriptor {
        private ActivityName activityName;
        private ActivityLocation activityLocation;
        private ActivityDateTime activityDateTime;
        private ActivityDescription activityDescription;
        private List<MatricNumber> attendance;

        public EditActivityDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditActivityDescriptor(EditActivityDescriptor toCopy) {
            setActivityName(toCopy.activityName);
            setActivityLocation(toCopy.activityLocation);
            setActivityDateTime(toCopy.activityDateTime);
            setActivityDescription(toCopy.activityDescription);
            setAttendance(toCopy.attendance);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(activityName, activityLocation, activityDateTime, activityDescription,
                    attendance);
        }

        public void setActivityName(ActivityName activityName) {
            this.activityName = activityName;
        }

        public Optional<ActivityName> getActivityName() {
            return Optional.ofNullable(activityName);
        }

        public void setActivityLocation(ActivityLocation activityLocation) {
            this.activityLocation = activityLocation;
        }

        public Optional<ActivityLocation> getActivityLocation() {
            return Optional.ofNullable(activityLocation);
        }

        public void setActivityDateTime(ActivityDateTime activityDateTime) {
            this.activityDateTime = activityDateTime;
        }

        public Optional<ActivityDateTime> getActivityDateTime() {
            return Optional.ofNullable(activityDateTime);
        }

        public void setActivityDescription(ActivityDescription activityDescription) {
            this.activityDescription = activityDescription;
        }

        public Optional<ActivityDescription> getActivityDescription() {
            return Optional.ofNullable(activityDescription);
        }

        public void setAttendance(List<MatricNumber> attendance) {
            this.attendance = attendance;
        }

        public Optional<List<MatricNumber>> getAttendance() {
            return Optional.ofNullable(attendance);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditActivityDescriptor)) {
                return false;
            }

            // state check
            EditActivityDescriptor e = (EditActivityDescriptor) other;

            return getActivityName().equals(e.getActivityName())
                    && getActivityLocation().equals((e.getActivityLocation()))
                    && getActivityDateTime().equals(e.getActivityDateTime())
                    && getActivityDescription().equals(e.getActivityDescription())
                    && getAttendance().equals(e.getAttendance());
        }
    }
}
