package seedu.address.logic.battle;

import java.util.List;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * API of the Battle component
 */
public interface Battle {
    /**
     * Adds a Player into the game.
     */
    public void addPlayer(Player p);

    /**
     * Begins the game and gives all players a Map View.
     */
    public void beginGame();

    /**
     * Handles the human player attacking another player.
     * @return Result of the player attack.
     */
    public AttackResult humanAttackAI(Player p, Coordinates coord);

    /**
     * Ends the player turn and causes all AIs to make attacks.
     */
    public List<AttackResult> humanEndTurn();
}
