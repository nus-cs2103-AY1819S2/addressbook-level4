package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder.NextOfKinBuilder;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withNric("S1234567A")
            .withDob("11-12-1990").withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withSex("F").withDrugAllergy("NIL").withDescription("Just another patient.")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Joshua Pauline").withKinRelation("Father")
                .withKinAddress("123, Jurong West Ave 6, #08-111").withKinPhone("96969696").build())
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withNric("S1234568A")
            .withDob("12-12-1990").withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withSex("M").withDrugAllergy("Panadol").withDescription("Just yet another patient.")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Jane Meier").withKinRelation("Sister")
            .withKinAddress("311, Clementi Ave 2, #02-25").withKinPhone("98712345").build())
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withNric("S1234569A")
            .withDob("11-12-1991").withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
            .withSex("F").withDrugAllergy("asprin").withDescription("Needs special anesthetics")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Charlie Kurz").withKinRelation("Brother")
            .withKinAddress("floor square").withKinPhone("85214369").build())
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withNric("S2234569A")
            .withDob("21-12-1991").withPhone("87652533").withEmail("cornelia@example.com").withAddress("10th street")
            .withSex("M").withDrugAllergy("Penicilin").withDescription("Requires different antibiotics")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Denice Meier").withKinRelation("Mother")
            .withKinAddress("11th Strret").withKinPhone("84456622").build())
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withNric("S2334569A")
            .withDob("11-06-1991").withPhone("9482224").withEmail("werner@example.com").withAddress("michegan ave")
            .withSex("F").withDrugAllergy("NIL").withDescription("Just another patient.")
            .withNextOfKin(new NextOfKinBuilder().withKinName("John Meyer").withKinRelation("Grandfather")
            .withKinAddress("Ohio Street").withKinPhone("87771222").build())
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withNric("S3234569A")
            .withDob("01-12-1991").withPhone("9482427").withEmail("lydia@example.com").withAddress("little tokyo")
            .withSex("F").withDrugAllergy("NIL").withDescription("Buck teeth may need surgery in future.")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Karly Kunz").withKinRelation("Grandmother")
            .withKinAddress("bigger tokyo").withKinPhone("821355555").build())
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withNric("S5234569A")
            .withDob("11-01-1991").withPhone("9482442").withEmail("anna@example.com").withAddress("4th street")
            .withSex("M").withDrugAllergy("naproxen").withDescription("Sensitive teeth, should use Sensodyn.")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Nimrod Worst").withKinRelation("Stepbrother")
            .withKinAddress("1st Avenue").withKinPhone("89894215").build())
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withNric("S9234569A")
            .withDob("11-01-1950").withPhone("8482424").withEmail("stefan@example.com").withAddress("little india")
            .withSex("M").withDrugAllergy("NIL").withDescription("Just another patient.")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Daniel Meier").withKinRelation("Brother")
            .withKinAddress("10 street").withKinPhone("87652533").build())
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withNric("S9334569A")
            .withDob("11-01-1980").withPhone("8482131").withEmail("hans@example.com").withAddress("chicago ave")
            .withSex("F").withDrugAllergy("NIL").withDescription("Just another patient.")
            .withNextOfKin(new NextOfKinBuilder().withKinName("Michael Mueller").withKinRelation("Father")
            .withKinAddress("chicago ave").withKinPhone("88885555").build())
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    // edited to specify patient attributes - for testing purposes
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withNric(VALID_NRIC_AMY).withDob(VALID_DOB_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSex("F").build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withNric(VALID_NRIC_BOB).withDob(VALID_DOB_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSex("M").build();

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
}
