package seedu.address.testutil;

import seedu.address.logic.commands.EditProjectInfoCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Client;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectDescription;
import seedu.address.model.project.ProjectName;

/**
 * A utility class to help with building EditProjectDescriptor objects.
 */
public class EditProjectDescriptorBuilder {

    private EditProjectInfoCommand.EditProjectDescriptor descriptor;

    public EditProjectDescriptorBuilder() {
        descriptor = new EditProjectInfoCommand.EditProjectDescriptor();
    }

    public EditProjectDescriptorBuilder(EditProjectInfoCommand.EditProjectDescriptor descriptor) {
        this.descriptor = new EditProjectInfoCommand.EditProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProjectDescriptor} with fields containing {@code Project}'s details
     */
    public EditProjectDescriptorBuilder(Project project) {
        descriptor = new EditProjectInfoCommand.EditProjectDescriptor();
        descriptor.setProjectName(project.getProjectName());
        descriptor.setClient(project.getClient());
        descriptor.setDeadline(project.getDeadline());
        descriptor.setDescription(project.getDescription());
    }

    /**
     * Sets the {@code EmployeeName} of the {@code EditProjectDescriptor} that we are building.
     */
    public seedu.address.testutil.EditProjectDescriptorBuilder withName(String name) {
        descriptor.setProjectName(new ProjectName(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditProjectDescriptor} that we are building.
     */
    public seedu.address.testutil.EditProjectDescriptorBuilder withClient(String client) {
        descriptor.setClient(new Client(client));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditProjectDescriptor} that we are building.
     */
    public seedu.address.testutil.EditProjectDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new ProjectDescription(description));
        return this;
    }

    /**
     * Sets the {@code GitHubAccount} of the {@code EditProjectDescriptor} that we are building.
     */
    public seedu.address.testutil.EditProjectDescriptorBuilder withDeadline(String deadline) throws ParseException {
        descriptor.setDeadline(ParserUtil.parseDate(deadline));
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code EditProjectDescriptor}
     * that we are building.
     */

    public EditProjectInfoCommand.EditProjectDescriptor build() {
        return descriptor;
    }
}


