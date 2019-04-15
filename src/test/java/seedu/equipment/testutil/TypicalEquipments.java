package seedu.equipment.testutil;

import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.equipment.Equipment;

/**
 * A utility class containing a list of {@code Equipment} objects to be used in tests.
 */
public class TypicalEquipments {
    public static final Equipment ANCHORVALECC = new EquipmentBuilder().withName("Anchorvale CC")
            .withAddress("59 Anchorvale Rd, Singapore 544965").withDate("22-04-2019")
            .withPhone("64894959").withSerialNumber("X14D669807").withTags("west").build();
    public static final Equipment HWIYOHCC = new EquipmentBuilder().withName("Hwi Yoh CC")
            .withAddress("535 Serangoon North Ave 4, #01-179, Singapore 550535")
            .withDate("5-05-2019").withPhone("64840338").withSerialNumber("X10E453103")
            .withTags("urgent", "west").build();
    public static final Equipment TECKGHEECC = new EquipmentBuilder().withName("Teck Ghee CC").withPhone("64567123")
            .withDate("8-08-2019").withAddress("861 Ang Mo Kio Ave 10, Singapore 569734")
            .withSerialNumber("X14F682834").build();
    public static final Equipment AYERRAJAHCC = new EquipmentBuilder().withName("Ayer Rajah CC").withPhone("65609983")
            .withDate("20-12-2019").withAddress("150 Pandan Gardens, Singapore 609335")
            .withSerialNumber("X10E453112").withTags("west").build();
    public static final Equipment BUKITGCC = new EquipmentBuilder().withName("Bukit Gombak CC").withPhone("65150073")
            .withDate("27-06-2019").withAddress("386 Bukit Batok West Ave 5, Singapore 650386")
            .withSerialNumber("X14F682858").build();
    public static final Equipment CHENGSANCC = new EquipmentBuilder().withName("Cheng San CC").withPhone("64588222")
            .withDate("27-06-2019").withAddress("6 Ang Mo Kio Street 53, Singapore 569205")
            .withSerialNumber("X14F681766").build();
    public static final Equipment JURONGREENCC = new EquipmentBuilder().withName("Jurong Green CC")
            .withPhone("65671374").withDate("21-05-2019").withAddress("6 Jurong West Ave 1, Singapore 649520")
            .withSerialNumber("X10E453250").build();

    // Manually added
    public static final Equipment HOON = new EquipmentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withDate("19-05-2019").withAddress("little india").withSerialNumber("A008834L").build();
    public static final Equipment IDA = new EquipmentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withDate("10-05-2019").withAddress("chicago ave").withSerialNumber("A008865L").build();

    // Manually added - Equipment's details found in {@code CommandTestUtil}
    public static final Equipment AMY = new EquipmentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withDate(VALID_DATE_AMY).withAddress(VALID_ADDRESS_AMY).withSerialNumber(VALID_SERIAL_NUMBER_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Equipment BOB = new EquipmentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withDate(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB).withSerialNumber(VALID_SERIAL_NUMBER_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_CC = "CC"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_HWI = "Hwi"; // A keyword that matches Bukit

    private TypicalEquipments() {} // prevents instantiation

    /**
     * Returns an {@code EquipmentManager} with all the typical equipment.
     */
    public static EquipmentManager getTypicalEquipmentManager() {
        EquipmentManager em = new EquipmentManager();
        for (Equipment equipment : getTypicalEquipment()) {
            em.addPerson(equipment);
        }
        return em;
    }

    public static List<Equipment> getTypicalEquipment() {
        return new ArrayList<>(Arrays.asList(ANCHORVALECC, HWIYOHCC, TECKGHEECC, AYERRAJAHCC, BUKITGCC, CHENGSANCC,
                JURONGREENCC));
    }
}
