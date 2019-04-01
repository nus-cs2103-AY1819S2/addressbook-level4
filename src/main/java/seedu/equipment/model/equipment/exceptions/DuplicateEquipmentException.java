package seedu.equipment.model.equipment.exceptions;

/**
 * Signals that the operation will result in duplicate Equipments (Equipments are considered duplicates
 * if they have the same identity).
 */
public class DuplicateEquipmentException extends RuntimeException {
    public DuplicateEquipmentException() {
        super("Operation would result in duplicate equipments");
    }
}
