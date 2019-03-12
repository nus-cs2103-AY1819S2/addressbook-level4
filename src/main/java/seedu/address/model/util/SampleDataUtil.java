package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.record.Amount;
import seedu.address.model.record.Date;
import seedu.address.model.record.Description;
import seedu.address.model.record.Name;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final String STANDARD_DESCRIPTION = "some description";
    public static Record[] getSampleRecords() {
        return new Record[] {
            new Record(new Name("Weekly groceries purchase"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), getTagSet("Shopping")),
            new Record(new Name("H and M Clothes"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), getTagSet("Shopping")),
            new Record(new Name("Chicken Rice lunch"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), getTagSet("Food")),
            new Record(new Name("Haircut"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), getTagSet("entertainment")),
            new Record(new Name("Bus Ride"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), getTagSet("Transportation")),
            new Record(new Name("Cigarettes"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), getTagSet("vices"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Record sampleRecord : getSampleRecords()) {
            sampleAb.addRecord(sampleRecord);
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
