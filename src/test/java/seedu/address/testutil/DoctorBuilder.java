package seedu.address.testutil;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Age;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Specialisation;
import seedu.address.model.util.SampleDataUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A utility class to help with building Doctor objects.
 */
public class DoctorBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_AGE = "23";

    private Name name;
    private Phone phone;
    private Gender gender;
    private Age age;
    private Set<Specialisation> specialisations;
    private List<Appointment> appointments;

    public DoctorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        gender = new Gender(DEFAULT_GENDER);
        age = new Age(DEFAULT_AGE);
        specialisations = new HashSet<>();
        appointments = new ArrayList<>();
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code doctorToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        name = doctorToCopy.getName();
        phone = doctorToCopy.getPhone();
        gender = doctorToCopy.getGender();
        age = doctorToCopy.getAge();
        specialisations = new HashSet<>(doctorToCopy.getSpecs());
    }

    /**
     * Sets the {@code Name} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code specialisations} into a {@code Set<Specialisation>}
     * and set it to the {@code Doctor} that we are building.
     */
    public DoctorBuilder withSpecs(String ... specs) {
        this.specialisations = SampleDataUtil.getSpecSet(specs);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Appointment} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withAppointments(String ... appointments) {
        this.appointments = SampleDataUtil.getAppointments(appointments);
        return this;
    }


    public Doctor build() {
        return new Doctor(name, phone, gender, age, specialisations);
    }

}
