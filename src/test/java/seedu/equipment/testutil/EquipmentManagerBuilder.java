package seedu.equipment.testutil;

import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.equipment.Equipment;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code EquipmentManager ab = new EquipmentManagerBuilder().withPerson("John", "Doe").build();}
 */
public class EquipmentManagerBuilder {

    private EquipmentManager equipmentManager;

    public EquipmentManagerBuilder() {
        equipmentManager = new EquipmentManager();
    }

    public EquipmentManagerBuilder(EquipmentManager equipmentManager) {
        this.equipmentManager = equipmentManager;
    }

    /**
     * Adds a new {@code Equipment} to the {@code EquipmentManager} that we are building.
     */
    public EquipmentManagerBuilder withPerson(Equipment equipment) {
        equipmentManager.addPerson(equipment);
        return this;
    }

    public EquipmentManager build() {
        return equipmentManager;
    }
}
