package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysHealthWorker;

import org.junit.Test;

import guitests.guihandles.HealthWorkerCardHandle;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.testutil.HealthWorkerBuilder;

public class HealthWorkerCardTest extends GuiUnitTest {

    @Test
    public void display() {

        HealthWorker worker = new HealthWorkerBuilder().build();
        HealthWorkerCard healthWorkerCard = new HealthWorkerCard(worker, 1);
        uiPartRule.setUiPart(healthWorkerCard);
        assertCardDisplay(healthWorkerCard, worker, 1);
    }

    @Test
    public void equals() {
        HealthWorker worker = new HealthWorkerBuilder().build();
        HealthWorkerCard healthWorkerCard = new HealthWorkerCard(worker, 0);

        // same health worker, same index -> returns true
        HealthWorkerCard copy = new HealthWorkerCard(worker, 0);
        assertTrue(healthWorkerCard.equals(copy));

        // same object -> returns true
        assertTrue(healthWorkerCard.equals(healthWorkerCard));

        // null -> returns false
        assertFalse(healthWorkerCard.equals(null));

        // different types -> returns false
        assertFalse(healthWorkerCard.equals(0));

        // different health workers, same index -> returns false
        HealthWorker diffHealthWorker = new HealthWorkerBuilder().withOrganization("SGH").build();
        assertFalse(healthWorkerCard.equals(new HealthWorkerCard(diffHealthWorker, 0)));
    }

    /**
     * Asserts that {@code healthWorkerCard} displays the details of {@code expectedHealthWorker} correctly
     * and matches {@code expectedId}.
     */
    private void assertCardDisplay(HealthWorkerCard healthWorkerCard, HealthWorker expectedHealthWorker,
                                   int expectedId) {
        guiRobot.pauseForHuman();

        HealthWorkerCardHandle healthWorkerCardHandle = new HealthWorkerCardHandle(healthWorkerCard.getRoot());

        // verify request details are displayed correctly
        assertCardDisplaysHealthWorker(expectedHealthWorker, healthWorkerCardHandle);
    }
}
