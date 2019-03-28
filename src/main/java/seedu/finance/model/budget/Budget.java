package seedu.finance.model.budget;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import seedu.finance.model.record.Amount;

/**
 * Represents the Budget for the particular instance of the Finance Tracker
 */
public class Budget {

    private final ObjectProperty<Amount> value = new SimpleObjectProperty<>();

    /**
     * Constructs a {@code Budget} with no initial value.
     */
    public Budget() {
        value.set(null);
    }

    public Budget(Amount amount) {
        value.set(amount);
    }

    /**
     * Called to set the budget Amount value wrapped in a ObjectProperty.
     *
     * @param amount amount to be set as budget
     */
    public void set(Amount amount) {
        value.set(amount);
    }

    public boolean isSet() {
        return !(value.getValue() == null);
    }

    public final ObjectProperty<Amount> valueProperty() {
        return value;
    }

    @Override
    public String toString() {
        if (value.getValue() == null) {
            return "0";
        }
        return value.getValue().toString();
    }
}
