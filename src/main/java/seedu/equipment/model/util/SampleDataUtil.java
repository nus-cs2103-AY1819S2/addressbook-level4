package seedu.equipment.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Date;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EquipmentManager} with sample data.
 */
public class SampleDataUtil {
    public static Equipment[] getSamplePersons() {
        return new Equipment[] {
            new Equipment(new Name("Kaki Bukit CC"), new Phone("64454223"), new Date("21 May 2019"),
                    new Address("670 Bedok North Street 3, Singapore 469627"), new SerialNumber("X14H702695"),
                    getTagSet("east")),
            new Equipment(new Name("Hougang CC"), new Phone("63662218"), new Date("22 June 2019"),
                    new Address("35 Hougang Ave 3, Singapore 538840"), new SerialNumber("X14H702664"),
                    getTagSet("ongoing", "east")),
            new Equipment(new Name("Woodlands Galaxy CC"), new Phone("63662218"), new Date("23 May 2019"),
                    new Address("31 Woodlands Ave 6, Singapore 738991"), new SerialNumber("X14H703071"),
                    getTagSet("east")),
            new Equipment(new Name("Woodlands Community Center Singapore"), new Phone("63689938"),
                    new Date("27 September 2019"), new Address("1 Woodlands Street 81, Singapore 738526"),
                    new SerialNumber("X14H702672"), getTagSet("east")),
            new Equipment(new Name("Tampines North CC"), new Phone("67832900"), new Date("18 July 2019"),
                    new Address("2 Tampines Street 41, Singapore 529204"), new SerialNumber("X14H702901"),
                    getTagSet("closed")),
            new Equipment(new Name("Paya Lebar Kovan CC"), new Phone("62844261"), new Date("16 December 2019"),
                    new Address("207 Hougang Street 21, Singapore 530207"), new SerialNumber("X14H703091"),
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
