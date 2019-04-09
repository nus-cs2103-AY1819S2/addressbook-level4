package quickdocs.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import quickdocs.model.QuickDocs;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new Patient(
            new Name("Alice"),
            new Nric("S1111111A"),
            new Email("alice@gmail.com"),
            new Address("1 Admiralty Road"),
            new Contact("91111111"),
            new Gender("F"),
            new Dob("1990-01-01"),
            new ArrayList<Tag>(Arrays.asList(new Tag("Diabetes")))
    );

    public static final Patient BOB = new Patient(
            new Name("Bob"),
            new Nric("S2222222A"),
            new Email("bob@gmail.com"),
            new Address("1 Bishan Road"),
            new Contact("92222222"),
            new Gender("M"),
            new Dob("1985-02-02"),
            new ArrayList<Tag>(Arrays.asList(new Tag("Gout")))
    );

    public static final Patient CHUCK = new Patient(
            new Name("Chuck"),
            new Nric("S3333333C"),
            new Email("chuck@gmail.com"),
            new Address("1 Clementi Road"),
            new Contact("93333333"),
            new Gender("M"),
            new Dob("1980-03-03"),
            new ArrayList<Tag>(Arrays.asList(new Tag("Highbloodpressure")))
    );

    // manually edited

    public static final Patient EVE = new Patient(
            new Name("Eve"),
            new Nric("S5555555E"),
            new Email("eve@gmail.com"),
            new Address("5 Edelweiss Road"),
            new Contact("95555555"),
            new Gender("F"),
            new Dob("2000-05-05"),
            new ArrayList<Tag>(Arrays.asList())
    );

    public static final Patient EDITED_BOB = new Patient(
            new Name("Bob"),
            new Nric("S2222333B"),
            new Email("bob@gmail.com"),
            new Address("2 Bishan Road"),
            new Contact("92222222"),
            new Gender("M"),
            new Dob("1985-02-02"),
            new ArrayList<Tag>(Arrays.asList(new Tag("Gout")))
    );

    /**
     * Returns an {@code QuickDocs} with all the typical persons.
     */
    public static QuickDocs getTypicalPatientQuickDocs() {
        QuickDocs qd = new QuickDocs();
        for (Patient patient : getTypicalPatients()) {
            qd.getPatientManager().addPatient(patient);
        }
        return qd;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CHUCK));
    }
}
