package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EquipmentManager;
import seedu.address.model.equipment.Equipment;

/**
 * A utility class containing a list of {@code Equipment} objects to be used in tests.
 */
public class TypicalEquipments {

    public static final Equipment ACHORVALECC = new EquipmentBuilder().withName("Achorvale CC")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withSerialNumber("A008844L").withTags("west").build();
    public static final Equipment HWIYOHCC = new EquipmentBuilder().withName("Hwi Yoh CC")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withSerialNumber("A008855L")
            .withTags("urgent", "west").build();
    public static final Equipment TECKGHEECC = new EquipmentBuilder().withName("Teck Ghee CC").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withSerialNumber("A0223344X").build();
    public static final Equipment AYERRAJAHCC = new EquipmentBuilder().withName("Ayer Rajah CC").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withSerialNumber("A008800L")
            .withTags("west").build();
    public static final Equipment BUKITGCC = new EquipmentBuilder().withName("Bukit Gombak CC").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withSerialNumber("A008811L").build();
    public static final Equipment CHENGSANCC = new EquipmentBuilder().withName("Cheng San CC").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withSerialNumber("A008822L").build();
    public static final Equipment JURONGREENCC = new EquipmentBuilder().withName("Jurong Green CC").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withSerialNumber("A008821L").build();

    // Manually added
    public static final Equipment HOON = new EquipmentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withSerialNumber("A008834L").build();
    public static final Equipment IDA = new EquipmentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSerialNumber("A008865L").build();

    // Manually added - Equipment's details found in {@code CommandTestUtil}
    public static final Equipment AMY = new EquipmentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSerialNumber(VALID_SERIAL_NUMBER_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Equipment BOB = new EquipmentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSerialNumber(VALID_SERIAL_NUMBER_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_CC = "CC"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_HWI = "Hwi"; // A keyword that matches Bukit

    private TypicalEquipments() {} // prevents instantiation

    /**
     * Returns an {@code EquipmentManager} with all the typical persons.
     */
    public static EquipmentManager getTypicalAddressBook() {
        EquipmentManager ab = new EquipmentManager();
        for (Equipment equipment : getTypicalPersons()) {
            ab.addPerson(equipment);
        }
        return ab;
    }

    public static List<Equipment> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ACHORVALECC, HWIYOHCC, TECKGHEECC, AYERRAJAHCC, BUKITGCC, CHENGSANCC,
                JURONGREENCC));
    }
}
