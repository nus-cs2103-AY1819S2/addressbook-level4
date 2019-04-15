package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Doctor objects.
 */
public class DoctorBuilder {

    public static final String DEFAULT_NAME = "Zhang Jie";
    public static final String DEFAULT_PHONE = "88734584";
    public static final String DEFAULT_GENDER = "M";
    public static final String DEFAULT_YEAR = "10";

    private Name name;
    private Phone phone;
    private Gender gender;
    private Year year;
    private Set<Specialisation> specialisations;

    public DoctorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        gender = new Gender(DEFAULT_GENDER);
        year = new Year(DEFAULT_YEAR);
        specialisations = new HashSet<>();
        specialisations.add(new Specialisation("acupuncture"));
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code doctorToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        name = doctorToCopy.getName();
        gender = doctorToCopy.getGender();
        year = doctorToCopy.getYear();
        phone = doctorToCopy.getPhone();
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
     * Sets the {@code Year of experience} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withYear(String year) {
        this.year = new Year(year);
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

    public Doctor build() {
        return new Doctor(name, phone, gender, year, specialisations);
    }

}
