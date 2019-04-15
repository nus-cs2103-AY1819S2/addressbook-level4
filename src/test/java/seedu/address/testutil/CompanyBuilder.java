package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class CompanyBuilder {

    public static final String DEFAULT_NAME = "McDonalds";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "mcd@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SALARY = "5000";
    public static final String DEFAULT_OCCUPATION = "food";
    public static final String DEFAULT_RELATIONSHIP = "conglomerate";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Salary salary;
    private Occupation occupation;
    private Relationship relationship;
    private Set<Tag> tags;

    public CompanyBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        salary = new Salary(DEFAULT_SALARY);
        occupation = new Occupation(DEFAULT_OCCUPATION);
        relationship = new Relationship(DEFAULT_RELATIONSHIP);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code companyToCopy}.
     */
    public CompanyBuilder(Company companyToCopy) {
        name = companyToCopy.getName();
        phone = companyToCopy.getPhone();
        email = companyToCopy.getEmail();
        address = companyToCopy.getAddress();
        salary = companyToCopy.getSalary();
        occupation = companyToCopy.getOccupation();
        relationship = companyToCopy.getRelationship();
        tags = new HashSet<>(companyToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CompanyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CompanyBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public CompanyBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Person} that we are building.
     */
    public CompanyBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    /**
     * Sets the {@code Occupation} of the {@code Person} that we are building.
     */
    public CompanyBuilder withOccupation(String occupation) {
        this.occupation = new Occupation(occupation);
        return this;
    }

    /**
     * Sets the {@code Relationship} of the {@code Person} that we are building.
     */
    public CompanyBuilder withRelationship(String relationship) {
        this.relationship = new Relationship(relationship);
        return this;
    }

    public Company build() {
        return new Company(name, phone, email, address, salary, occupation, relationship, tags);
    }

}

