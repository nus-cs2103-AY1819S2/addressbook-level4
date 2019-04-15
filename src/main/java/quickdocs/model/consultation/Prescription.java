package quickdocs.model.consultation;

import quickdocs.model.medicine.Medicine;

/**
 * Indicate the drug and the quantity administered to the person
 */
public class Prescription {

    public static final String MEDICINE_CONSTRAINTS = "Medicine name can take any values, and it should not be blank";
    //public static final String MEDICINE_REGEX = "[^\\s].*";

    private Medicine medicine;
    private int quantity;

    public Prescription() {
    }

    public Prescription(Medicine medicine, int quantity) {

        //if (!medicine.matches(MEDICINE_REGEX)) {
        //    throw new IllegalArgumentException(MEDICINE_CONSTRAINTS);
        //}

        if (quantity < 1) {
            throw new IllegalArgumentException("Amount administered must be a non-zero positive number");
        }

        this.medicine = medicine;
        this.quantity = quantity;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Medicine: " + getMedicine().name + " quantity: " + getQuantity() + "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Prescription
                && getQuantity() == ((Prescription) other).getQuantity()
                && getMedicine().equals(((Prescription) other).getMedicine()));
    }
}
