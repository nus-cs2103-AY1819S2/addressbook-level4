package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.doctor.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.specialisation.Specialisation;


/**
 * A utility class to help with building EditPatientDescriptor objects.
 */
public class EditDoctorDescriptorBuilder {

    private EditDoctorDescriptor descriptor;

    public EditDoctorDescriptorBuilder() {
        descriptor = new EditDoctorDescriptor();
    }

    public EditDoctorDescriptorBuilder(EditDoctorDescriptor descriptor) {
        this.descriptor = new EditDoctorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDoctorDescriptor} with fields containing {@code doctor}'s details
     */
    public EditDoctorDescriptorBuilder(Doctor doctor) {
        descriptor = new EditDoctorDescriptor();
        descriptor.setName(doctor.getName());
        descriptor.setGender(doctor.getGender());
        descriptor.setYear(doctor.getYear());
        descriptor.setPhone(doctor.getPhone());
        descriptor.setSpecs(doctor.getSpecs());
    }

    /**
     * Sets the {@code Name} of the {@code EditDoctorDescriptor} that we are building.
     */
    public EditDoctorDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditDoctorDescriptor} that we are building.
     */
    public EditDoctorDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code EditDoctorDescriptor} that we are building.
     */
    public EditDoctorDescriptorBuilder withYear(String year) {
        descriptor.setYear(new Year(year));
        return this;
    }
    /**
     * Sets the {@code Phone} of the {@code EditDoctorDescriptor} that we are building.
     */
    public EditDoctorDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Parses the {@code specs} into a {@code Set<Specialisation>} and set it to the {@code EditDoctorDescriptor}
     * that we are building.
     */
    public EditDoctorDescriptorBuilder withSpecs(String... specs) {
        Set<Specialisation> specSet = Stream.of(specs).map(Specialisation::new).collect(Collectors.toSet());
        descriptor.setSpecs(specSet);
        return this;
    }

    public EditDoctorDescriptor build() {
        return descriptor;
    }
}
