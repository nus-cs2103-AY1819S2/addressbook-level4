package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HDB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MRT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.ArchiveBook;
import seedu.address.model.PinBook;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Seller ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withRemark("Alice is a seller").withSellingPrice("540000")
            .withPhone("94351253").withTags("mrt").buildSeller();
    public static final Landlord BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
            .withRemark("Benson is a landlord").withRentalPrice("2300")
            .withTags("mrt", "3room").buildLandlord();
    public static final Buyer CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withRemark("Carl is a buyer").buildBuyer();
    public static final Tenant DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withRemark("Daniel is a Tenant").buildTenant();
    public static final Seller ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withRemark("Elle is  a seller without tags")
            .withSellingPrice("890000").buildSeller();
    public static final Landlord FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withRemark("Fiona is a landlord without tags").withRentalPrice("3500")
            .withEmail("lydia@example.com").withAddress("little tokyo").buildLandlord();
    public static final Buyer GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withRemark("George is a buyer").buildBuyer();

    // Manually added
    public static final Buyer HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withRemark("Hoon is a manually added buyer").buildBuyer();
    public static final Seller IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withRemark("Ida is a manually added Seller")
            .withAddress("Block 623 Clementi Ave 6 #12-02").withSellingPrice("760000").buildSeller();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_HDB).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_MRT, VALID_TAG_HDB)
            .build();

    // Archived persons
    public static final Person CATHY = new PersonBuilder().withName("Cathy Black").withPhone("98765432")
            .withEmail("cathy@example.com").withAddress("woodlands ave").build();
    public static final Person DIANA = new PersonBuilder().withName("Diana Prince").withPhone("87654321")
            .withEmail("diana@example.com").withAddress("baker street").build();
    public static final Person ENID = new PersonBuilder().withName("Enid Blyton").withPhone("86543210")
            .withEmail("enid@example.com").withAddress("writers street").build();

    // Pinned persons
    public static final Person PHILIP = new PersonBuilder().withName("Philip Fu").withPhone("83071234")
            .withEmail("philip@example.com").withAddress("clementi").build();
    public static final Person IRWIN = new PersonBuilder().withName("Irwin King").withPhone("83072345")
            .withEmail("irwin@example.com").withAddress("clementi").build();
    public static final Person JIMMY = new PersonBuilder().withName("Jimmy Lee").withPhone("83073456")
            .withEmail("jimmy@example.com").withAddress("clementi").build();

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

    /**
     * Returns an {@code ArchiveBook} with all the typical persons.
     */
    public static ArchiveBook getTypicalArchiveBook() {
        ArchiveBook ab = new ArchiveBook();
        for (Person person : getTypicalArchivedPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code PinBook} with all the typical persons.
     */
    public static PinBook getTypicalPinBook() {
        PinBook ab = new PinBook();
        for (Person person : getTypicalPinnedPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalArchivedPersons() {
        return new ArrayList<>(Arrays.asList(CATHY, DIANA, ENID));
    }

    public static List<Person> getTypicalPinnedPersons() {
        return new ArrayList<>(Arrays.asList(PHILIP, IRWIN, JIMMY));
    }
}
