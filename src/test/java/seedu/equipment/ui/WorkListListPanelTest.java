package seedu.equipment.ui;

import static java.time.Duration.ofMillis;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
//import static seedu.equipment.testutil.TypicalIndexes.INDEX_SECOND_WORKLIST;
import static seedu.equipment.testutil.TypicalWorkLists.getTypicalWorkLists;
//import static seedu.equipment.ui.testutil.GuiTestAssert.assertCardDisplaysWorkList;
//import static seedu.equipment.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

//import guitests.guihandles.WorkListCardHandle;

import guitests.guihandles.WorkListListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;

public class WorkListListPanelTest extends GuiUnitTest {
    private static final ObservableList<WorkList> TYPICAL_WORKLISTS =
            FXCollections.observableList(getTypicalWorkLists());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<WorkList> selectedWorkList = new SimpleObjectProperty<>();
    private WorkListListPanelHandle workListListPanelHandle;

    /*@Test
    public void display() {
        initUi(TYPICAL_WORKLISTS);

        for (int i = 0; i < TYPICAL_WORKLISTS.size(); i++) {
            workListListPanelHandle.navigateToCard(TYPICAL_WORKLISTS.get(i));
            WorkList expectedWorkList = TYPICAL_WORKLISTS.get(i);
            WorkListCardHandle actualCard = workListListPanelHandle.getWorkListCardHandle(i);

            assertCardDisplaysWorkList(expectedWorkList, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedWorkListChanged_selectionChanges() {
        initUi(TYPICAL_WORKLISTS);
        WorkList secondWorkList = TYPICAL_WORKLISTS.get(INDEX_SECOND_WORKLIST.getZeroBased());
        guiRobot.interact(() -> selectedWorkList.set(secondWorkList));
        guiRobot.pauseForHuman();

        WorkListCardHandle expectedWorkList = workListListPanelHandle.getWorkListCardHandle(
                INDEX_SECOND_WORKLIST.getZeroBased());
        WorkListCardHandle selectedWorkList = workListListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedWorkList, selectedWorkList);
    }
    */

    /**
     * Verifies that creating and deleting large number of WorkLists in {@code WorkListListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<WorkList> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of WorkList cards exceeded time limit");
    }

    /**
     * Returns a list of WorkLists containing {@code workListCount} WorkLists that is used to populate the
     * {@code WorkListListPanel}.
     */
    private ObservableList<WorkList> createBackingList(int workListCount) {
        ObservableList<WorkList> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < workListCount; i++) {
            String date = "a aaa aaaa";
            String assignee = i + "a";
            WorkListId id = new WorkListId("111");
            WorkList workList = new WorkList(date, assignee, id);
            backingList.add(workList);
        }
        return backingList;
    }

    /**
     * Initializes {@code workListPanelHandle} with a {@code WorkListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code WorkListPanel}.
     */
    private void initUi(ObservableList<WorkList> backingList) {
        WorkListListPanel workListListPanel =
                new WorkListListPanel(backingList, selectedWorkList, selectedWorkList::set);
        uiPartRule.setUiPart(workListListPanel);

        workListListPanelHandle = new WorkListListPanelHandle(getChildNode(workListListPanel.getRoot(),
                WorkListListPanelHandle.WORKLIST_LIST_VIEW_ID));
    }
}
