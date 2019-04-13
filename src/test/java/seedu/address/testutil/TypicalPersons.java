package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRICNUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRICNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_RUNNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SWIMMING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAROFSTUDY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAROFSTUDY_BOB;

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
            .withMatricNumber("A0123123B")
            .withPhone("94351253")
            .withEmail("alice@example.com")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withGender("Female")
            .withYearOfStudy("4")
            .withMajor("Business Analytics")
            .withTags("Running").build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withMatricNumber("A0222222B")
            .withPhone("98765432")
            .withEmail("benson@example.com")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withGender("Male")
            .withYearOfStudy("2")
            .withMajor("Information Systems")
            .withTags("Swimming", "Running").build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withMatricNumber("A0333333N")
            .withPhone("95352563")
            .withEmail("carl@example.com")
            .withAddress("wall street")
            .withGender("Male")
            .withYearOfStudy("1")
            .withMajor("Computer Engineering")
            .withTags("Swimming", "Running").build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withMatricNumber("A0444444A")
            .withPhone("87652533")
            .withEmail("daniel@example.com")
            .withAddress("10th street")
            .withGender("Male")
            .withYearOfStudy("4")
            .withMajor("Computer Science")
            .withTags("Hockey").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withMatricNumber("A0555555R")
            .withPhone("94822241")
            .withEmail("elle@example.com")
            .withAddress("michegan ave")
            .withGender("Female")
            .withYearOfStudy("1")
            .withMajor("Life Science")
            .withTags("Hockey").build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withMatricNumber("A0777777U")
            .withPhone("94812427")
            .withEmail("fiona@example.com")
            .withAddress("little tokyo")
            .withGender("Female")
            .withYearOfStudy("5")
            .withMajor("Medicine")
            .withTags("Floorball").build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withMatricNumber("A0888888H")
            .withPhone("94182442")
            .withEmail("george@example.com")
            .withAddress("4th street")
            .withGender("Male")
            .withYearOfStudy("3")
            .withMajor("Biomedical Engineering")
            .withTags("Hockey").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withMatricNumber("A0999999W")
            .withPhone("84824241")
            .withEmail("hoon@example.com")
            .withAddress("little india")
            .withGender("Female")
            .withYearOfStudy("1")
            .withMajor("Economics")
            .withTags("Soccer").build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withMatricNumber("A0321654J")
            .withPhone("84821311")
            .withEmail("ida@example.com")
            .withAddress("chicago ave")
            .withGender("Female")
            .withYearOfStudy("4")
            .withMajor("Business")
            .withTags("Basketball").build();


    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withMatricNumber(VALID_MATRICNUMBER_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withGender(VALID_GENDER_AMY).withYearOfStudy(VALID_YEAROFSTUDY_AMY)
            .withMajor(VALID_MAJOR_AMY).withTags(VALID_TAG_SWIMMING).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withMatricNumber(VALID_MATRICNUMBER_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).withGender(VALID_GENDER_BOB)
            .withYearOfStudy(VALID_YEAROFSTUDY_BOB).withMajor(VALID_MAJOR_BOB)
            .withTags(VALID_TAG_RUNNING, VALID_TAG_SWIMMING).build();

    public static final String KEYWORD_MATCHING_MEIER = "name Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBookWithPerson() {
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
