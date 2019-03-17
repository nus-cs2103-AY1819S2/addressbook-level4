package seedu.hms.testutil;

import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.customer.Customer;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    public static final Customer ALICE = new seedu.hms.testutil.CustomerBuilder().withName("Alice Pauline")
        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253").withIdNum("12312")
        .withTags("friends").build();
    public static final Customer BENSON = new seedu.hms.testutil.CustomerBuilder().withName("Benson Meier")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withIdNum("12335")
        .withTags("owesMoney", "friends").build();
    public static final Customer CARL = new seedu.hms.testutil.CustomerBuilder().withName("Carl Kurz").withPhone("95352563")
        .withEmail("heinz@example.com").withAddress("wall street").withIdNum("104535")
        .build();
    public static final Customer DANIEL = new seedu.hms.testutil.CustomerBuilder().withName("Daniel Meier").withPhone("87652533")
        .withEmail("cornelia@example.com").withAddress("10th street").withIdNum("12005")
        .withTags("friends").build();
    public static final Customer ELLE = new seedu.hms.testutil.CustomerBuilder().withName("Elle Meyer").withPhone("9482224")
        .withEmail("werner@example.com").withAddress("michegan ave").withIdNum("123300")
        .build();
    public static final Customer FIONA = new seedu.hms.testutil.CustomerBuilder().withName("Fiona Kunz").withPhone("9482427")
        .withEmail("lydia@example.com").withAddress("little tokyo").withIdNum("10095")
        .build();
    public static final Customer GEORGE = new seedu.hms.testutil.CustomerBuilder().withName("George Best").withPhone("9482442")
        .withEmail("anna@example.com").withAddress("4th street").withIdNum("1233999")
        .build();
    public static final Customer VIP_CUSTOMER = new seedu.hms.testutil.CustomerBuilder().withName("I am VIP").withPhone("94824425")
        .withEmail("vip@example.com").withAddress("VIP street").withIdNum("2536363")
        .withTags("VIP").build();
    public static final Customer VIP_CUSTOMER2 = new seedu.hms.testutil.CustomerBuilder().withName("I am also VIP").withPhone("34824425")
        .withEmail("vip2@example.com").withAddress("VVIP street").withIdNum("5352525")
        .withTags("vip").build();

    // Manually added
    public static final Customer HOON = new seedu.hms.testutil.CustomerBuilder().withName("Hoon Meier").withPhone("8482424")
        .withEmail("stefan@example.com").withIdNum("456363").withAddress("little india").build();
    public static final Customer IDA = new seedu.hms.testutil.CustomerBuilder().withName("Ida Mueller").withPhone("8482131")
        .withEmail("hans@example.com").withIdNum("13342").withAddress("chicago ave").build();


    // Manually added - Customer's details found in {@code CommandTestUtil}
    public static final Customer AMY = new seedu.hms.testutil.CustomerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
        .withEmail(VALID_EMAIL_AMY).withIdNum(VALID_ID_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
        .build();
    public static final Customer BOB = new seedu.hms.testutil.CustomerBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
        .withEmail(VALID_EMAIL_BOB).withIdNum(VALID_ID_BOB).withAddress(VALID_ADDRESS_BOB)
        .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
        .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCustomers() {
    } // prevents instantiation

    /**
     * Returns an {@code HotelManagementSystem} with all the typical customers.
     */
    public static HotelManagementSystem getTypicalHotelManagementSystem() {
        HotelManagementSystem ab = new HotelManagementSystem();
        for (Customer customer : getTypicalCustomers()) {
            ab.addCustomer(customer);
        }
        return ab;
    }

    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
