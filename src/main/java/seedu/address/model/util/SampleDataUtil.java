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
            new Medicine(new Name("Aspirin"), new Company("Otsuka Pharmaceutical Co."), new Quantity("27"),
                    new Expiry("29/11/2019"), getTagSet("fever"), getBatchSet("CD485", "27", "29/11/2019")),
            new Medicine(new Name("Atorvastatin"), new Company("Mitsubishi Tanabe Pharma"), new Quantity("39"),
                    new Expiry("31/12/2019"), getTagSet("cardiovascular"),
                    getBatchSet("BAL101025", "39", "31/12/2019")),
            new Medicine(new Name("Bendroflumethiazide"), new Company("Piramal Healthcare"), new Quantity("52"),
                    new Expiry("03/10/2019"), getTagSet("hypertension", "tablet"),
                    getBatchSet("1001194", "52", "03/10/2019")),
            new Medicine(new Name("Co-codamol"), new Company("Renovo PLC"), new Quantity("28"),
                    new Expiry("28/02/2020"), getTagSet("painkiller"), getBatchSet("HK-85412", "28", "28/02/2020")),
            new Medicine(new Name("Omeprazole"), new Company("Eli Lilly and Company"), new Quantity("121"),
                    new Expiry("02/07/2019"), getTagSet("gastroesophageal"),
                    getBatchSet("A4415115698", "21", "02/07/2019", "A4415118456", "100", "22/09/2019")),
            new Medicine(new Name("Simvastatin"), new Company("Vion Pharmaceuticals, Inc."), new Quantity("41"),
                    new Expiry("16/09/2019"), getTagSet("lipid"), getBatchSet("B5003B504", "41", "16/09/2019"))
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
            map.put(batchNumber, new Batch(batchNumber, new Quantity(strings[i + 1]), new Expiry(strings[i + 2])));
        }
        return map;
    }
}
