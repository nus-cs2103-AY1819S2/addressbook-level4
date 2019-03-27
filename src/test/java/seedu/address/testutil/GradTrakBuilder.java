package seedu.address.testutil;

import seedu.address.model.GradTrak;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code GradTrak ab = new GradTrakBuilder().withPerson("John", "Doe").build();}
 */
public class GradTrakBuilder {

    private GradTrak addressBook;
    private Semester currentSemester;

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

    /**
     * Sets the currentSemester to the given Semester.
     */
    public GradTrakBuilder withCurrentSemester(Semester currentSemester) {
        this.currentSemester = currentSemester;
        return this;
    }

    public GradTrak build() {
        return addressBook;
    }
}
