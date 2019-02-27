package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.place.Place;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPlace("John", "Doe").build();}
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
     * Adds a new {@code Place} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPlace(Place place) {
        addressBook.addPlace(place);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
