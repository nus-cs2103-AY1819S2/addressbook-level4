package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Medicine[] getSampleMedicines() {
        return new Medicine[] {
            new Medicine(new Name("Alex Yeoh"), new Quantity("807"), new Expiry("29/11/2019"),
                new Company("Otsuka Pharmaceutical Co."),
                getTagSet("friends")),
            new Medicine(new Name("Bernice Yu"), new Quantity("58"), new Expiry("03/10/2019"),
                new Company("Piramal Healthcare"),
                getTagSet("colleagues", "friends")),
            new Medicine(new Name("Charlotte Oliveiro"), new Quantity("283"), new Expiry("28/02/2020"),
                new Company("Renovo PLC"),
                getTagSet("neighbours")),
            new Medicine(new Name("David Li"), new Quantity("312"), new Expiry("16/09/2019"),
                new Company("Vion Pharmaceuticals, Inc."),
                getTagSet("family")),
            new Medicine(new Name("Irfan Ibrahim"), new Quantity("921"), new Expiry("02/07/2019"),
                new Company("Eli Lilly and Company"),
                getTagSet("classmates")),
            new Medicine(new Name("Roy Balakrishnan"), new Quantity("296"), new Expiry("31/12/2019"),
                new Company("Mitsubishi Tanabe Pharma"),
                getTagSet("colleagues"))
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

}
