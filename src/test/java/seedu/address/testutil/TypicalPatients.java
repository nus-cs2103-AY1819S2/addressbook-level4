package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HBP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STROKE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DocX;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withGender("F").withAge("23").withPhone("94351251")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withTags("friends").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withGender("M").withAge("30").withPhone("98765432")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withTags("owesMoney", "friends").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz")
            .withGender("M").withAge("101").withPhone("95352563").withAddress("wall street").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier")
            .withGender("M").withAge("7").withPhone("87652533").withAddress("10th street").withTags("friends").build();
    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer")
            .withGender("F").withAge("19").withPhone("94822241").withAddress("michegan ave").build();
    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz")
            .withGender("F").withAge("110").withPhone("94824271").withAddress("little tokyo").build();
    public static final Patient GEORGE = new PatientBuilder().withName("George Best")
            .withGender("M").withAge("50").withPhone("94824421").withAddress("4th street").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier")
            .withGender("F").withAge("23").withPhone("84824241")
            .withAddress("little india").build();
    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller")
            .withGender("F").withAge("23").withPhone("84821311")
            .withAddress("chicago ave").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY)
            .withGender(VALID_GENDER_AMY).withAge(VALID_AGE_AMY).withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_HBP).build();
    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB)
            .withGender(VALID_GENDER_BOB).withAge(VALID_AGE_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_STROKE, VALID_TAG_HBP)
            .build();


    public static final Doctor ALVINA = new DoctorBuilder().withName("Alvina Ong")
            .withAge("28").withGender("F").withPhone("82376447").withSpecs("acupuncture").build();
    public static final Doctor BOND = new DoctorBuilder().withName("John Doe")
            .withAge("21").withGender("M").withPhone("91612342").withSpecs("general", "acupuncture").build();
    public static final Doctor CHARLIE = new DoctorBuilder().withName("Carl Kurz").withPhone("95352563")
            .withGender("M").withAge("33").build();
    public static final Doctor DAM = new DoctorBuilder().withName("Daniel Meier").withPhone("87652533")
            .withGender("M").withAge("59").withSpecs("massage").build();
    public static final Doctor ELSIE = new DoctorBuilder().withName("Elle Meyer").withPhone("94822241")
            .withGender("F").withAge("44").build();
    public static final Doctor FLORA = new DoctorBuilder().withName("Fiona Kunz").withPhone("94824271")
            .withGender("F").withAge("34").build();
    public static final Doctor GAN = new DoctorBuilder().withName("George Best").withPhone("94824421")
            .withGender("F").withAge("41").build();

    // Manually added
    public static final Doctor HAN = new DoctorBuilder().withName("Hoon Meier").withPhone("84824241")
            .withGender("M").withAge("38").build();
    public static final Doctor ILI = new DoctorBuilder().withName("Ida Mueller").withPhone("84821311")
            .withGender("F").withAge("28").build();

    // Manually added - Doctor's details found in {@code CommandTestUtil}
    public static final Doctor JOHN = new DoctorBuilder().withName(VALID_NAME_JOHN).withPhone(VALID_PHONE_JOHN)
            .withGender(VALID_GENDER_JOHN).withAge(VALID_AGE_JOHN)
            .withSpecs(VALID_SPECIALISATION_ACUPUNCTURE, VALID_SPECIALISATION_MASSAGE).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code DocX} with all the typical patients and doctors.
     */
    public static DocX getTypicalDocX() {
        DocX ab = new DocX();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        for (Doctor doctor : getTypicalDoctors()) {
            ab.addDoctor(doctor);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(ALVINA, BOND, CHARLIE, DAM, ELSIE, FLORA, GAN));
    }

}
