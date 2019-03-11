package seedu.address.logic.battle;

import java.util.List;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * BattleManager is the implementor of the Battle interface.
 */
public class BattleManager implements Battle {
    @Override
    public void addPlayer(Player p) {
        return;
    }

    @Override
    public void beginGame() {
        return;
    }

    @Override
    public AttackResult humanAttackAI(Player p, Coordinates coord) {
        return null;
    }

    @Override
    public List<AttackResult> humanEndTurn() {
        return null;
    }
}
