package seedu.equipment.logic.parser;

import java.util.Comparator;

import seedu.equipment.model.equipment.Equipment;

/**
 * A comparison function to sort the list by client's phone number in ascending order.
 */
public class PhoneComparator implements Comparator<Equipment> {
    @Override
    public int compare(Equipment e1, Equipment e2) {
        return e1.getPhone().toString().compareTo(e2.getPhone().toString());
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
