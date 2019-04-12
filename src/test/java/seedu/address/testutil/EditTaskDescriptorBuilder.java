package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.patient.Nric;
import seedu.address.model.person.Name;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setTitle(task.getTitle());
        descriptor.setStartDate(task.getStartDate());
        descriptor.setEndDate(task.getEndDate());
        descriptor.setStartTime(task.getStartTime());
        descriptor.setEndTime(task.getEndTime());
        descriptor.setPriority(task.getPriority());
        descriptor.setPatientIndex(null);
        descriptor.setLinkedPatient(task.getLinkedPatient());
    }

    /**
     * Sets the {@code Title} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new DateCustom(startDate));
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(new DateCustom(endDate));
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new TimeCustom(startTime));
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new TimeCustom(endTime));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(Priority.returnPriority(priority));
        return this;
    }

    /**
     * Sets the {@code PatientIndex} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPatientIndex(Index index) {
        descriptor.setPatientIndex(index);
        return this;
    }

    /**
     * Sets the {@code LinkedPatient} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withLinkedPatient(String name, String nric) {
        descriptor.setLinkedPatient(new LinkedPatient(new Name(name), new Nric(nric)));
        return this;
    }

    /**
     * Sets the {@code LinkedPatient} of the {@code EditTaskDescriptor} that we are building to null;
     */
    public EditTaskDescriptorBuilder withNoLinkedPatient() {
        descriptor.setLinkedPatient(null);
        return this;
    }


    public EditTaskDescriptor build() {
        return descriptor;
    }
}
