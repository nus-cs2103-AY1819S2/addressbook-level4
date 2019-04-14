package seedu.address.testutil;

import seedu.address.model.FashionMatch;
import seedu.address.model.apparel.Apparel;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FashionMatch ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private FashionMatch addressBook;

    public AddressBookBuilder() {
        addressBook = new FashionMatch();
    }

    public AddressBookBuilder(FashionMatch addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Apparel} to the {@code FashionMatch} that we are building.
     */
    public AddressBookBuilder withPerson(Apparel apparel) {
        addressBook.addApparel(apparel);
        return this;
    }

    public FashionMatch build() {
        return addressBook;
    }
}
