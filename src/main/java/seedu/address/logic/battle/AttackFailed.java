package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

public class AttackFailed extends AttackResult {
    private String reason;

    public AttackFailed(Player attacker, Player target, Coordinates cell, String reason) {
        super(attacker, target, cell);
        this.reason = reason;
    }
    /**
     * Checks if this AttackResult is a hit or a miss
     * (guess they never miss, huh?)
     */
    public boolean isHit() {
        return false;
    }

    /**
     * Returns a String representation of the attack result.
     */
    @Override
    public String toString() {
        return String.format(ATTACK + "failed: %s",
            attacker, target, coords, reason);
    }
}
