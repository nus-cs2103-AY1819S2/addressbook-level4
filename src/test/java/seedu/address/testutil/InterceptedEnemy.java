package seedu.address.testutil;

import seedu.address.model.player.Enemy;

/**
 * Stub for the Enemy class, to aid in testing BeginCommand and EndTurnCommand.
 */
public class InterceptedEnemy extends Enemy {
    private boolean isPrepCalled;
    private boolean isReceiveStatusCalled;

    public InterceptedEnemy() {
        super();
        isPrepCalled = false;
        isReceiveStatusCalled = false;
    }

    @Override
    public void prepEnemy() {
        isPrepCalled = true;
        super.prepEnemy();
    }

    /**
     * Interceptor for receiveStatus().
     */
    public void receiveStatus() {
        isReceiveStatusCalled = true;
        //super.receiveStatus();
    }

    public boolean isPrepCalled() {
        return isPrepCalled;
    }

    public boolean isReceiveStatusCalled() {
        return isReceiveStatusCalled;
    }
}
