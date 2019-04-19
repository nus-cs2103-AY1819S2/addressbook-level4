package seedu.giatros.testutil;

import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_IBUPROFEN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDH;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDHM;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withAllergies("amoxicillin")
            .withAppointments("2019-01-01 00:00:00").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withAllergies("penicillin", "ibuprofen")
            .withAppointments("2019-04-19 10:00", "2019-04-27 10:00").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withAllergies("penicillin").build();
    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Patient GEORGE = new PatientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withAllergies(VALID_ALLERGY_IBUPROFEN)
            .withAppointments(VALID_APPOINTMENT_YMDH).build();
    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withAllergies(VALID_ALLERGY_AMPICILLIN, VALID_ALLERGY_IBUPROFEN)
            .withAppointments(VALID_APPOINTMENT_YMDH, VALID_APPOINTMENT_YMDHM).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code GiatrosBook} with all the typical patients.
     */
    public static GiatrosBook getTypicalGiatrosBook() {
        GiatrosBook ab = new GiatrosBook();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
