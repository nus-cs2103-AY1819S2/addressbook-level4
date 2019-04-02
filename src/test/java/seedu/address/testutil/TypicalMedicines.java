package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BATCHNUMBER_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FEVER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Inventory;
import seedu.address.model.medicine.Medicine;

/**
 * A utility class containing a list of {@code Medicine} objects to be used in tests.
 */
public class TypicalMedicines {

    public static final Medicine PARACETAMOL = new MedicineBuilder().withName("Paracetamol")
            .withCompany("GlaxoSmithKline").withExpiry("31/12/2019").withQuantity("312")
            .withTags("fever").withBatches("0042121", "31/12/2019", "312").build();
    public static final Medicine IBUPROFEN = new MedicineBuilder().withName("Ibuprofen")
            .withCompany("Novartis").withExpiry("21/09/2019").withQuantity("432")
            .withTags("inflammation", "fever").withBatches("HH073101", "21/09/2019", "432").build();
    public static final Medicine ACETAMINOPHEN = new MedicineBuilder().withName("Acetaminophen").withQuantity("0")
            .withExpiry("-").withCompany("Pfizer").build();
    public static final Medicine LEVOTHYROXINE = new MedicineBuilder().withName("Levothyroxine Sodium")
            .withQuantity("533").withExpiry("13/08/2019").withCompany("3M Pharmaceuticals").withTags("fever")
            .withBatches("GKP1684", "13/08/2019", "233", "GKP1685", "15/08/2019", "300").build();
    public static final Medicine LISINOPRIL = new MedicineBuilder().withName("Lisinopril").withQuantity("94")
            .withExpiry("06/07/2019").withCompany("Takeda Pharmaceutical Co.")
            .withBatches("307002", "06/07/2019", "94").build();
    public static final Medicine PREDNISONE = new MedicineBuilder().withName("Prednisone")
            .withQuantity("427").withExpiry("19/11/2019").withCompany("Gilead Sciences")
            .withBatches("A030F21", "19/11/2019", "427").build();
    public static final Medicine LIPITOR = new MedicineBuilder().withName("Lipitor").withQuantity("82")
            .withExpiry("25/01/2020").withCompany("Johnson & Johnson")
            .withBatches("NDC 0777-3105-02", "25/01/2020", "82").build();

    // Manually added
    public static final Medicine NAPROXEN = new MedicineBuilder().withName("Naproxen Sodium").withQuantity("424")
            .withExpiry("29/07/2019").withCompany("Boehringer-Ingelheim")
            .withBatches("HH 5100004", "29/07/2019", "224", "HH 5100011", "11/08/2019", "200").build();
    public static final Medicine HYDROCHLOROTHIAZIDE = new MedicineBuilder().withName("Hydrochlorothiazide")
            .withQuantity("0").withExpiry("-").withCompany("NovaBay Pharmaceuticals").build();

    // Manually added - Medicine's details found in {@code CommandTestUtil}
    public static final Medicine AMOXICILLIN = new MedicineBuilder().withName(VALID_NAME_AMOXICILLIN)
            .withQuantity(VALID_QUANTITY_AMOXICILLIN).withExpiry(VALID_EXPIRY_AMOXICILLIN)
            .withCompany(VALID_COMPANY_AMOXICILLIN).withTags(VALID_TAG_FEVER)
            .withBatches(VALID_BATCHNUMBER_AMOXICILLIN, VALID_EXPIRY_AMOXICILLIN, VALID_QUANTITY_AMOXICILLIN).build();
    public static final Medicine GABAPENTIN = new MedicineBuilder().withName(VALID_NAME_GABAPENTIN)
            .withQuantity(VALID_QUANTITY_GABAPENTIN).withExpiry(VALID_EXPIRY_GABAPENTIN)
            .withCompany(VALID_COMPANY_GABAPENTIN).withTags(VALID_TAG_PAINKILLER, VALID_TAG_FEVER).build();

    public static final String KEYWORD_MATCHING_SODIUM = "Sodium"; // A keyword that matches SODIUM

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
        return new ArrayList<>(Arrays.asList(PARACETAMOL, IBUPROFEN, ACETAMINOPHEN, LEVOTHYROXINE, LISINOPRIL,
                PREDNISONE, LIPITOR));
    }
}
