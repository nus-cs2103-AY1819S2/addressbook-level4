package seedu.finance.testutil;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.record.Record;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FinanceTracker ab = new AddressBookBuilder().withRecord("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private FinanceTracker financeTracker;

    public AddressBookBuilder() {
        financeTracker = new FinanceTracker();
    }

    public AddressBookBuilder(FinanceTracker financeTracker) {
        this.financeTracker = financeTracker;
    }

    /**
     * Adds a new {@code Record} to the {@code FinanceTracker} that we are building.
     */
    public AddressBookBuilder withRecord(Record record) {
        financeTracker.addRecord(record);
        return this;
    }

    public FinanceTracker build() {
        return financeTracker;
    }
}
