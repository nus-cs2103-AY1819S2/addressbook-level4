package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;

/**
 * Edits the existing user story of an exisiting project in pocket project
 */
public class EditProjectUserStoryCommand extends EditProjectCommand {

    public static final String EDIT_USER_STORY_KEYWORD = "userstory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME " + EDIT_USER_STORY_KEYWORD
        + ": Edit the specified userstory in the list of userstories in project.\n"
        + "Example: " + COMMAND_WORD + " Apollo userstory 2 i/2 as a ... i want to .... so that .....";

    public static final String MESSAGE_EDIT_STORY_SUCCESS = "Edited user story: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_USER_STORY = "This story already exists in the pocket project.";

    private final Index index;
    private final EditUserStoryDescriptor editUserStoryDescriptor;
    private final ProjectName name;

    /**
     * @param index of the user story in the user stories list to edit
     * @param editUserStoryDescriptor details to edit the user story with
     */
    public EditProjectUserStoryCommand(ProjectName name, Index index, EditUserStoryDescriptor editUserStoryDescriptor) {
        requireNonNull(index);
        requireNonNull(editUserStoryDescriptor);
        requireNonNull(name);

        this.index = index;
        this.editUserStoryDescriptor = new EditUserStoryDescriptor(editUserStoryDescriptor);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Project selectedProject = model.getProjectWithName(name);

        if (isNull(selectedProject)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }

        ObservableList<UserStory> userStories = selectedProject.getUserStories();

        if (index.getZeroBased() >= userStories.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_USERSTORY_DISPLAYED_INDEX);
        }


        UserStory storyToEdit = userStories.get(index.getZeroBased());
        UserStory editedStory = createEditedUserStory(storyToEdit, editUserStoryDescriptor);

        if (!storyToEdit.isSameUserStory(editedStory) && userStories.contains(editedStory)) {
            throw new CommandException(MESSAGE_DUPLICATE_USER_STORY);
        }

        selectedProject.setUserStory(storyToEdit, editedStory);

        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_EDIT_STORY_SUCCESS, editedStory));
    }

    /**
     * Creates and returns a {@code Employee} with the details of {@code employeeToEdit}
     * edited with {@code editEmployeeDescriptor}.
     */
    private static UserStory createEditedUserStory(UserStory userStoryToEdit,
                                                  EditUserStoryDescriptor editUserStoryDescriptor) {
        assert userStoryToEdit != null;

        UserStoryUser newUser = editUserStoryDescriptor.getUser().orElse(
                new UserStoryUser((userStoryToEdit.getUserStoryUser())));
        UserStoryFunction newFunction = editUserStoryDescriptor.getFunction().orElse(
                new UserStoryFunction((userStoryToEdit.getUserStoryFunction())));
        UserStoryReason newReason = editUserStoryDescriptor.getReason().orElse(
                new UserStoryReason((userStoryToEdit.getUserStoryReason())));
        UserStoryImportance newImportance = editUserStoryDescriptor.getImportance().orElse(
                new UserStoryImportance((userStoryToEdit.getUserStoryImportance())));
        Status newStatus = editUserStoryDescriptor.getStatus().orElse(
                new Status((userStoryToEdit.getUserStoryStatus())));

        return new UserStory(newImportance, newUser, newFunction, newReason, newStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProjectUserStoryCommand)) {
            return false;
        }

        // state check
        EditProjectUserStoryCommand c = (EditProjectUserStoryCommand) other;
        return index.equals(c.index)
                && editUserStoryDescriptor.equals(c.editUserStoryDescriptor);
    }

    /**
     * Stores the details to edit the user story with. Each non-empty field value will replace the
     * corresponding field value of the user story.
     */
    public static class EditUserStoryDescriptor {
        private UserStoryUser user;
        private UserStoryFunction function;
        private UserStoryReason reason;
        private UserStoryImportance importance;
        private Status status;

        public EditUserStoryDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditUserStoryDescriptor(EditUserStoryDescriptor toCopy) {
            setUser(toCopy.user);
            setFunction(toCopy.function);
            setReason(toCopy.reason);
            setImportance(toCopy.importance);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(user, function, reason, importance, status);
        }

        public void setUser(UserStoryUser user) {
            this.user = user;
        }

        public Optional<UserStoryUser> getUser() {
            return Optional.ofNullable(user);
        }

        public void setFunction(UserStoryFunction func) {
            this.function = func;
        }

        public Optional<UserStoryFunction> getFunction() {
            return Optional.ofNullable(function);
        }

        public void setReason(UserStoryReason newReason) {
            if (newReason != null && newReason.getReason().equals("nil")) {
                this.reason = new UserStoryReason(UserStoryReason.DEFAULT_REASON);
            } else {
                this.reason = newReason;
            }
        }

        public Optional<UserStoryReason> getReason() {
            return Optional.ofNullable(reason);
        }

        public void setImportance(UserStoryImportance importance) {
            this.importance = importance;
        }

        public Optional<UserStoryImportance> getImportance() {
            return Optional.ofNullable(importance);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditUserStoryDescriptor)) {
                return false;
            }

            // state check
            EditUserStoryDescriptor story = (EditUserStoryDescriptor) other;

            return getUser().equals(story.getUser())
                    && getFunction().equals(story.getFunction())
                    && getReason().equals(story.getReason())
                    && getImportance().equals(story.getImportance())
                    && getStatus().equals(story.getStatus());
        }
    }


}
