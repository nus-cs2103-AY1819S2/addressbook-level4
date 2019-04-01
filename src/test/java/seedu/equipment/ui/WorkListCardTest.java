package seedu.equipment.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.ui.testutil.GuiTestAssert.assertCardDisplaysWorkList;

import org.junit.Test;

import guitests.guihandles.WorkListCardHandle;
import seedu.equipment.model.WorkList;
import seedu.equipment.testutil.WorkListBuilder;

public class WorkListCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        WorkList workListWithNoTags = new WorkListBuilder().build();
        WorkListCard workListCard = new WorkListCard(workListWithNoTags, 1);
        uiPartRule.setUiPart(workListCard);
        assertCardDisplay(workListCard, workListWithNoTags, 1);

        // with tags
        WorkList workListWithEquipments = new WorkListBuilder().build();
        workListCard = new WorkListCard(workListWithEquipments, 2);
        uiPartRule.setUiPart(workListCard);
        assertCardDisplay(workListCard, workListWithEquipments, 2);
    }

    @Test
    public void equals() {
        WorkList workList = new WorkListBuilder().build();
        WorkListCard workListCard = new WorkListCard(workList, 0);

        // same WorkList, same index -> returns true
        WorkListCard copy = new WorkListCard(workList, 0);
        assertTrue(workListCard.equals(copy));

        // same object -> returns true
        assertTrue(workListCard.equals(workListCard));

        // null -> returns false
        assertFalse(workListCard.equals(null));

        // different types -> returns false
        assertFalse(workListCard.equals(0));

        // different WorkList, same index -> returns false
        WorkList differentWorkList = new WorkListBuilder().withAssignee("differentAssignee").build();
        assertFalse(workListCard.equals(new WorkListCard(differentWorkList, 0)));

        // same WorkList, different index -> returns false
        assertFalse(workListCard.equals(new WorkListCard(workList, 1)));
    }

    /**
     * Asserts that {@code workListCard} displays the details of {@code expectedWorkList} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(WorkListCard workListCard, WorkList expectedWorkList, int expectedId) {
        guiRobot.pauseForHuman();

        WorkListCardHandle workListCardHandle = new WorkListCardHandle(workListCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", workListCardHandle.getId());

        // verify equipment details are displayed correctly
        assertCardDisplaysWorkList(expectedWorkList, workListCardHandle);
    }
}
