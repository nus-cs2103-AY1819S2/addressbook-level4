package seedu.address.logic.battle;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.MapGrid;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;
import seedu.address.model.player.Enemy;
import seedu.address.model.player.Player;

/**
 * BattleManager is the implementor of the Battle interface.
 * In this game, we implement the case of one human player and N AI players (here N=1).
 */
public class BattleManager implements Battle {
    private static final Logger logger = LogsCenter.getLogger(BattleManager.class);
    /**
     * The human player
     */
    private Player humanPlayer;
    /**
     * The enemy player
     */
    private Enemy enemyPlayer;

    public BattleManager(Player humanPlayer, Enemy enemyPlayer) {
        requireNonNull(humanPlayer);
        requireNonNull(enemyPlayer);
        this.humanPlayer = humanPlayer;
        this.enemyPlayer = enemyPlayer;
    }

    @Override
    public void beginGame() {
        enemyPlayer.prepEnemy();
    }

    @Override
    public AttackResult humanPerformAttack(Coordinates coord) {
        requireNonNull(coord);
        return performAttack(humanPlayer, enemyPlayer, coord);
    }

    /**
     * A Player attacks another Player.
     */
    private AttackResult performAttack(Player attacker, Player target, Coordinates coord) {
        logger.info(String.format(AttackResult.ATTACK, attacker.getName(), coord, target.getName()));
        try {
            MapGrid targetMapGrid = target.getMapGrid();
            if (targetMapGrid.attackCell(coord)) {
                // we hit a ship
                if (targetMapGrid.getCellStatus(coord) == Status.DESTROYED) {
                    String hitShipName = targetMapGrid.getShipNameInCell(coord);
                    return new AttackDestroyedShip(attacker, target, coord, hitShipName);
                } else {
                    return new AttackHit(attacker, target, coord);
                }
            } else {
                return new AttackMissed(attacker, target, coord);
            }
        } catch (IndexOutOfBoundsException ioobe) {
            return new AttackFailed(attacker, target, coord, "coordinates out of bounds");
        } catch (Exception ex) {
            return new AttackFailed(attacker, target, coord, ex.getMessage());
        }
    }

    @Override
    public AttackResult takeComputerTurn() {
        // AI takes its turn

        Coordinates enemyAttack = enemyPlayer.enemyShootAt();

        AttackResult res = performAttack(enemyPlayer, humanPlayer, enemyAttack);
        // update the enemy with it's result
        logger.info(String.format("+++++++BATMAN SAYS: LAST HIT ON: " + enemyAttack.toString()
                + " status: " + res.toString()));
        return res;
    }

    /**
     * Returns the human player in the game.
     */
    @Override
    public Player getHumanPlayer() {
        return humanPlayer;
    }

    /**
     * Returns the computer player.
     */
    @Override
    public Enemy getEnemyPlayer() {
        return enemyPlayer;
    }
}
