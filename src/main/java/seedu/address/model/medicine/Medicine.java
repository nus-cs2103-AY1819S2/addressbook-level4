package seedu.address.model.medicine;

/**
 * Represents the name and history of quantities of a particular medicine
 */
public class Medicine {

    private static int defaultThreshold = 50;

    public final String name;
    private int quantity;
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
        setQuantity(amount);
    }

    public void setQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        quantity = amount;
    }

    public int getQuantity() {
        return quantity;
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
}
