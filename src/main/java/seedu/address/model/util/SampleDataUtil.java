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
import seedu.address.model.medicine.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Medicine[] getSampleMedicines() {
        return new Medicine[] {
            new Medicine(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Company("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Medicine(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Company("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Medicine(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Company("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Medicine(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Company("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Medicine(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Company("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Medicine(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Company("Blk 45 Aljunied Street 85, #11-31"),
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
