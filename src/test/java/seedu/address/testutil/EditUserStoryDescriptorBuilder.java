package seedu.address.testutil;

import seedu.address.logic.commands.EditProjectUserStoryCommand;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;

/**
 * A utility class to help with building EditUserStoryDescriptor objects.
 */
public class EditUserStoryDescriptorBuilder {

    private EditProjectUserStoryCommand.EditUserStoryDescriptor descriptor;

    public EditUserStoryDescriptorBuilder() {
        descriptor = new EditProjectUserStoryCommand.EditUserStoryDescriptor();
    }

    public EditUserStoryDescriptorBuilder(EditProjectUserStoryCommand.EditUserStoryDescriptor descriptor) {
        this.descriptor = new EditProjectUserStoryCommand.EditUserStoryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditUserStoryDescriptor} with fields containing {@code userStory}'s details
     */
    public EditUserStoryDescriptorBuilder(UserStory userStory) {
        descriptor = new EditProjectUserStoryCommand.EditUserStoryDescriptor();
        descriptor.setStatus(new Status(userStory.getUserStoryStatus()));
        descriptor.setImportance(new UserStoryImportance(userStory.getUserStoryImportance()));
        descriptor.setUser(new UserStoryUser(userStory.getUserStoryUser()));
        descriptor.setFunction(new UserStoryFunction(userStory.getUserStoryFunction()));
        descriptor.setReason(new UserStoryReason(userStory.getUserStoryReason()));
    }

    /**
     * Sets the {@code userStoryUser} of the {@code EditUserStoryDescriptor} that we are building.
     */
    public EditUserStoryDescriptorBuilder withUser(String user) {
        descriptor.setUser(new UserStoryUser(user));
        return this;
    }

    /**
     * Sets the {@code userStoryFunction} of the {@code EditUserStoryDescriptor} that we are building.
     */
    public EditUserStoryDescriptorBuilder withFunction(String function) {
        descriptor.setFunction(new UserStoryFunction(function));
        return this;
    }

    /**
     * Sets the {@code userStoryReason} of the {@code EditUserStoryDescriptor} that we are building.
     */
    public EditUserStoryDescriptorBuilder withReason(String reason) {
        descriptor.setReason(new UserStoryReason(reason));
        return this;
    }

    /**
     * Sets the {@code userStoryImportance} of the {@code EditUserStoryDescriptor} that we are building.
     */
    public EditUserStoryDescriptorBuilder withImportance(String importance) {
        descriptor.setImportance(new UserStoryImportance(importance));
        return this;
    }

    public EditProjectUserStoryCommand.EditUserStoryDescriptor build() {
        return descriptor;
    }
}
