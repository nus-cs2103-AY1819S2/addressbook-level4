package seedu.finance.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.AppUtil.checkArgument;

import javafx.collections.ObservableList;
import seedu.finance.model.record.Record;

/**
 * Represents the Budget for the particular instance of the Finance Tracker
 */
public class Budget {

    protected double totalBudget;
    protected double currentBudget;
    protected double currentSpendings;


    /**
     * Constructs a {@code Budget} with no initial value.
     */
    public Budget() {
        totalBudget = 0;
        currentBudget = 0;
        currentSpendings = 0;
    }

    public Budget(double initialBudget) {
        checkArgument(isValidBudget(initialBudget, initialBudget));

        this.totalBudget = initialBudget;
        this.currentBudget = initialBudget;
        this.currentSpendings = 0;
    }

    public Budget(double totalBudget, double currentBudget) {
        checkArgument(isValidBudget(totalBudget, currentBudget));

        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
        this.currentSpendings = totalBudget - currentBudget;
    }

    public Budget(Budget budget) {
        requireNonNull(budget);

        this.totalBudget = budget.getTotalBudget();
        this.currentBudget = budget.getCurrentBudget();
        this.currentSpendings = budget.getCurrentSpendings();
    }

    /**
     * Called to set the total and current budget of the class.
     *
     * @param totalBudget the totalBudget to set
     * @param currentBudget the currentBudget to set
     */
    public void set(double totalBudget, double currentBudget) {
        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
        this.currentSpendings = totalBudget - currentBudget;
    }

    /**
     * Called to check whether the budget values are valid.
     *
     * @param totalBudget the total budget to test.
     * @param currentBudget the current budget left to test.
     * @return true if total budget is more than current Budget and 0.
     */
    public static boolean isValidBudget(double totalBudget, double currentBudget) {
        // Check that total budget is non-zero
        if (totalBudget < 0) {
            return false;
        }
        // check that total budget is more than current budget
        return totalBudget >= currentBudget;
    }

    /**
     * Method to update budget
     * @param records the records in Finance Tracker
     */
    public void updateBudget(ObservableList<Record> records) {
        if (totalBudget == 0) {
            return;
        }
        currentBudget = totalBudget;
        currentSpendings = 0;
        records.forEach(record -> currentBudget -= Double.parseDouble(record.getAmount().toString()));
        records.forEach(record -> currentSpendings += Double.parseDouble(record.getAmount().toString()));
    }

    /**
     * Method to add spendings of record
     * @param record the record to be added
     * @return true if currentSpendings is within totalBudget
     */
    public boolean addRecord(Record record) {
        Double spending = record.getAmount().getValue();
        this.currentSpendings += spending;
        this.currentBudget -= spending;
        return this.currentSpendings <= totalBudget;
    }

    /**
     * Method to edit current budget and spendings based on changes in records.
     *
     * @param target The original record
     * @param editedRecord The record with the edits
     */
    public void editRecord(Record target, Record editedRecord) {
        this.currentBudget = currentBudget + target.getAmount().getValue()
                - editedRecord.getAmount().getValue();
        this.currentSpendings = this.totalBudget - this.currentBudget;
    }

    /**
     * Method to remove spendings of record
     * @param record the record to be removed
     */
    public void removeRecord(Record record) {
        Double spending = record.getAmount().getValue();
        this.currentSpendings -= spending;
        this.currentBudget += spending;
    }

    public boolean isSet() {
        return !(totalBudget == 0);
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public double getCurrentBudget() {
        return currentBudget;
    }

    public double getCurrentSpendings() {
        return (totalBudget - currentBudget);
    }

    @Override
    public String toString() {
        return currentBudget + "/" + totalBudget;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return this.totalBudget == otherBudget.totalBudget
                && this.currentBudget == otherBudget.currentBudget;
    }
}
