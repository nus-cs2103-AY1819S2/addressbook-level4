package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class WarningPanelSettings implements Serializable {

    public static final int DEFAULT_EXPIRY_THRESHOLD_VALUE = 10;
    public static final int DEFAULT_LOW_STOCK_THRESHOLD_VALUE = 20;

    private final int expiryThresholdValue;
    private final int lowStockThresholdValue;

    public WarningPanelSettings() {
        expiryThresholdValue = DEFAULT_EXPIRY_THRESHOLD_VALUE;
        lowStockThresholdValue = DEFAULT_LOW_STOCK_THRESHOLD_VALUE;
    }

    public WarningPanelSettings(int expiryThresholdValue, int lowStockThresholdValue) {
        this.expiryThresholdValue = expiryThresholdValue;
        this.lowStockThresholdValue = lowStockThresholdValue;
    }

    public int getExpiryThresholdValue() {
        return expiryThresholdValue;
    }

    public int getLowStockThresholdValue() {
        return lowStockThresholdValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WarningPanelSettings)) { //this handles null as well.
            return false;
        }

        WarningPanelSettings o = (WarningPanelSettings) other;

        return expiryThresholdValue == o.expiryThresholdValue
                && lowStockThresholdValue == o.lowStockThresholdValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expiryThresholdValue, lowStockThresholdValue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Expiry Threshold Value : " + expiryThresholdValue + "\n");
        sb.append("Low Stock Threshold Value : " + lowStockThresholdValue);
        return sb.toString();
    }
}
