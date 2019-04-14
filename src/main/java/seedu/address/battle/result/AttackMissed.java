package seedu.address.battle.result;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * AttackMissed represents the result of an attack that missed.
 */
public class AttackMissed extends AttackResult {
    public AttackMissed(Player attacker, Player target, Coordinates cell) {
        super(attacker, target, cell);

        succeeds(true);
        hitsShip(false);
        destroysShip(false);
        winsGame(false);
    }

    @Override
    public String resultString() {
        return "missed";
    }
}
