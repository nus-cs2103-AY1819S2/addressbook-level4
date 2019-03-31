package seedu.finance.model.budget;

import javafx.collections.ObservableList;
import seedu.finance.model.record.Record;

/**
 * Represents the Budget for the particular instance of the Finance Tracker
 */
public class Budget {

    private double totalBudget;

    private double currentBudget;

    /**
     * Constructs a {@code Budget} with no initial value.
     */
    public Budget() {
        totalBudget = 0;
        currentBudget = 0;
    }

    public Budget(double initialBudget) {
        this.totalBudget = initialBudget;
        this.currentBudget = initialBudget;
    }

    public Budget(double totalBudget, double currentBudget) {
        this.totalBudget = totalBudget;
        this.currentBudget = currentBudget;
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
    }

    public void updateBudget(ObservableList<Record> records) {
        if (totalBudget == 0) {
            return;
        }
        currentBudget = totalBudget;
        records.forEach(record -> currentBudget -= Double.parseDouble(record.getAmount().toString()));
        if (currentBudget < 0) {
            currentBudget = 0;
        }
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

    @Override
    public String toString() {
        return currentBudget + "/" + totalBudget;
    }
}
