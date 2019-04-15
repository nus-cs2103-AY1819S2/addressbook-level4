package seedu.address.battle.result;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * Represents the result of an invalid attack (e.g. to a non-existent player, or an invalid coordinate)
 */
public class AttackFailed extends AttackResult {
    private String reason;

    public AttackFailed(Player attacker, Player target, Coordinates cell, String reason) {
        super(attacker, target, cell);
        this.reason = reason;

        succeeds(false);
        hitsShip(false);
        destroysShip(false);
        winsGame(false);
    }

    @Override
    public String resultString() {
        return String.format("failed: %s", reason);
    }
}
