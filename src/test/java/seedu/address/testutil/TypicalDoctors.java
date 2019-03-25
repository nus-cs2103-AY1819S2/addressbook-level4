package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_MASSAGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import javax.print.Doc;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Doctor;

/**
 * A utility class containing a list of {@code Doctor} objects to be used in tests.
 */
public class TypicalDoctors {

    public static final Doctor ALICE = new DoctorBuilder().withName("Alice Pauline")
            .withAge("23").withGender("F").withPhone("94351253").withSpecs("general").build();
    public static final Doctor BENSON = new DoctorBuilder().withName("Benson Meier")
            .withAge("31").withGender("M").withPhone("98765432").withSpecs("general", "acupuncture").build();
    public static final Doctor CARL = new DoctorBuilder().withName("Carl Kurz").withPhone("95352563")
            .withGender("M").withAge("33").build();
    public static final Doctor DANIEL = new DoctorBuilder().withName("Daniel Meier").withPhone("87652533")
            .withGender("M").withAge("59").withSpecs("massage").build();
    public static final Doctor ELLE = new DoctorBuilder().withName("Elle Meyer").withPhone("94822241")
            .withGender("F").withAge("44").build();
    public static final Doctor FIONA = new DoctorBuilder().withName("Fiona Kunz").withPhone("94824271")
            .withGender("F").withAge("34").build();
    public static final Doctor GEORGE = new DoctorBuilder().withName("George Best").withPhone("94824421")
            .withGender("F").withAge("41").build();

    // Manually added
    public static final Doctor HOON = new DoctorBuilder().withName("Hoon Meier").withPhone("84824241")
            .withGender("M").withAge("38").build();
    public static final Doctor IDA = new DoctorBuilder().withName("Ida Mueller").withPhone("84821311")
            .withGender("F").withAge("28").build();

    // Manually added - Doctor's details found in {@code CommandTestUtil}
    public static final Doctor JOHN = new DoctorBuilder().withName(VALID_NAME_JOHN).withPhone(VALID_PHONE_JOHN)
            .withGender(VALID_GENDER_JOHN).withAge(VALID_AGE_JOHN)
            .withSpecs(VALID_SPECIALISATION_ACUPUNCTURE, VALID_SPECIALISATION_MASSAGE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDoctors() {} // prevents instantiation

    /**
     * Returns an {@code docX} with all the typical doctors.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Doctor doctor : getTypicalDoctors()) {
            ab.addDoctor(doctor);
        }
        return ab;
    }

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
