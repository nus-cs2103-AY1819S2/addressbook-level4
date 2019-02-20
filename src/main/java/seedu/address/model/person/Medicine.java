package seedu.address.model.person;

import java.util.ArrayList;

/**
 * Represents the name and history of quantities of a particular medicine
 */
public class Medicine {

    private static int defaultThreshold = 50;

    public final String name;
    private ArrayList<Integer> quantity; //A record of history of quantities to support undo function
    private int pointer;
    private boolean thresholdIsDefault = true;
    private int threshold;

    /**
     * Constructs a medicine with given name and default quantity 0.
     * @param name The name of medicine
     */
    public Medicine(String name) {
        this(name, 0);
    }

    /**
     * Constructs a medicine with given name and given quantity;
     * @param name The name of medicine
     * @param amount The amount of medicine
     */
    public Medicine(String name, int amount) {
        this.name = name;
        this.quantity = new ArrayList<>();
        this.setQuantity(amount);
        this.pointer = -1;
    }

    public void setQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        pointer++;
        if (quantity.size() > pointer) {
            int n = quantity.size() - 1;
            for (int i = n; i >= pointer; i--) {
                quantity.remove(i);
            }
        }
        quantity.add(amount);
    }

    public int getQuantity() {
        return quantity.get(pointer);
    }

    /**
     * Add a given amount to the existing storage
     * @param change the amount to be added;
     */
    public void addQuantity(int change) {
        if (change <= 0) {
            throw new IllegalArgumentException("Change amount must be positive");
        }
        int current = this.getQuantity();
        setQuantity(current + change);
    }

    /**
     * Subtract a given amount to the existing storage;
     * Constraint: the subtracted amount must not be larger than existing storage;
     * @param change the amount to be subtracted
     */
    public void subtractQuantity(int change) {
        if (change > this.getQuantity()) {
            throw new IllegalArgumentException("Insufficient storage");
        }
        int current = this.getQuantity();
        setQuantity(current - change);
        checkIfSufficient();
    }

    /**
     * Called after each subtraction to detect if the storage is running low
     */
    private void checkIfSufficient() {
        int current = this.getQuantity();
        if (thresholdIsDefault) {
            if (current <= defaultThreshold) {
                //Throws a reminder
            }
        } else {
            if (current <= threshold) {
                //Throws a reminder
            }
        }
    }

    public void setThreshold(int threshold) {
        thresholdIsDefault = false;
        this.threshold = threshold;
    }

    /**
     * To undo a previous addition to this medicine;
     */
    public void undoChange() {
        if (pointer <= 0) {
            throw new IllegalStateException("No change to undo");
        }
        pointer--;
    }

    /**
     * To redo a undone change
     */
    public void redoChange() {
        if (pointer >= quantity.size() - 1) {
            throw new IllegalStateException("No change to redo");
        }
        pointer++;
    }
}
