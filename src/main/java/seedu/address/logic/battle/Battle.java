package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * API of the Battle component
 *
 * The Battle component manages the battle between a human player and an enemy player.
 */
public interface Battle {
    /**
     * Begins the game and gives all players a Map View.
     */
    public void beginGame();

    /**
     * Handles the human player attacking another player.
     * @return Result of the player attack.
     */
    public AttackResult humanPerformAttack(Coordinates coord);

    /**
     * Ends the player turn and causes the AI to make its attack.
     */
    public AttackResult takeComputerTurn();

    /**
     * Returns the human player.
     * TODO: Replace Player with a more specific class, e.g. HumanPlayer
     */
    Player getHumanPlayer();

    /**
     * Returns the computer player.
     * TODO: Replace Player with a more specific class, e.g. EnemyPlayer
     */
    Player getEnemyPlayer();
}
