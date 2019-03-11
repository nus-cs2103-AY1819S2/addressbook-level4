package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * AttackMissed represents the result of an attack that missed.
 */
public class AttackMissed extends AttackResult {
    public AttackMissed(Player attacker, Player target, Coordinates cell) {
        super(attacker, target, cell);
    }

    @Override
    public String toString() {
        return String.format("Attack by player %s on cell %s of player %s missed",
            attacker, coords, target);
    }
}
