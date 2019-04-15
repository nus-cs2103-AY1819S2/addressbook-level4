package seedu.address.testutil;

import seedu.address.model.cell.Coordinates;

/**
 * EvilEnemy is like InterceptedEnemy, but throws an exception
 * when enemyShootAt is called
 */
public class EvilEnemy extends InterceptedEnemy {
    @Override
    public Coordinates enemyShootAt() {
        throw new RuntimeException();
    }
}
