package seedu.address.battle.result;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * Represents the result of an attack that defeated an enemy.
 */
public class AttackDefeatedEnemy extends AttackResult {
    private String destroyedShipName;

    public AttackDefeatedEnemy(Player attacker, Player target, Coordinates cell, String destroyedShipName) {
        super(attacker, target, cell);
        this.destroyedShipName = destroyedShipName;

        succeeds(true);
        hitsShip(true);
        destroysShip(true);
        winsGame(true);
    }

    @Override
    public String resultString() {
        return String.format("destroyed %s and won the game", destroyedShipName);
    }
}
