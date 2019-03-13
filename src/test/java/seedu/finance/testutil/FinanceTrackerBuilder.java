package seedu.finance.testutil;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.record.Record;

/**
 * A utility class to help with building FinanceTracker objects.
 * Example usage: <br>
 *     {@code FinanceTracker ab = new FinanceTrackerBuilder().withRecord("John", "Doe").build();}
 */
public class FinanceTrackerBuilder {

    private FinanceTracker financeTracker;

    public FinanceTrackerBuilder() {
        financeTracker = new FinanceTracker();
    }

    public FinanceTrackerBuilder(FinanceTracker financeTracker) {
        this.financeTracker = financeTracker;
    }

    /**
     * Adds a new {@code Record} to the {@code FinanceTracker} that we are building.
     */
    public FinanceTrackerBuilder withRecord(Record record) {
        financeTracker.addRecord(record);
        return this;
    }

    public FinanceTracker build() {
        return financeTracker;
    }
}
