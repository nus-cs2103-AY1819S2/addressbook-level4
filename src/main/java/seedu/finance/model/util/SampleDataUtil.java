package seedu.finance.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;
import seedu.finance.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FinanceTracker} with sample data.
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

    public static ReadOnlyFinanceTracker getSampleFinanceTracker() {
        FinanceTracker sampleFT = new FinanceTracker();
        for (Record sampleRecord : getSampleRecords()) {
            sampleFT.addRecord(sampleRecord);
        }
        return sampleFT;
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
