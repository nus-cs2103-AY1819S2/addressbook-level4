package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.medicine.Medicine;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withMedicine("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Medicine} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withMedicine(Medicine medicine) {
        addressBook.addMedicine(medicine);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
