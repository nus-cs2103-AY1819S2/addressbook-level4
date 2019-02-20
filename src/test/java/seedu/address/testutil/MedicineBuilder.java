package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.medicine.Address;
import seedu.address.model.medicine.Email;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Medicine objects.
 */
public class MedicineBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public MedicineBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MedicineBuilder with the data of {@code medicineToCopy}.
     */
    public MedicineBuilder(Medicine medicineToCopy) {
        name = medicineToCopy.getName();
        phone = medicineToCopy.getPhone();
        email = medicineToCopy.getEmail();
        address = medicineToCopy.getAddress();
        tags = new HashSet<>(medicineToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Medicine} that we are building.
     */
    public MedicineBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Medicine} that we are building.
     */
    public MedicineBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Medicine build() {
        return new Medicine(name, phone, email, address, tags);
    }

}
