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
            .withCompany("GlaxoSmithKline").withQuantity("312").withExpiry("31/12/2019")
            .withTags("fever").withBatches("0042121", "312", "31/12/2019").build();
    public static final Medicine IBUPROFEN = new MedicineBuilder().withName("Ibuprofen")
            .withCompany("Novartis").withQuantity("432").withExpiry("21/09/2019")
            .withTags("inflammation", "fever").withBatches("HH073101", "432", "21/09/2019").build();
    public static final Medicine ACETAMINOPHEN = new MedicineBuilder().withName("Acetaminophen").withCompany("Pfizer")
            .withQuantity("0").withExpiry("-").build();
    public static final Medicine LEVOTHYROXINE = new MedicineBuilder().withName("Levothyroxine Sodium")
            .withCompany("3M Pharmaceuticals").withQuantity("533").withExpiry("31/07/2019").withTags("thyroid")
            .withBatches("GKP1684", "233", "13/08/2019", "GKP1685", "300", "15/08/2019", "GKP1682", "5", "31/07/2019")
            .build();
    public static final Medicine LISINOPRIL = new MedicineBuilder().withName("Lisinopril")
            .withCompany("Takeda Pharmaceutical Co.").withQuantity("94").withExpiry("06/07/2019")
            .withBatches("307002", "94", "06/07/2019").build();
    public static final Medicine PREDNISONE = new MedicineBuilder().withName("Prednisone")
            .withCompany("Gilead Sciences").withQuantity("427").withExpiry("19/11/2019")
            .withBatches("A030F21", "427", "19/11/2019").build();
    public static final Medicine LIPITOR = new MedicineBuilder().withName("Lipitor").withCompany("Johnson & Johnson")
            .withQuantity("82").withExpiry("25/01/2020").withBatches("NDC 0777-3105-02", "82", "25/01/2020").build();

    // Manually added
    public static final Medicine NAPROXEN = new MedicineBuilder().withName("Naproxen Sodium")
            .withCompany("Boehringer-Ingelheim").withQuantity("424").withExpiry("29/07/2019")
            .withBatches("HH 5100004", "224", "29/07/2019", "HH 5100011", "200", "11/08/2019").build();
    public static final Medicine HYDROCHLOROTHIAZIDE = new MedicineBuilder().withName("Hydrochlorothiazide")
            .withCompany("NovaBay Pharmaceuticals").withQuantity("0").withExpiry("-").build();

    // Manually added - Medicine's details found in {@code CommandTestUtil}
    public static final Medicine AMOXICILLIN = new MedicineBuilder().withName(VALID_NAME_AMOXICILLIN)
            .withCompany(VALID_COMPANY_AMOXICILLIN).withQuantity(VALID_QUANTITY_AMOXICILLIN)
            .withExpiry(VALID_EXPIRY_AMOXICILLIN).withTags(VALID_TAG_FEVER)
            .withBatches(VALID_BATCHNUMBER_AMOXICILLIN, VALID_QUANTITY_AMOXICILLIN, VALID_EXPIRY_AMOXICILLIN).build();
    public static final Medicine GABAPENTIN = new MedicineBuilder().withName(VALID_NAME_GABAPENTIN)
            .withCompany(VALID_COMPANY_GABAPENTIN).withQuantity(VALID_QUANTITY_GABAPENTIN)
            .withExpiry(VALID_EXPIRY_GABAPENTIN).withTags(VALID_TAG_PAINKILLER, VALID_TAG_FEVER).build();

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
        return new ArrayList<>(Arrays.asList(ACETAMINOPHEN, IBUPROFEN, LEVOTHYROXINE, LIPITOR, LISINOPRIL, PARACETAMOL,
                PREDNISONE));
    }
}
