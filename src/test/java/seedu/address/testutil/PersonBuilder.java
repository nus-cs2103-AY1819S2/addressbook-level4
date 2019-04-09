package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "96428463";
    public static final String DEFAULT_EMAIL = "alicepauline@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "this is a default person";
    public static final String DEFAULT_SELLINGPRICE = "500000";
    public static final String DEFAULT_RENTALPRICE = "1000";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Price sellingPrice;
    private Price rentalPrice;
    private Remark remark;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        remark = personToCopy.getRemark();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code sellerToCopy}.
     */
    public PersonBuilder(Seller sellerToCopy) {
        name = sellerToCopy.getName();
        phone = sellerToCopy.getPhone();
        email = sellerToCopy.getEmail();
        remark = sellerToCopy.getRemark();
        address = sellerToCopy.getAddress();
        sellingPrice = sellerToCopy.getSellingPrice();
        tags = sellerToCopy.getTags();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code landlordToCopy}.
     */
    public PersonBuilder(Landlord landlordToCopy) {
        name = landlordToCopy.getName();
        phone = landlordToCopy.getPhone();
        email = landlordToCopy.getEmail();
        remark = landlordToCopy.getRemark();
        address = landlordToCopy.getAddress();
        rentalPrice = landlordToCopy.getRentalPrice();
        tags = landlordToCopy.getTags();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code sellingPrice} of the {@code Person} that we are building.
     */
    public PersonBuilder withSellingPrice(String sellingPrice) {
        this.sellingPrice = new Price(sellingPrice);
        return this;
    }

    /**
     * Sets the {@code rentalPrice} of the {@code Person} that we are building.
     */
    public PersonBuilder withRentalPrice(String rentalPrice) {
        this.rentalPrice = new Price(rentalPrice);
        return this;
    }


    public Buyer buildBuyer() {
        return new Buyer(name, phone, email, remark);
    }

    public Seller buildSeller() {
        return new Seller(name, phone, email, remark,
                new Property(Property.PROPERTY_TYPE_SELL, address, sellingPrice, tags));
    }

    public Landlord buildLandlord() {
        return new Landlord(name, phone, email, remark,
                new Property(Property.PROPERTY_TYPE_RENT, address, rentalPrice, tags));
    }
    public Tenant buildTenant() {
        return new Tenant(name, phone, email, remark);
    }

    public Person build() {
        return new Person(name, phone, email, remark);
    }
}
