package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;

/**
 * An AttackResult represents the end result of an attack.
 */
public abstract class AttackResult {
    protected int attacker;
    protected int target;
    protected Coordinates coords;

    protected AttackResult(int attacker, int target, Coordinates coords) {
        this.attacker = attacker;
        this.target = target;
        this.coords = coords;
    }

    /**
     * Returns the player ID of the attacker.
     */
    public int getAttacker() {
        return attacker;
    }

    /**
     * Returns the player ID of the attacked.
     */
    public int getTarget() {
        return target;
    }

    /**
     * Returns the coordinate of the cell which was attacked.
     */
    public Coordinates getCoords() {
        return coords;
    }

    /**
     * Returns the status of the attacked cell.
     */
    public abstract Status getStatus();

    /**
     * Returns a String representation of the attack result.
     */
    @Override
    public abstract String toString();
}
