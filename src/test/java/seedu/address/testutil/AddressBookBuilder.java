package seedu.address.testutil;

import seedu.address.model.DocX;
import seedu.address.model.person.Patient;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code DocX ab = new AddressBookBuilder().withPatient("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private DocX docX;

    public AddressBookBuilder() {
        docX = new DocX();
    }

    public AddressBookBuilder(DocX docX) {
        this.docX = docX;
    }

    /**
     * Adds a new {@code Patient} to the {@code DocX} that we are building.
     */
    public AddressBookBuilder withPatient(Patient patient) {
        docX.addPatient(patient);
        return this;
    }

    public DocX build() {
        return docX;
    }
}
