package seedu.address.logic.battle;

import java.util.List;

import seedu.address.model.battleship.Name;
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
    public AttackResult humanPerformAttack(String enemyName, Coordinates coord);

    /**
     * Ends the player turn and causes all AIs to make attacks.
     */
    public List<AttackResult> humanEndTurn();
}
