package seedu.finance.model.budget;

import javafx.collections.ObservableList;
import seedu.finance.model.record.Record;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.AppUtil.checkArgument;

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
     * Called to set the budget Amount value wrapped in a ObjectProperty.
     *
     * @param totalBudget
     * @param currentBudget
     */
    public void set(double totalBudget, double currentBudget) {
        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
        this.currentSpendings = totalBudget - currentBudget;
    }

    public static boolean isValidBudget(double totalBudget, double currentBudget) {
        // Check for negative total budget
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
        return currentSpendings;
    }

    /**
     * Method to reset spendings to 0
     */
    public void clearSpendings() {
        this.currentSpendings = 0;
        this.currentBudget = totalBudget;
    }

    @Override
    public String toString() {
        return currentBudget + "/" + totalBudget;
    }

    @Override
    public boolean equals(Object budget) {
        Budget otherBudget = (Budget) budget;
        return this.totalBudget == otherBudget.totalBudget;
    }
}
