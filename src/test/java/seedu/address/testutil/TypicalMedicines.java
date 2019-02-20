package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Inventory;
import seedu.address.model.medicine.Medicine;

/**
 * A utility class containing a list of {@code Medicine} objects to be used in tests.
 */
public class TypicalMedicines {

    public static final Medicine ALICE = new MedicineBuilder().withName("Alice Pauline")
            .withCompany("GlaxoSmithKline").withExpiry("31/12/2019")
            .withQuantity("312")
            .withTags("friends").build();
    public static final Medicine BENSON = new MedicineBuilder().withName("Benson Meier")
            .withCompany("Novartis")
            .withExpiry("21/09/2019").withQuantity("432")
            .withTags("owesMoney", "friends").build();
    public static final Medicine CARL = new MedicineBuilder().withName("Carl Kurz").withQuantity("563")
            .withExpiry("04/02/2020").withCompany("Pfizer").build();
    public static final Medicine DANIEL = new MedicineBuilder().withName("Daniel Meier").withQuantity("533")
            .withExpiry("13/08/2019").withCompany("3M Pharmaceuticals").withTags("friends").build();
    public static final Medicine ELLE = new MedicineBuilder().withName("Elle Meyer").withQuantity("94")
            .withExpiry("06/07/2019").withCompany("Takeda Pharmaceutical Co.").build();
    public static final Medicine FIONA = new MedicineBuilder().withName("Fiona Kunz").withQuantity("427")
            .withExpiry("19/11/2019").withCompany("Gilead Sciences").build();
    public static final Medicine GEORGE = new MedicineBuilder().withName("George Best").withQuantity("82")
            .withExpiry("25/01/2020").withCompany("Johnson & Johnson").build();

    // Manually added
    public static final Medicine HOON = new MedicineBuilder().withName("Hoon Meier").withQuantity("424")
            .withExpiry("29/07/2019").withCompany("Boehringer-Ingelheim").build();
    public static final Medicine IDA = new MedicineBuilder().withName("Ida Mueller").withQuantity("31")
            .withExpiry("12/08/2019").withCompany("NovaBay Pharmaceuticals").build();

    // Manually added - Medicine's details found in {@code CommandTestUtil}
    public static final Medicine AMY = new MedicineBuilder().withName(VALID_NAME_AMY).withQuantity(VALID_QUANTITY_AMY)
            .withExpiry(VALID_EXPIRY_AMY).withCompany(VALID_COMPANY_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Medicine BOB = new MedicineBuilder().withName(VALID_NAME_BOB).withQuantity(VALID_QUANTITY_BOB)
            .withExpiry(VALID_EXPIRY_BOB).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMedicines() {} // prevents instantiation

    /**
     * Returns an {@code Inventory} with all the typical medicines.
     */
    public static Inventory getTypicalInventory() {
        Inventory inv = new Inventory();
        for (Medicine medicine : getTypicalMedicines()) {
            inv.addMedicine(medicine);
        }
        return inv;
    }

    public static List<Medicine> getTypicalMedicines() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
