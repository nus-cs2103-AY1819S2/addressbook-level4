package seedu.address.battle;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.battle.result.AttackDefeatedEnemy;
import seedu.address.battle.result.AttackDestroyedShip;
import seedu.address.battle.result.AttackFailed;
import seedu.address.battle.result.AttackHit;
import seedu.address.battle.result.AttackMissed;
import seedu.address.battle.result.AttackResult;
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
        AttackResult res = performAttack(humanPlayer, enemyPlayer, coord);
        logger.info(res.formatAsUserAttack());
        return res;
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
                    // we destroyed a ship
                    String hitShipName = targetMapGrid.getShipNameInCell(coord);
                    if (target.getFleet().isAllDestroyed()) {
                        // all enemy ships destroyed - win!
                        return new AttackDefeatedEnemy(attacker, target, coord, hitShipName);
                    } else {
                        // one enemy ship destroyed
                        return new AttackDestroyedShip(attacker, target, coord, hitShipName);
                    }
                } else {
                    // we hit but did not destroy a ship
                    return new AttackHit(attacker, target, coord);
                }
            } else {
                // we didn't hit anything
                return new AttackMissed(attacker, target, coord);
            }
        } catch (IndexOutOfBoundsException ioobe) {
            return new AttackFailed(attacker, target, coord, "coordinates out of bounds");
        }
    }

    @Override
    public List<AttackResult> takeComputerTurns() {
        // AI takes its turn
        List<AttackResult> resList = new ArrayList<>();
        try {
            AttackResult lastRes;
            do {
                // ask enemy for an attack
                Coordinates enemyAttack = enemyPlayer.enemyShootAt();
                lastRes = performAttack(enemyPlayer, humanPlayer, enemyAttack);
                // update the enemy with its result
                enemyPlayer.receiveStatus(humanPlayer.getMapGrid().getCellStatus(enemyAttack));
                logger.info(lastRes.formatAsEnemyAttack());
                resList.add(lastRes);
                // update the UI for every enemy attack
                enemyPlayer.getMapGrid().updateUi();
                // if the Enemy has won, just end it now
                if (lastRes.isWin()) {
                    break;
                }
                // if the Enemy hit, they get to take another turn
            } while (lastRes.isHit());
            return resList;
        } catch (Exception ex) {
            resList.add(
                new AttackFailed(enemyPlayer, humanPlayer, null, ex.getMessage()));
            return resList;
        }
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
