package seedu.equipment.logic.parser;

import java.util.Comparator;

import seedu.equipment.model.equipment.Equipment;

/**
 * A comparison function to sort the equipment by name in lexicographic order.
 */
public class NameComparator implements Comparator<Equipment> {
    @Override
    public int compare(Equipment e1, Equipment e2) {
        return e1.getName().toString().compareToIgnoreCase(e2.getName().toString());
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
