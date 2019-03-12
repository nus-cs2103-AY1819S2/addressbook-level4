package seedu.address.logic.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.cell.Cell;
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

    private BooleanProperty isPlayerTurn;

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
    public AttackResult humanPerformAttack(String enemyName, Coordinates coord) {
        Optional<Player> target = aiPlayers.stream()
            .filter((Player p) -> p.getName().equals(enemyName))
            .findFirst();
        if (target.isPresent()) {
            return performAttack(humanPlayer, target.get(), coord);
        } else {
            return new AttackFailed(humanPlayer, enemyName, coord,
                "could not find player with that name");
        }
    }

    /**
     * The human attacks the default enemy.
     */
    public AttackResult humanPerformAttack(Coordinates coord) {
        return performAttack(humanPlayer, humanPlayer, coord);
    }

    /**
     * An Player attacks an enemy.
     */
    private AttackResult performAttack(Player attacker, Player target, Coordinates coord) {
        try {
            Cell cell = target.getMapGrid().getCell(coord);
            if (cell.receiveAttack()) {
                // we hit a ship
                Battleship hitShip = cell.getBattleship().get();
                if (hitShip.isDestroyed()) {
                    return new AttackDestroyedShip(attacker, target, coord, hitShip);
                } else {
                    return new AttackHit(attacker, target, coord);
                }
            } else {
                return new AttackMissed(attacker, target, coord);
            }
        } catch (IndexOutOfBoundsException ioobe) {
            return new AttackFailed(attacker, target.getName(), coord, "coordinates out of bounds");
        } catch (Exception ex) {
            return new AttackFailed(attacker, target.getName(), coord, ex.getMessage());
        }
    }

    @Override
    public List<AttackResult> humanEndTurn() {
        isPlayerTurn.setValue(false);

        ArrayList<AttackResult> results = new ArrayList<>();
        for (Player ai: aiPlayers) {
            // make the AIs take turns
        }

        isPlayerTurn.setValue(true);

        return results;
    }
}
