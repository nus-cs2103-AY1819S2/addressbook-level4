package quickdocs.model.record;

import java.math.BigDecimal;

import quickdocs.model.medicine.Medicine;

/**
 * Record representation of a MedicinePurchase
 */
public class MedicinePurchaseRecord extends Record {
    private final Medicine medicine;
    private final int quantity;
    private final BigDecimal cost;

    public MedicinePurchaseRecord(Medicine medicine, int quantity) {
        if (medicine.getPrice() == null) {
            throw new NullPointerException("Price not set");
        }
        if (quantity <= 0 || medicine.getPrice().compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        this.medicine = medicine;
        this.quantity = quantity;
        this.cost = medicine.getPrice();
    }
    public MedicinePurchaseRecord(Medicine medicine, int quantity, BigDecimal cost) {
        if (quantity <= 0 || cost.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        this.medicine = medicine;
        this.quantity = quantity;
        this.cost = cost;
    }
    @Override
    public Statistics toStatistics(StatisticsManager statisticsManager) {
        BigDecimal totalCost = cost.multiply(new BigDecimal(quantity));
        return new Statistics(0, BigDecimal.ZERO, totalCost);
    }
}
