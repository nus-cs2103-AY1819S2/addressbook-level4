package seedu.address.battle.result;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * Represents the result of an attack that hit, but did not destroy, a ship
 */
public class AttackHit extends AttackResult {
    public AttackHit(Player attacker, Player target, Coordinates cell) {
        super(attacker, target, cell);

        succeeds(true);
        hitsShip(true);
        destroysShip(false);
        winsGame(false);
    }

    @Override
    public String resultString() {
        return String.format("hit");
    }
}
