package seedu.equipment.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Email;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;
import seedu.equipment.model.util.SampleDataUtil;

/**
 * A utility class to help with building Equipment objects.
 */
public class EquipmentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SERIALNUMBER = "A008842X";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private SerialNumber serialNumber;
    private Set<Tag> tags;

    public EquipmentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        serialNumber = new SerialNumber(DEFAULT_SERIALNUMBER);
        tags = new HashSet<>();
    }

    /**
     * Initializes the EquipmentBuilder with the data of {@code equipmentToCopy}.
     */
    public EquipmentBuilder(Equipment equipmentToCopy) {
        name = equipmentToCopy.getName();
        phone = equipmentToCopy.getPhone();
        email = equipmentToCopy.getEmail();
        address = equipmentToCopy.getAddress();
        serialNumber = equipmentToCopy.getSerialNumber();
        tags = new HashSet<>(equipmentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withSerialNumber(String serialNumber) {
        this.serialNumber = new SerialNumber(serialNumber);
        return this;
    }

    public Equipment build() {
        return new Equipment(name, phone, email, address, serialNumber, tags);
    }

}
