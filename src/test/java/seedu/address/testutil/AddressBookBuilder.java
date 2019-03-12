package seedu.address.testutil;

import seedu.address.model.PdfBook;
import seedu.address.model.pdf.Pdf;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code PdfBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private PdfBook addressBook;

    public AddressBookBuilder() {
        addressBook = new PdfBook();
    }

    public AddressBookBuilder(PdfBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Pdf} to the {@code PdfBook} that we are building.
     */
    public AddressBookBuilder withPerson(Pdf pdf) {
        addressBook.addPdf(pdf);
        return this;
    }

    public PdfBook build() {
        return addressBook;
    }
}
