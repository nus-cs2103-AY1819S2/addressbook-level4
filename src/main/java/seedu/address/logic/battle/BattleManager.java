package seedu.address.logic.battle;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * BattleManager is the implementor of the Battle interface.
 * In this game, we implement the case of one human player and N AI players (here N=1).
 */
public class BattleManager implements Battle {
    private Player humanPlayer;
    // The list of AI players who are waiting to take a turn.
    private ArrayList<Player> aiPlayers;

    private ObservableBooleanValue isPlayerTurn;

    public BattleManager(Player humanPlayer, List<Player> aiPlayers) {
        this.humanPlayer = humanPlayer;
        this.aiPlayers = new ArrayList<>(aiPlayers);
        isPlayerTurn = new SimpleBooleanProperty(true);
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
