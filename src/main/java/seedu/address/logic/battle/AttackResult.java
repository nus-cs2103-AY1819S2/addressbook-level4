package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;
import seedu.address.model.player.Player;

/**
 * An AttackResult represents the end result of an attack.
 */
public abstract class AttackResult {
    public static final String ATTACK = "Attack by player %s on cell %s of player %s ";

    // refactoring the way of handling attributes to enable a declarative style
    private boolean succeeds;
    private boolean hitsShip;
    private boolean destroysShip;
    private boolean winsGame;

    private Player attacker;
    private Player target;
    private Coordinates coords;

    protected AttackResult(Player attacker, Player target, Coordinates coords) {
        this.attacker = attacker;
        this.target = target;
        this.coords = coords;
    }

    protected void succeeds(boolean succeeds) {
        this.succeeds = succeeds;
    }

    protected void hitsShip(boolean hitsShip) {
        this.hitsShip = hitsShip;
    }

    protected void destroysShip(boolean destroysShip) {
        this.destroysShip = destroysShip;
    }

    protected void winsGame(boolean winsGame) {
        this.winsGame = winsGame;
    }

    /**
     * Checks if this AttackResult actually succeeds in hitting a cell
     */
    public final boolean isSuccessful() {
        return succeeds;
    }

    /**
     * Checks if this AttackResult is a hit or a miss
     * (guess they never miss, huh?)
     */
    public final boolean isHit() {
        return hitsShip;
    }

    /**
     * Checks if this AttackResult is of the destruction of a ship
     */
    public final boolean isDestroy() {
        return destroysShip;
    }

    /**
     * Checks if this AttackResult is of a winning attack
     */
    public final boolean isWin() {
        return winsGame;
    }

    public Status getStatus() {
        return attacker.getMapGrid().getCellStatus(coords);
    }

    /**
     * Returns a short form of the attack result, without the front portion
     * "Player __ attacked __ __"
     */
    public abstract String resultString();

    /**
     * Returns a String representation of the attack result.
     */
    @Override
    public String toString() {
        return String.format(ATTACK, attacker.getName(), coords, target.getName()) + resultString();
    }

    /**
     * Formats the string as if the user attacked.
     */
    public String formatAsUserAttack() {
        return String.format("You attacked %s and ", coords) + resultString();
    }

    /**
     * Formats the string as if the enemy attacked.
     */
    public String formatAsEnemyAttack() {
        return String.format("Enemy attacked %s and ", coords) + resultString();
    }
}
