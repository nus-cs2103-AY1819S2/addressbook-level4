package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Email;
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
            new Medicine(new Name("Alex Yeoh"), new Quantity("807"), new Email("alexyeoh@example.com"),
                new Company("Otsuka Pharmaceutical Co."),
                getTagSet("friends")),
            new Medicine(new Name("Bernice Yu"), new Quantity("58"), new Email("berniceyu@example.com"),
                new Company("Piramal Healthcare"),
                getTagSet("colleagues", "friends")),
            new Medicine(new Name("Charlotte Oliveiro"), new Quantity("283"), new Email("charlotte@example.com"),
                new Company("Renovo PLC"),
                getTagSet("neighbours")),
            new Medicine(new Name("David Li"), new Quantity("312"), new Email("lidavid@example.com"),
                new Company("Vion Pharmaceuticals, Inc."),
                getTagSet("family")),
            new Medicine(new Name("Irfan Ibrahim"), new Quantity("4921"), new Email("irfan@example.com"),
                new Company("Eli Lilly and Company"),
                getTagSet("classmates")),
            new Medicine(new Name("Roy Balakrishnan"), new Quantity("296"), new Email("royb@example.com"),
                new Company("Mitsubishi Tanabe Pharma"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyInventory getSampleInventory() {
        Inventory sampleAb = new Inventory();
        for (Medicine sampleMedicine : getSampleMedicines()) {
            sampleAb.addMedicine(sampleMedicine);
        }
        return sampleAb;
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
