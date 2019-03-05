package seedu.address.model.record;

import java.math.BigDecimal;

/**
 * Record representation of a MedicinePurchase
 */
public class MedicinePurchaseRecord extends Record {
    private final String medicineName;
    private final int quantity;
    private final BigDecimal cost;
    public MedicinePurchaseRecord(String medicineName, int quantity, BigDecimal cost) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.cost = cost;
    }
    @Override
    public Statistics toStatistics() {
        BigDecimal totalCost = cost.multiply(new BigDecimal(quantity));
        return new Statistics(0, BigDecimal.ZERO , totalCost);
    }
}
