package seedu.address.model.record;

import java.math.BigDecimal;

import seedu.address.model.medicine.Medicine;

/**
 * Record representation of a MedicinePurchase
 */
public class MedicinePurchaseRecord extends Record {
    private final Medicine medicine;
    private final int quantity;
    private final BigDecimal cost;

    public MedicinePurchaseRecord(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.cost = medicine.getPrice();
    }
    public MedicinePurchaseRecord(Medicine medicine, int quantity, BigDecimal cost) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.cost = cost;
    }
    @Override
    public Statistics toStatistics(RecordManager recordManager) {
        BigDecimal totalCost = cost.multiply(new BigDecimal(quantity));
        return new Statistics(0, BigDecimal.ZERO , totalCost);
    }
}
