package seedu.address.testutil;

import seedu.address.model.Inventory;
import seedu.address.model.medicine.Medicine;

/**
 * A utility class to help with building inventory objects.
 * Example usage: <br>
 *     {@code Inventory inv = new InventoryBuilder().withMedicine("John", "Doe").build();}
 */

public class InventoryBuilder {

    private Inventory inventory;

    public InventoryBuilder() {
        inventory = new Inventory();
    }

    public InventoryBuilder(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds a new {@code Medicine} to the {@code inventory} that we are building.
     */
    public InventoryBuilder withMedicine(Medicine medicine) {
        inventory.addMedicine(medicine);
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
