package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * An AttackResult represents the end result of an attack.
 */
public abstract class AttackResult {
    protected Player attacker;
    protected Player target;
    protected Coordinates coords;

    protected AttackResult(Player attacker, Player target, Coordinates coords) {
        this.attacker = attacker;
        this.target = target;
        this.coords = coords;
    }

    /**
     * Returns the player ID of the attacker.
     */
    public Player getAttacker() {
        return attacker;
    }

    /**
     * Returns the player ID of the attacked.
     */
    public Player getTarget() {
        return target;
    }

    /**
     * Returns the coordinate of the cell which was attacked.
     */
    public Coordinates getCoords() {
        return coords;
    }

    /**
     * Returns a String representation of the attack result.
     */
    @Override
    public abstract String toString();
}
