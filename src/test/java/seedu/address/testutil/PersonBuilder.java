package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Degree;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gpa;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.SkillsTag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_EDUCATION = "NUS";
    public static final String DEFAULT_GPA = "3";
    public static final String DEFAULT_DEGREE = "Bachelors";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Education education;
    private Gpa gpa;
    private Degree degree;
    private Address address;
    private Set<SkillsTag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        education = new Education(DEFAULT_EDUCATION);
        gpa = new Gpa(DEFAULT_GPA);
        degree = new Degree(DEFAULT_DEGREE);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        education = personToCopy.getEducation();
        gpa = personToCopy.getGpa();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<SkillsTag>}, appends any other tags already present
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSkills(String ... skills) {
        this.tags.addAll(SampleDataUtil.getTagSet(Arrays.asList(skills), null, null));
        return this;
    }

    /**
     * Changes the skills to directly given values
     */
    public PersonBuilder changeSkills(String ... skills) {
        this.tags = SampleDataUtil.getTagSet(Arrays.asList(skills), null, null);
        return this;
    }

    /**
     * Parses the {@code positions} into a {@code Set<SkillsTag>}, appends any other tags already present
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withPositions(String ... positions) {
        Set otherTags = this.tags;
        Set newTags = SampleDataUtil.getTagSet(null, Arrays.asList(positions), null);
        otherTags.addAll(newTags);
        return this;
    }

    /**
     * Parses the {@code endorsements} into a {@code Set<SkillsTag>}, appends any other tags already present
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withEndorsements(String ... endorsements) {
        Set otherTags = this.tags;
        Set newTags = SampleDataUtil.getTagSet(null, null, Arrays.asList(endorsements));
        otherTags.addAll(newTags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Education} of the {@code Person} that we are building.
     */
    public PersonBuilder withEducation(String education) {
        this.education = new Education(education);
        return this;
    }

    /**
     * Sets the {@code GPA} of the {@code Person} that we are building.
     */
    public PersonBuilder withGpa(String gpa) {
        this.gpa = new Gpa(gpa);
        return this;
    }

    /**
     * Sets the {@code GPA} of the {@code Person} that we are building.
     */
    public PersonBuilder withDegree(String degree) {
        this.degree = new Degree(degree);
        return this;
    }

    /**
     * {@code Person} that we are building.
     */
    public Person build() {
        return new Person(name, phone, email, education, gpa, degree, address, tags);
    }

}
