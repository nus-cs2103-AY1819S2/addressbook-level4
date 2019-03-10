package seedu.address.model.cell;

/**
 * Enum that has different statuses a cell can have
 */
public enum Status {
    /**
     * This cell has not been hit before, and its state is not known to enemies.
     */
    HIDDEN,
    /**
     * This cell has been hit before, and there is no ship here.
     */
    EMPTY,
    /**
     * This cell has been hit before, and there is a damaged ship here.
     */
    HIT,
    /**
     * This cell has been hit before, and the ship here has been destroyed.
     */
    DESTROYED


}
