package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.patient.Patient;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = ((PatientBuilder) new PatientBuilder()
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withNric("S9670515H")
            .withTags("Physiotherapy", "Dialysis")).build();
    public static final Patient BENSON = ((PatientBuilder) new PatientBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withNric("S9274100D")
            .withTags("Eldercare", "Stroke")).build();
    public static final Patient CARL = ((PatientBuilder) new PatientBuilder()
            .withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street")
            .withEmail("heinz@example.com")
            .withPhone("87652533")
            .withNric("S9328723A")
            .withTags("Palliative")).build();
    public static final Patient DANIEL = ((PatientBuilder) new PatientBuilder()
            .withName("Daniel Meier")
            .withAddress("10th street")
            .withEmail("cornelia@example.com")
            .withPhone("82015737")
            .withNric("S2652663Z")
            .withTags("friends")).build();
    public static final Patient ELLE = ((PatientBuilder) new PatientBuilder()
            .withName("Elle Meyer")
            .withAddress("michegan ave")
            .withEmail("werner@example.com")
            .withPhone("9482224")
            .withNric("S9462345E")
            .withTags("Dementia")).build();
    public static final Patient FIONA = ((PatientBuilder) new PatientBuilder()
            .withName("Fiona Kunz")
            .withAddress("little tokyo")
            .withEmail("lydia@example.com")
            .withPhone("9482427")
            .withNric("S5450367F")
            .withTags("Cancer")).build();
    public static final Patient GEORGE = ((PatientBuilder) new PatientBuilder()
            .withName("George Best")
            .withAddress("4th street")
            .withEmail("anna@example.com")
            .withPhone("9482442")
            .withNric("S8736498R")
            .withTags("Alzheimers")).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalPatientBook() {
        AddressBook ab = new AddressBook();
        for (Patient person : getTypicalPatients()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
