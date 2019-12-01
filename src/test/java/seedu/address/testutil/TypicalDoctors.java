package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_STEVEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import javax.print.Doc;

import seedu.address.model.DocX;
import seedu.address.model.person.doctor.Doctor;

/**
 * A utility class containing a list of {@code Doctor} objects to be used in tests.
 */
public class TypicalDoctors {

    public static final Doctor AMELIA = new DoctorBuilder().withName("Amelia Lim").withPhone("67253742").withGender("F")
            .withYear("2").withSpecs("surgery", "general").build();
    public static final Doctor BOND = new DoctorBuilder().withName("Bond Meier")
            .withYear("10").withGender("M").withPhone("60923911").withSpecs("general", "acupuncture").build();
    public static final Doctor CHARLIE = new DoctorBuilder().withName("Charlie Cruzlei").withPhone("95352563")
            .withGender("M").withYear("33").build();
    public static final Doctor DAM = new DoctorBuilder().withName("Dam Meier").withPhone("87652533")
            .withGender("M").withYear("11").withSpecs("massage").build();
    public static final Doctor ELSIE = new DoctorBuilder().withName("Elsie Lim").withPhone("94822241")
            .withGender("F").withYear("5").build();
    public static final Doctor FLORA = new DoctorBuilder().withName("Flora Winx").withPhone("94824271")
            .withGender("F").withYear("3").build();
    public static final Doctor GAN = new DoctorBuilder().withName("Gan Chong").withPhone("94824421")
            .withGender("F").withYear("8").build();

    // Manually added
    public static final Doctor HAN = new DoctorBuilder().withName("Han Meier").withPhone("84824241")
            .withGender("M").withYear("38").build();
    public static final Doctor ILLIOT = new DoctorBuilder().withName("Illiot Mueller").withPhone("84821311")
            .withGender("F").withYear("18").build();

    // Manually added - Doctor's details found in {@code CommandTestUtil}
    public static final Doctor ALVINA = new DoctorBuilder().withName(VALID_NAME_ALVINA)
            .withYear(VALID_YEAR_ALVINA).withGender(VALID_GENDER_ALVINA).withPhone(VALID_PHONE_ALVINA)
            .withSpecs(VALID_SPECIALISATION_ACUPUNCTURE).build();
    public static final Doctor STEVEN = new DoctorBuilder().withName(VALID_NAME_STEVEN).withPhone(VALID_PHONE_STEVEN)
            .withGender(VALID_GENDER_STEVEN).withYear(VALID_YEAR_STEVEN)
            .withSpecs(VALID_SPECIALISATION_ACUPUNCTURE, VALID_SPECIALISATION_MASSAGE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDoctors() {} // prevents instantiation

    /**
     * Returns an {@code docX} with all the typical doctors.
     */
    public static DocX getTypicalDocX_doctor() {
        DocX ab = new DocX();
        for (Doctor doctor : getTypicalDoctors()) {
            ab.addDoctor(doctor);
        }
        return ab;
    }

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(AMELIA, BOND, CHARLIE, DAM, ELSIE, FLORA, GAN));
    }
}
