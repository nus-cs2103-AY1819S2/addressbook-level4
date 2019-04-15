package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEGREE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEGREE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GPA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GPA_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withEducation("NUS")
            .withGpa("3.4")
            .withDegree("Bachelors")
            .withSkills("C++", "Debugging")
            .withPositions("Software Engineer").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("98765432")
            .withEmail("johnd@example.com")
            .withEducation("NTU")
            .withGpa("3.1")
            .withDegree("Bachelors")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withSkills("C++", "Debugging", "JavaScript")
            .withPositions("Website Developer").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEducation("NTU")
            .withGpa("3.2")
            .withDegree("Bachelors")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withSkills("Python", "Photography", "Cyber Crime", "Finance", "JavaScript")
            .withPositions("Systems Analyst", "UI Developer").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEducation("NUS")
            .withGpa("2.6")
            .withDegree("Bachelors")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withSkills("C++", "Debugging", "Linux")
            .withEndorsements("Bill Gates", "Mark Cuban", "Elon Musk", "Barak Obama")
            .withPositions("UI Developer").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEducation("Harvard")
            .withGpa("1.3")
            .withDegree("Bachelors")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withSkills("C++", "C#", "Java", "excel")
            .withEndorsements("Elon Musk", "Mark Cuban").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEducation("HARVARD")
            .withGpa("4.0")
            .withDegree("Bachelors")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withSkills("C++", "Java", "MATlab")
            .withPositions("Civil Servant", "Project Manager")
            .withEndorsements("Stan Lee", "Barak Obama").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442")
            .withEducation("NUS")
            .withGpa("0.0")
            .withDegree("Bachelors")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withSkills("Linux", "PHP")
            .withPositions("Data Analyst")
            .withEndorsements("Mark Zuckerberg").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEducation("NTU")
            .withGpa("1.6")
            .withDegree("Bachelors")
            .withEmail("stefan@example.com")
            .withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withEducation("Nus")
            .withGpa("2.8")
            .withDegree("Bachelors")
            .withEmail("hans@example.com")
            .withAddress("chicago ave").build();

    // Persons without tags, used for some tests as a temporary work-around
    public static final Person JOHN = new PersonBuilder().withName("John Peer")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("john@example.com")
            .withPhone("94351253")
            .withEducation("NUS")
            .withDegree("Bachelors")
            .withGpa("3.4").build();
    public static final Person KATIE = new PersonBuilder().withName("Katie Ming")
            .withPhone("98765432")
            .withEmail("katie@example.com")
            .withEducation("NTU")
            .withGpa("3.1")
            .withDegree("Bachelors")
            .withAddress("311, Clementi Ave 2, #02-25").build();
    public static final Person LARS = new PersonBuilder().withName("Lars Schaaf")
            .withPhone("95352563")
            .withEducation("NTU")
            .withGpa("3.2")
            .withDegree("Bachelors")
            .withEmail("heinz@example.com")
            .withAddress("wall street").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withEducation(VALID_EDUCATION_AMY).withGpa(VALID_GPA_AMY)
            .withDegree(VALID_DEGREE_AMY).withAddress(VALID_ADDRESS_AMY).withSkills(VALID_SKILL_PYTHON).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withEducation(VALID_EDUCATION_BOB).withGpa(VALID_GPA_BOB)
            .withDegree(VALID_DEGREE_BOB).withAddress(VALID_ADDRESS_BOB).withSkills(VALID_SKILL_JAVA).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

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

    public static List<Person> getTypicalPersonsNoTags() {
        return new ArrayList<>(Arrays.asList(JOHN, KATIE, LARS));
    }
}
