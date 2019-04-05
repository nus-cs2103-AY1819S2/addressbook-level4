package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * Represents the result of an attack that hit and destroyed a ship.
 */
public class AttackDestroyedShip extends AttackResult {
    private String destroyedShipName;

    public AttackDestroyedShip(Player attacker, Player target, Coordinates cell, String destroyedShipName) {
        super(attacker, target, cell);
        this.destroyedShipName = destroyedShipName;
    }

    @Override
    public boolean isHit() {
        return true;
    }

    @Override
    public String toString() {
        return String.format(ATTACK + "destroyed %s",
            attacker.getName(), coords, target.getName(), destroyedShipName);
    }
}
