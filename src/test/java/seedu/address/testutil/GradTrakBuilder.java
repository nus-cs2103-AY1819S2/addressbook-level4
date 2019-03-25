package seedu.address.testutil;

import seedu.address.model.GradTrak;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code GradTrak ab = new GradTrakBuilder().withPerson("John", "Doe").build();}
 */
public class GradTrakBuilder {

    private GradTrak addressBook;

    public GradTrakBuilder() {
        addressBook = new GradTrak();
    }

    public GradTrakBuilder(GradTrak addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code ModuleTaken} to the {@code GradTrak} that we are building.
     */
    public GradTrakBuilder withPerson(ModuleTaken moduleTaken) {
        addressBook.addModuleTaken(moduleTaken);
        return this;
    }

    public GradTrak build() {
        return addressBook;
    }
}
