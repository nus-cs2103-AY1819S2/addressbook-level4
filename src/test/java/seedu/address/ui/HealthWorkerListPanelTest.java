package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthStaff;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysHealthWorker;
import static seedu.address.ui.testutil.GuiTestAssert.assertHealthWorkerCardEquals;

import org.junit.Test;

import guitests.guihandles.HealthWorkerCardHandle;
import guitests.guihandles.HealthWorkerListPanelHandle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;

public class HealthWorkerListPanelTest extends GuiUnitTest {
    private static final ObservableList<HealthWorker> TYPICAL_HEALTH_WORKERS =
            FXCollections.observableList(getTypicalHealthStaff());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 3500;

    private final SimpleObjectProperty<HealthWorker> selectedHealthWorker = new SimpleObjectProperty<>();
    private HealthWorkerListPanelHandle healthWorkerListPanelHandle;

    /**
     * Initialises typical health workers on the UI, then checks for each health worker
     * if the card handle is displaying the same as the health worker.
     */
    @Test
    public void display() {
        initUi(TYPICAL_HEALTH_WORKERS);

        for (int i = 0; i < TYPICAL_HEALTH_WORKERS.size(); i++) {
            healthWorkerListPanelHandle.navigateToCard(TYPICAL_HEALTH_WORKERS.get(i));
            HealthWorker expectedHealthWorker = TYPICAL_HEALTH_WORKERS.get(i);
            HealthWorkerCardHandle actualCard = healthWorkerListPanelHandle.getHealthWorkerCardHandle(i);

            assertCardDisplaysHealthWorker(expectedHealthWorker, actualCard);
        }
    }

    /**
     * Selection test for one of the health workers from typical health workers.
     */
    @Test
    public void selection_modelSelectedHealthWorkerChanged_selectionChanges() {
        initUi(TYPICAL_HEALTH_WORKERS);
        HealthWorker secondHealthWorker = TYPICAL_HEALTH_WORKERS.get(INDEX_SECOND.getZeroBased());
        guiRobot.interact(() -> selectedHealthWorker.set(secondHealthWorker));
        guiRobot.pauseForHuman();

        HealthWorkerCardHandle expectedHealthWorker = healthWorkerListPanelHandle.getHealthWorkerCardHandle(
                INDEX_SECOND.getZeroBased());
        HealthWorkerCardHandle selectedHealthWorker = healthWorkerListPanelHandle.getHandleToSelectedCard();
        assertHealthWorkerCardEquals(expectedHealthWorker, selectedHealthWorker);
    }

    /**
     * Verifies that creating and deleting large number of health workers in
     * {@code HealthWorkerListPanel} requires lesser than {@code CARD_CREATION_AND_DELETION_TIMEOUT}
     * milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<HealthWorker> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of health worker cards exceeded time limit");
    }

    /**
     * Returns a list of health workers containing {@code count} workers that is used to populate the
     * {@code HealthWorkerListPanel}.
     */
    private ObservableList<HealthWorker> createBackingList(int count) {
        ObservableList<HealthWorker> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < count; i++) {
            Name name = new Name("Sally Marymount");
            Nric nric = new Nric("S8677924C");
            Phone phone = new Phone("81526694");
            Organization org = new Organization("NUH");
            Skills skills = new Skills();
            Specialisation specialisation1 = Specialisation.GENERAL_PRACTICE;
            Specialisation specialisation2 = Specialisation.ENDOCRINOLOGY;
            skills.addSpecialisation(specialisation1);
            skills.addSpecialisation(specialisation2);
            HealthWorker worker = new HealthWorker(name, nric, phone, org, skills);
            backingList.add(worker);
        }
        return backingList;
    }

    /**
     * Initializes {@code healthWorkerListPanelHandle} with a {@code healthWorkerListPanel} backed by
     * {@code backingList}. Also shows the {@code Stage} that displays only {@code healthWorkerListPanel}.
     */
    private void initUi(ObservableList<HealthWorker> backingList) {
        HealthWorkerListPanel healthWorkerListPanel =
                new HealthWorkerListPanel(backingList, selectedHealthWorker, selectedHealthWorker::set);
        uiPartRule.setUiPart(healthWorkerListPanel);

        healthWorkerListPanelHandle = new HealthWorkerListPanelHandle(getChildNode(healthWorkerListPanel.getRoot(),
                HealthWorkerListPanelHandle.HEALTH_WORKER_LIST_VIEW_ID));
    }
}
