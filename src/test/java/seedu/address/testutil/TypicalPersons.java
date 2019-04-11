package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTALPRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SELLINGPRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HDB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MRT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
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
    public static final Seller ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822244")
            .withEmail("werner@example.com").withAddress("michigan ave").withRemark("Elle is  a seller")
            .withSellingPrice("890000").withTags("mrt").buildSeller();
    public static final Landlord FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824277")
            .withRemark("Fiona is a landlord").withRentalPrice("3500")
            .withEmail("lydia@example.com").withAddress("little tokyo").withTags("mrt").buildLandlord();
    public static final Buyer GEORGE = new PersonBuilder().withName("George Best").withPhone("94824422")
            .withEmail("anna@example.com").withRemark("George is a buyer").buildBuyer();

    // Manually added
    public static final Buyer HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824244")
            .withEmail("stefan@example.com").withRemark("Hoon is a manually added buyer").buildBuyer();
    public static final Seller IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821311")
            .withEmail("hans@example.com").withRemark("Ida is a manually added Seller")
            .withAddress("Block 623 Clementi Ave 6 #12-02").withSellingPrice("760000").withTags("mrt").buildSeller();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Seller AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withRemark(VALID_REMARK_AMY).withAddress(VALID_ADDRESS_AMY)
            .withSellingPrice(VALID_SELLINGPRICE_AMY).withTags(VALID_TAG_HDB).buildSeller();
    public static final Landlord BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withRemark(VALID_REMARK_BOB).withAddress(VALID_ADDRESS_BOB)
            .withRentalPrice(VALID_RENTALPRICE_BOB).withTags(VALID_TAG_MRT, VALID_TAG_HDB).buildLandlord();

    // Archived persons
    public static final Buyer CATHY = new PersonBuilder().withName("Cathy Black").withPhone("98765432")
            .withEmail("cathy@example.com").withRemark("Cathy is an archived buyer").buildBuyer();
    public static final Seller DIANA = new PersonBuilder().withName("Diana Prince").withPhone("87654321")
            .withEmail("diana@example.com").withRemark("Diana is an archived seller").withAddress("baker street")
            .withSellingPrice("350000").withTags("mrt").buildSeller();
    public static final Landlord ENID = new PersonBuilder().withName("Enid Blyton").withPhone("86543210")
            .withEmail("enid@example.com").withRemark("Enid is an archived landlord").withAddress("writers street")
            .withRentalPrice("2300").withTags("mrt").buildLandlord();

    // Pinned persons
    public static final Buyer PHILIP = new PersonBuilder().withName("Philip Fu").withPhone("83071234")
            .withEmail("philip@example.com").withRemark("Philip is a pinned buyer").buildBuyer();
    public static final Seller IRWIN = new PersonBuilder().withName("Irwin King").withPhone("83072345")
            .withEmail("irwin@example.com").withRemark("Irwin is a pinned seller").withAddress("clementi")
            .withSellingPrice("350000").withTags("mrt").buildSeller();
    public static final Landlord JIMMY = new PersonBuilder().withName("Jimmy Lee").withPhone("83073456")
            .withEmail("jimmy@example.com").withRemark("Jimmy is a pinned landlord").withAddress("clementi")
            .withRentalPrice("2500").withTags("mrt").buildLandlord();

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
     * Returns an {@code AddressBook} with all the typical archived persons.
     */
    public static AddressBook getTypicalArchiveBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalArchivedPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code PinBook} with all the typical pinned persons.
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
