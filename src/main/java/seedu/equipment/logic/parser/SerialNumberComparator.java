package seedu.equipment.logic.parser;

import java.util.Comparator;

import seedu.equipment.model.equipment.Equipment;

/**
 * A comparison function to sort the equipment by the serial number in ascending order.
 */
public class SerialNumberComparator implements Comparator<Equipment> {
    @Override
    public int compare(Equipment e1, Equipment e2) {
        return e1.getSerialNumber().toString().compareTo(e2.getSerialNumber().toString());
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
