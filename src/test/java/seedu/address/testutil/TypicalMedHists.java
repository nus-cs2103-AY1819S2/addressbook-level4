package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DocX;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

/**
 * A utility class containing a list of {@code Medical History} objects to be used in tests.
 */
public class TypicalMedHists {

    public static final MedicalHistory MED_HIST1 = new MedicalHistory(new PersonId("1"), new PersonId("7"),
            new ValidDate("2019-03-03"), new WriteUp("The patient got a high fever."));
    public static final MedicalHistory MED_HIST2 = new MedicalHistory(new PersonId("2"), new PersonId("7"),
            new ValidDate("2019-03-03"), new WriteUp("The patient had a sneeze."));
    public static final MedicalHistory MED_HIST3 = new MedicalHistory(new PersonId("2"), new PersonId("8"),
            new ValidDate("2019-01-30"), new WriteUp("The patient had a stomachache. "
            + "I gave him some medicine to release the pain."));
    public static final MedicalHistory MED_HIST4 = new MedicalHistory(new PersonId("3"), new PersonId("9"),
            new ValidDate("2019-04-03"), new WriteUp("Had a fever with sore throat. Sleeps late."));
    public static final MedicalHistory MED_HIST5 = new MedicalHistory(new PersonId("4"), new PersonId("10"),
            new ValidDate("2019-02-25"),
            new WriteUp("Came down with a stomach flu, possibly due to eating expired food"));

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

    public static final Doctor ALVINA = new DoctorBuilder().withName("Alvina Ong")
            .withYear("21").withGender("F").withPhone("82376447").withSpecs("acupuncture").build();
    public static final Doctor BOND = new DoctorBuilder().withName("Bond Park")
            .withYear("10").withGender("M").withPhone("91612342").withSpecs("general", "acupuncture").build();
    public static final Doctor CHARLIE = new DoctorBuilder().withName("Charlie Cruzlei").withPhone("95352563")
            .withGender("M").withYear("33").build();
    public static final Doctor DAM = new DoctorBuilder().withName("Dam Dong").withPhone("87652533")
            .withGender("M").withYear("11").withSpecs("massage").build();
    public static final Doctor ELSIE = new DoctorBuilder().withName("Elsie Lim").withPhone("94822241")
            .withGender("F").withYear("5").build();
    public static final Doctor FLORA = new DoctorBuilder().withName("Flora Winx").withPhone("94824271")
            .withGender("F").withYear("3").build();
    public static final Doctor GAN = new DoctorBuilder().withName("Gan Chong").withPhone("94824421")
            .withGender("F").withYear("8").build();

    private TypicalMedHists() {
    } // prevents instantiation

    /**
     * Returns an {@code DocX} with all the typical Medical History.
     */
    public static DocX getTypicalDocX() {
        DocX ab = new DocX();
        for (MedicalHistory medicalHistory : getTypicalMedHists()) {
            ab.addMedHist(medicalHistory);
        }

        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }

        for (Doctor doctor : getTypicalDoctors()) {
            ab.addDoctor(doctor);
        }

        return ab;
    }

    public static List<MedicalHistory> getTypicalMedHists() {
        return new ArrayList<>(Arrays.asList(MED_HIST1, MED_HIST2, MED_HIST3, MED_HIST4, MED_HIST5));
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(ALVINA, BOND, CHARLIE, DAM, ELSIE, FLORA, GAN));
    }
}
