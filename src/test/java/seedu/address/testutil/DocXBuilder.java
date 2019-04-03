package seedu.address.testutil;

import seedu.address.model.DocX;
import seedu.address.model.person.Patient;

/**
 * A utility class to help with building DocX objects.
 * Example usage: <br>
 *     {@code DocX ab = new DocXBuilder().withPatient("John", "Doe").build();}
 */
public class DocXBuilder {

    private DocX docX;

    public DocXBuilder() {
        docX = new DocX();
    }

    public DocXBuilder(DocX docX) {
        this.docX = docX;
    }

    /**
     * Adds a new {@code Patient} to the {@code DocX} that we are building.
     */
    public DocXBuilder withPatient(Patient patient) {
        docX.addPatient(patient);
        return this;
    }

    public DocX build() {
        return docX;
    }
}
