package seedu.address.logic.battle;

import java.util.List;

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
     * @return Result of the player attack.
     */
    AttackResult humanPerformAttack(Coordinates coord);

    /**
     * Causes the AI to make its attack(s).
     */
    List<AttackResult> takeComputerTurns();

    /**
     * Returns the human player.
     */
    Player getHumanPlayer();

    /**
     * Returns the computer player.
     */
    Enemy getEnemyPlayer();
}
