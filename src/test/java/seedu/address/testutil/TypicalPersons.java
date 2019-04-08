package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEWSCORES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEWSCORES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBSAPPLY_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBSAPPLY_TRADER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KNOWNPROGLANG_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASTJOB_PROFESSSOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RACE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RACE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withRace("Others")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withNric("S9345678K")
            .withGender("Female").withPhone("94351253").withSchool("NUS").withMajor("CS").withGrade("4.54")
            .withKnownProgLangs("Python").withPastJobs("Professor").withJobsApply("Accountant")
            .withInterviewScores("10,8,5,10,1").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withRace("Chinese")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withNric("S9421423F")
            .withGender("Male").withPhone("98765432").withSchool("NTU").withMajor("CS").withGrade("4.00")
            .withKnownProgLangs("Python").withPastJobs("Lawyer", "SDE").withJobsApply("Data Analyst")
            .withInterviewScores("10,2,10,10,10").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withRace("Others")
            .withPhone("95352563").withEmail("heinz@example.com").withNric("S9123121F")
            .withGender("Male").withAddress("wall street").withGrade("4.12").withJobsApply("Data Analyst")
            .withInterviewScores("1,2,1,1,1").withKnownProgLangs("Python").withPastJobs("Professor")
            .withSchool("SMU").withMajor("CS").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withRace("Chinese")
            .withPhone("87652533").withEmail("cornelia@example.com").withNric("S9098983R")
            .withGender("Male").withAddress("10th street").withGrade("5.00").withJobsApply("Chief Financial Officer")
            .withInterviewScores("10,10,10,10,10").withSchool("SIM").withMajor("CS").withKnownProgLangs("Python")
            .withPastJobs("Professor").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withRace("Others")
            .withPhone("9482224").withEmail("werner@example.com").withNric("S9876523T")
            .withGender("Female").withAddress("michegan ave").withGrade("4.85")
            .withSchool("SUTD").withMajor("CS").withKnownProgLangs("Python")
            .withPastJobs("SDE").withJobsApply("Chief Executive Officer").withInterviewScores("10,10,10,10,10").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withRace("Malay")
            .withPhone("9482427").withEmail("lydia@example.com").withNric("S7951769H")
            .withGender("Female").withAddress("little tokyo").withGrade("5.00").withJobsApply("Chief Operating Officer")
            .withInterviewScores("9,9,9,10,10").withSchool("SIT").withMajor("CS")
            .withKnownProgLangs("Python").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withRace("Indian")
            .withPhone("9482442").withEmail("anna@example.com").withNric("S0101011Y")
            .withGender("Male").withAddress("4th street").withGrade("2.10").withJobsApply("Human Resource Manager")
            .withInterviewScores("1,3,4,10,4").withSchool("SUSS").withMajor("CS")
            .withKnownProgLangs("Python").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withRace("Chinese")
            .withPhone("8482424").withEmail("stefan@example.com").withAddress("little india").withNric("S3344556O")
            .withGender("Male").withGrade("4.10").withJobsApply("Security Manager")
            .withInterviewScores("2,3,4,10,10").withSchool("NUS").withMajor("CS").withKnownProgLangs("Python").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withRace("Others")
            .withNric("S4867206C").withGender("Male").withGrade("3.00").withJobsApply("Business Development Intern")
            .withInterviewScores("2,2,2,2,2").withPhone("8482131").withEmail("hans@example.com")
            .withAddress("chicago ave").withSchool("NTU").withMajor("CS").withKnownProgLangs("Python").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withRace(VALID_RACE_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).withSchool(VALID_SCHOOL_AMY).withPastJobs(VALID_PASTJOB_PROFESSSOR)
            .withKnownProgLangs(VALID_KNOWNPROGLANG_PYTHON).withMajor(VALID_MAJOR_AMY).withNric(VALID_NRIC_AMY)
            .withGender(VALID_GENDER_AMY).withGrade(VALID_GRADE_AMY).withInterviewScores(VALID_INTERVIEWSCORES_AMY)
            .withJobsApply(VALID_JOBSAPPLY_TRADER).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withRace(VALID_RACE_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withMajor(VALID_MAJOR_BOB).withSchool(VALID_SCHOOL_BOB).withPastJobs(VALID_PASTJOB_PROFESSSOR)
            .withKnownProgLangs(VALID_KNOWNPROGLANG_PYTHON).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withNric(VALID_NRIC_BOB).withGender(VALID_GENDER_BOB).withGrade(VALID_GRADE_BOB)
            .withInterviewScores(VALID_INTERVIEWSCORES_BOB).withJobsApply(VALID_JOBSAPPLY_ENGINEER).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
