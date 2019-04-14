package seedu.address.battle;

import java.util.List;

import seedu.address.battle.result.AttackResult;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Enemy;
import seedu.address.model.player.Player;

/**
 * API of the Battle component
 * <br>
 * The Battle component manages the battle between a human player and an enemy player.
 */
public interface Battle {
    /**
     * Begins the battle.
     */
    void beginGame();

    /**
     * Handles the human player attacking the computer enemy.
     * @param coord The coordinate that the human attacks
     * @return Result of the player attack.
     */
    AttackResult humanPerformAttack(Coordinates coord);

    /**
     * Causes the AI to make its attack(s).
     * @return a list of the results of the computer's attacks, in order
     */
    List<AttackResult> takeComputerTurns();

    /**
     * Returns the human player.
     * @return the human player
     */
    Player getHumanPlayer();

    /**
     * Returns the computer enemy
     * player.
     * @return the enemy player
     */
    Enemy getEnemyPlayer();
}
