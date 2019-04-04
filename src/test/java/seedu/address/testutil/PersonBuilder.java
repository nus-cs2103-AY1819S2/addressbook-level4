package seedu.address.testutil;

import seedu.address.model.battleship.Name;
import seedu.address.model.cell.Address;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Email;
import seedu.address.model.cell.Phone;

/**
 * A utility class to help with building Cell objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code cellToCopy}.
     */
    public PersonBuilder(Cell cellToCopy) {
        name = cellToCopy.getName();
        phone = cellToCopy.getPhone();
        email = cellToCopy.getEmail();
        address = cellToCopy.getAddress();
    }

    /**
     * Sets the {@code Name} of the {@code Cell} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Cell} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Cell} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Cell} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Cell build() {
        return new Cell(name, phone, email, address);
    }

}
