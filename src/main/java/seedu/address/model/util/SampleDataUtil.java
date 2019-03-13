package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Medicine[] getSampleMedicines() {
        return new Medicine[] {
            new Medicine(new Name("Aspirin"), new Quantity("807"), new Expiry("29/11/2019"),
                    new Company("Otsuka Pharmaceutical Co."),
                    getTagSet("fever"), getBatchSet("CD485", "29/11/2019", "807")),
            new Medicine(new Name("Bendroflumethiazide"), new Quantity("58"), new Expiry("03/10/2019"),
                    new Company("Piramal Healthcare"),
                    getTagSet("hypertension", "tablet"), getBatchSet("1001194", "03/10/2019", "58")),
            new Medicine(new Name("Co-codamol"), new Quantity("283"), new Expiry("28/02/2020"),
                    new Company("Renovo PLC"), getTagSet("painkiller"),
                    getBatchSet("HK-85412", "28/02/2020", "283")),
            new Medicine(new Name("Simvastatin"), new Quantity("312"), new Expiry("16/09/2019"),
                    new Company("Vion Pharmaceuticals, Inc."), getTagSet("lipid"),
                    getBatchSet("B5003B504", "16/09/2019", "312")),
            new Medicine(new Name("Omeprazole"), new Quantity("921"), new Expiry("02/07/2019"),
                    new Company("Eli Lilly and Company"), getTagSet("gastroesophageal"),
                    getBatchSet("A4415115698", "02/07/2019", "421", "A4415118456", "22/09/2019", "500")),
            new Medicine(new Name("Atorvastatin"), new Quantity("296"), new Expiry("31/12/2019"),
                    new Company("Mitsubishi Tanabe Pharma"),
                    getTagSet("cardiovascular"), getBatchSet("BAL101025", "31/12/2019", "296"))
        };
    }

    public static ReadOnlyInventory getSampleInventory() {
        Inventory sampleInv = new Inventory();
        for (Medicine sampleMedicine : getSampleMedicines()) {
            sampleInv.addMedicine(sampleMedicine);
        }
        return sampleInv;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a batch map containing the list of strings given.
     */
    public static Map<BatchNumber, Batch> getBatchSet(String... strings) {
        Map<BatchNumber, Batch> map = new HashMap<>();
        for (int i = 0; i < strings.length; i += 3) {
            BatchNumber batchNumber = new BatchNumber(strings[i]);
            map.put(batchNumber, new Batch(batchNumber, new Expiry(strings[i + 1]), new Quantity(strings[i + 2])));
        }
        return map;
    }
}
