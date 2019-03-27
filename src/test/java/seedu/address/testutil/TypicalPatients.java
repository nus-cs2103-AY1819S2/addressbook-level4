package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

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
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalPatientddressBook() {
        AddressBook ab = new AddressBook();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CHUCK));
    }
}
