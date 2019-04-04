package seedu.finance.model.util;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;

/**
 * Contains utility methods for populating {@code FinanceTracker} with sample data.
 */
public class SampleDataUtil {

    public static final String STANDARD_DESCRIPTION = "some description";
    public static Record[] getSampleRecords() {
        return new Record[] {
            new Record(new Name("Weekly groceries purchase"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), new Category("shopping")),
            new Record(new Name("H and M Clothes"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), new Category("shopping")),
            new Record(new Name("Chicken Rice lunch"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), new Category("food")),
            new Record(new Name("Haircut"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), new Category("entertainment")),
            new Record(new Name("Bus Ride"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), new Category("transportation")),
            new Record(new Name("Cigarettes"), new Amount("100"), new Date("12/02/2018"),
                new Description(STANDARD_DESCRIPTION), new Category("vices"))
        };
    }

    public static ReadOnlyFinanceTracker getSampleFinanceTracker() {
        FinanceTracker sampleFinanceTracker = new FinanceTracker();
        for (Record sampleRecord : getSampleRecords()) {
            sampleFinanceTracker.addRecord(sampleRecord);
        }
        return sampleFinanceTracker;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Category getCategory(String string) {
        /*return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());*/
        return new Category(string);
    }

}
