package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;

/**
 * Represents the name and history of quantities of a particular medicine
 */
public class Medicine {

    public static final String MESSAGE_CONSTRAINTS = "Medicine name can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "\\S+";
    public static final String TO_STRING = "Medicine: %1$s, Quantity: %2$d, Price: %3$s";
    public static final String REMINDER_TITLE_IF_INSUFFICIENT = "Quantity of %1$s is too low.";
    public static final String REMINDER_COMMENT_IF_INSUFFICIENT =
            "Current quantity is at %1$d.\nThe minimum treshold is %2$d.";
    private static final int DEFAULT_THRESHOLD = 0;

    //private static ReminderManager reminderManager = new ReminderManager();
    //
    public final String name;
    private int quantity;
    private int threshold;
    private BigDecimal price;

    public Medicine() {
        name = "";
    }

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
        requireNonNull(name);
        checkArgument(isValidMedicine(name), MESSAGE_CONSTRAINTS);
        if (amount < 0) {
            throw new IllegalArgumentException("Quantity should not be negative.");
        }
        this.name = name;
        setQuantity(amount);
        this.threshold = DEFAULT_THRESHOLD;
    }
    /**
    public static void setReminderManager(ReminderManager newReminderManager) {
        reminderManager = newReminderManager;
    }
    */
    public static boolean isValidMedicine(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public void setQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        quantity = amount;
        //generateOrDeleteReminder();
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
        if (change <= 0) {
            throw new IllegalArgumentException("Change amount must be positive");
        }
        if (change > this.getQuantity()) {
            throw new IllegalArgumentException("Insufficient storage");
        }
        int current = this.getQuantity();
        setQuantity(current - change);
    }

    /**
     * Called after each subtraction to detect if the storage is running low
     */
    public boolean isSufficient() {
        return quantity >= threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("threshold must be non-negative");
        }
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING, name, quantity, price.toString());
    }

    public String viewDetail() {
        return this.toString() + " With threshold: " + threshold;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(new BigDecimal("0")) == -1) {
            throw new IllegalArgumentException("Price should not be negative.");
        }
        this.price = price;
    }
}
