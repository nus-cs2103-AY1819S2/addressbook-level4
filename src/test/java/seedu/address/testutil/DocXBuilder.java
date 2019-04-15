package seedu.address.testutil;

import seedu.address.model.DocX;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

/**
 * A utility class to help with building DocX objects.
 * Example usage: <br>
 *     {@code DocX ab = new DocXBuilder().withPatient("John", "Doe").build();}
 *     {@code DocX ab = new DocXBuilder().withDoctor("Alvina", "Steven").build();}
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

    /**
     * Adds a new {@code Doctor} to the {@code DocX} that we are building.
     */
    public DocXBuilder withDoctor(Doctor doctor) {
        docX.addDoctor(doctor);
        return this;
    }

    public DocX build() {
        return docX;
    }
}
