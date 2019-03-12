package seedu.address.logic.battle;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * Represents the result of an attack that hit and destroyed a ship.
 */
public class AttackDestroyedShip extends AttackResult {
    private Battleship destroyedShip;

    public AttackDestroyedShip(Player attacker, Player target, Coordinates cell, Battleship destroyedShip) {
        super(attacker, target, cell);
        this.destroyedShip = destroyedShip;
    }

    @Override
    public boolean isHit() {
        return false;
    }

    @Override
    public String toString() {
        return String.format(ATTACK + "destroyed %s",
            attacker.getName(), coords, target.getName(), destroyedShip.getName());
    }
}
