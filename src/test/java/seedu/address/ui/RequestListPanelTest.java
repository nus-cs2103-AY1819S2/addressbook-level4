package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalRequests.getTypicalRequests;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import guitests.guihandles.RequestCardHandle;
import guitests.guihandles.RequestListPanelHandle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.tag.Condition;

public class RequestListPanelTest extends GuiUnitTest {
    private static final ObservableList<Request> TYPICAL_REQUESTS =
            FXCollections.observableList(getTypicalRequests());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 3500;

    private final SimpleObjectProperty<Request> selectedRequest = new SimpleObjectProperty<>();
    private RequestListPanelHandle requestListPanelHandle;

    /**
     * Still debugging
     */
    //@Test
    public void display() {
        initUi(TYPICAL_REQUESTS);

        for (int i = 0; i < TYPICAL_REQUESTS.size(); i++) {
            requestListPanelHandle.navigateToCard(TYPICAL_REQUESTS.get(i));
            Request expectedRequest = TYPICAL_REQUESTS.get(i);
            RequestCardHandle actualCard = requestListPanelHandle.getRequestCardHandle(i);

            assertCardDisplaysPerson(expectedRequest, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Still debugging
     */
    //@Test
    public void selection_modelSelectedRequestChanged_selectionChanges() {
        initUi(TYPICAL_REQUESTS);
        Request secondRequest = TYPICAL_REQUESTS.get(INDEX_SECOND.getZeroBased());
        guiRobot.interact(() -> selectedRequest.set(secondRequest));
        guiRobot.pauseForHuman();

        RequestCardHandle expectedRequest = requestListPanelHandle.getRequestCardHandle(INDEX_SECOND.getZeroBased());
        RequestCardHandle selectedRequest = requestListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedRequest, selectedRequest);
    }

    /**
     * Verifies that creating and deleting large number of requests in {@code RequestListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Request> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of request cards exceeded time limit");
    }

    /**
     * Returns a list of requests containing {@code requestCount} requests that is used to populate the
     * {@code RequestListPanel}.
     */
    private ObservableList<Request> createBackingList(int requestCount) {
        ObservableList<Request> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < requestCount; i++) {
            Name name = new Name(i + "Sample Request Name");
            Nric nric = new Nric("S1234567A");
            Phone phone = new Phone("81812288");
            Address address = new Address("123 ABC Road, #09-99");
            RequestDate requestDate = new RequestDate("30-01-2019 10:00:00");
            Set<Condition> conditionList = new HashSet<>();
            Condition condition = new Condition("diabetic");
            conditionList.add(condition);
            Request request = new Request(name, nric, phone, address, requestDate, conditionList);
            backingList.add(request);
        }
        return backingList;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Request> backingList) {
        RequestListPanel requestListPanel =
                new RequestListPanel(backingList, selectedRequest, selectedRequest::set);
        uiPartRule.setUiPart(requestListPanel);

        requestListPanelHandle = new RequestListPanelHandle(getChildNode(requestListPanel.getRoot(),
                RequestListPanelHandle.REQUEST_LIST_VIEW_ID));
    }
}
