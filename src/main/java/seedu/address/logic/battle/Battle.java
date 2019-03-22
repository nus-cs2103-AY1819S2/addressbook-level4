package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * API of the Battle component
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
    public AttackResult humanEndTurn();

    /**
     * Returns the human player in the game.
     */
    Player getHumanPlayer();

    /**
     * Returns the computer player.
     */
    Player getEnemyPlayer();
}
