package seedu.hms.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.tag.Tag;
import seedu.hms.model.util.SampleDataUtil;

/**
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_DOB = "01/02/1986";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_IDENTIFICATION_N0 = "1223453";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private DateOfBirth dob;
    private Email email;
    private IdentificationNo idnum;
    private Address address;
    private Set<Tag> tags;

    public CustomerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        dob = new DateOfBirth(DEFAULT_DOB);
        email = new Email(DEFAULT_EMAIL);
        idnum = new IdentificationNo(DEFAULT_IDENTIFICATION_N0);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CustomerBuilder with the data of {@code customerToCopy}.
     */
    public CustomerBuilder(Customer customerToCopy) {
        name = customerToCopy.getName();
        phone = customerToCopy.getPhone();
        dob = customerToCopy.getDateOfBirth();
        email = customerToCopy.getEmail();
        idnum = customerToCopy.getIdNum();
        address = customerToCopy.getAddress();
        tags = new HashSet<>(customerToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Customer} that we are building.
     */
    public CustomerBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code address} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code IdentificationNo} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withIdNum(String idnum) {
        this.idnum = new IdentificationNo(idnum);
        return this;
    }

    /**
     * Sets the {@code IdentificationNo} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withDateOfBirth(String dob) {
        this.dob = new DateOfBirth(dob);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Customer build() {
        return new Customer(name, phone, dob, email, idnum, address, tags);
    }

}
