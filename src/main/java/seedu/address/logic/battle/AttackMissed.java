package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;

/**
 * AttackMissed represents the result of an attack that missed.
 */
public class AttackMissed extends AttackResult {
    public AttackMissed(int attacker, int target, Coordinates cell) {
        super(attacker, target, cell);
    }

    @Override
    public Status getStatus() {
        return Status.EMPTY;
    }

    @Override
    public String toString() {
        return String.format("Attack by player %d on cell %s of player %d missed",
            attacker, coords, target);
    }
}
