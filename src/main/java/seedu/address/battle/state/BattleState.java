package seedu.address.battle.state;

/**
 * Enum that represents the state the battle is currently in.
 */
public enum BattleState {
    /**
     * The battle has not begun yet.
     */
    PRE_BATTLE("Preparing for battle..."),
    /**
     * The human player is currently putting their ships.
     */
    PLAYER_PUT_SHIP("You are currently placing ships..."),
    /**
     * The enemy player is currently putting their ships.
     */
    ENEMY_PUT_SHIP("Enemy is currently placing ships..."),
    /**
     * The human player is currently planning their attack.
     */
    PLAYER_ATTACK("You are currently planning attack..."),
    /**
     * The enemy player is currently planning their attack.
     */
    ENEMY_ATTACK("Enemy is currently planning attack..."),
    /**
     * The human player has won the game.
     */
    PLAYER_WIN("You have won!"),
    /**
     * The enemy player has won the game.
     */
    ENEMY_WIN("You have lost...");
    /**
     * A human-readable description of the state.
     * Usage: as-is
     */
    private final String description;

    BattleState(String description) {
        this.description = description;
    }

    /**
     * Gets a human-readable description of the battle state.
     * @return a human-readable description of the battle state
     */
    @Override
    public String toString() {
        return description;
    }
}
