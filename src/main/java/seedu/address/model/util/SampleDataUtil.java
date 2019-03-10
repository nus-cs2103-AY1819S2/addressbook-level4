package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EquipmentManager;
import seedu.address.model.ReadOnlyEquipmentManager;
import seedu.address.model.equipment.Address;
import seedu.address.model.equipment.Email;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.Name;
import seedu.address.model.equipment.Phone;
import seedu.address.model.equipment.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EquipmentManager} with sample data.
 */
public class SampleDataUtil {
    public static Equipment[] getSamplePersons() {
        return new Equipment[] {
            new Equipment(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new SerialNumber("A008866X"),
                getTagSet("west")),
            new Equipment(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new SerialNumber("A008877X"),
                getTagSet("ongoing", "west")),
            new Equipment(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new SerialNumber("A008888X"),
                getTagSet("east")),
            new Equipment(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new SerialNumber("A008899X"),
                getTagSet("east")),
            new Equipment(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new SerialNumber("A008800X"),
                getTagSet("urgent")),
            new Equipment(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new SerialNumber("A008822X"),
                getTagSet("ongoing"))
        };
    }

    public static ReadOnlyEquipmentManager getSampleAddressBook() {
        EquipmentManager sampleAb = new EquipmentManager();
        for (Equipment sampleEquipment : getSamplePersons()) {
            sampleAb.addPerson(sampleEquipment);

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
