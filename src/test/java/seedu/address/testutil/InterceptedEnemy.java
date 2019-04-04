package seedu.address.testutil;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;
import seedu.address.model.player.Enemy;

/**
 * Stub for the Enemy class, to aid in testing BeginCommand and AttackCommand.
 */
public class InterceptedEnemy extends Enemy {
    private boolean isPrepCalled;
    private boolean isReceiveStatusCalled;

    private boolean isEnemyShootAtCalled;

    public InterceptedEnemy() {
        super();
        isPrepCalled = false;
        isReceiveStatusCalled = false;
    }

    /**
     * Interceptor for prepEnemy().
     */
    @Override
    public void prepEnemy() {
        isPrepCalled = true;
        super.prepEnemy();
    }

    /**
     * Interceptor for enemyShootAt()
     */
    @Override
    public Coordinates enemyShootAt() {
        isEnemyShootAtCalled = true;
        return super.enemyShootAt();
    }

    /**
     * Interceptor for receiveStatus().
     */
    @Override
    public void receiveStatus(Status s) {
        isReceiveStatusCalled = true;
        super.receiveStatus(s);
    }

    public boolean isPrepCalled() {
        return isPrepCalled;
    }

    public boolean isReceiveStatusCalled() {
        return isReceiveStatusCalled;
    }

    public boolean isEnemyShootAtCalled() {
        return isEnemyShootAtCalled;
    }
}
