package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
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
            .withCompany("GlaxoSmithKline").withEmail("alice@example.com")
            .withQuantity("312")
            .withTags("friends").build();
    public static final Medicine BENSON = new MedicineBuilder().withName("Benson Meier")
            .withCompany("Novartis")
            .withEmail("johnd@example.com").withQuantity("432")
            .withTags("owesMoney", "friends").build();
    public static final Medicine CARL = new MedicineBuilder().withName("Carl Kurz").withQuantity("563")
            .withEmail("heinz@example.com").withCompany("Pfizer").build();
    public static final Medicine DANIEL = new MedicineBuilder().withName("Daniel Meier").withQuantity("533")
            .withEmail("cornelia@example.com").withCompany("3M Pharmaceuticals").withTags("friends").build();
    public static final Medicine ELLE = new MedicineBuilder().withName("Elle Meyer").withQuantity("94")
            .withEmail("werner@example.com").withCompany("takeda pharmaceutical co.").build();
    public static final Medicine FIONA = new MedicineBuilder().withName("Fiona Kunz").withQuantity("427")
            .withEmail("lydia@example.com").withCompany("Gilead Sciences").build();
    public static final Medicine GEORGE = new MedicineBuilder().withName("George Best").withQuantity("82")
            .withEmail("anna@example.com").withCompany("Johnson & Johnson").build();

    // Manually added
    public static final Medicine HOON = new MedicineBuilder().withName("Hoon Meier").withQuantity("424")
            .withEmail("stefan@example.com").withCompany("Boehringer-Ingelheim").build();
    public static final Medicine IDA = new MedicineBuilder().withName("Ida Mueller").withQuantity("31")
            .withEmail("hans@example.com").withCompany("NovaBay Pharmaceuticals").build();

    // Manually added - Medicine's details found in {@code CommandTestUtil}
    public static final Medicine AMY = new MedicineBuilder().withName(VALID_NAME_AMY).withQuantity(VALID_QUANTITY_AMY)
            .withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Medicine BOB = new MedicineBuilder().withName(VALID_NAME_BOB).withQuantity(VALID_QUANTITY_BOB)
            .withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
