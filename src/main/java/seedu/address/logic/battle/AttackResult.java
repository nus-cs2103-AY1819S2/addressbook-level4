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

    /**
     * Sets whether this AttackResult represents the result of a successful attack.
     * @param succeeds whether the attack was a success
     */
    protected void succeeds(boolean succeeds) {
        this.succeeds = succeeds;
    }

    /**
     * Sets whether this AttackResult represents the result of an attack that hit a ship.
     * @param hitsShip whether the attack hit an enemy ship
     */
    protected void hitsShip(boolean hitsShip) {
        this.hitsShip = hitsShip;
    }

    /**
     * Sets whether this AttackResult represents the result of an attack that destroyed an enemy ship
     * @param destroysShip whether the attack destroyed an enemy ship
     */
    protected void destroysShip(boolean destroysShip) {
        this.destroysShip = destroysShip;
    }

    /**
     * Sets whether this AttackResult represents the result of an attack that
     * caused the attacker to win.
     * @param winsGame whether the attack was a winning attack
     */
    protected void winsGame(boolean winsGame) {
        this.winsGame = winsGame;
    }

    /**
     * Checks if this AttackResult is of an attack which succeeded
     */
    public final boolean isSuccessful() {
        return succeeds;
    }

    /**
     * Checks if this AttackResult is of an attack which hit
     * (guess they never miss, huh?)
     */
    public final boolean isHit() {
        return hitsShip;
    }

    /**
     * Checks if this AttackResult is of an attack which destroyed a ship
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

    /**
     * Gets the {@code Status} of the cell which was hit, if any.
     * @return the {@code Status} of the hit cell
     * @see Status
     */
    public Status getStatus() {
        return attacker.getMapGrid().getCellStatus(coords);
    }

    /**
     * Returns a concise String representation of the attack result.
     */
    public abstract String resultString();

    /**
     * Returns a verbose String representation of the attack result.
     */
    @Override
    public String toString() {
        return String.format(ATTACK, attacker.getName(), coords, target.getName()) + resultString();
    }

    /**
     * Formats this AttackResult as if a human user was the attacker.
     */
    public String formatAsUserAttack() {
        return String.format("You attacked %s and ", coords) + resultString();
    }

    /**
     * Formats this AttackResult as if a computer enemy was the attacker.
     */
    public String formatAsEnemyAttack() {
        return String.format("Enemy attacked %s and ", coords) + resultString();
    }
}
