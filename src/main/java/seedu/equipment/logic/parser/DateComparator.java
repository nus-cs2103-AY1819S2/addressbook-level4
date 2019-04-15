package seedu.equipment.logic.parser;

import java.util.Comparator;

import seedu.equipment.model.equipment.Equipment;

/**
 * A comparison function to sort the list by amount in descending order.
 */
public class DateComparator implements Comparator<Equipment> {
    @Override
    public int compare(Equipment e1, Equipment e2) {
        return e1.getDate().getDate().compareTo(e2.getDate().getDate());
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
