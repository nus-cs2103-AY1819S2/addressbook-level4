package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.HealthWorkerCardHandle;
import guitests.guihandles.RequestCardHandle;
import guitests.guihandles.RequestListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RequestCardHandle expectedCard, RequestCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getNric(), actualCard.getNric());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getConditions(), actualCard.getConditions());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRequest}.
     */
    public static void assertCardDisplaysRequest(Request expectedRequest, RequestCardHandle actualCard) {
        assertEquals(expectedRequest.getName().toString(), actualCard.getName());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedWorker}.
     */
    public static void assertCardDisplaysHealthWorker(HealthWorker expectedWorker, HealthWorkerCardHandle actualCard) {
        assertEquals(expectedWorker.getName().toString(), actualCard.getName());
        assertEquals(expectedWorker.getOrganization().toString(), actualCard.getOrganisation());
    }

    /**
     * Asserts that the list in {@code requestListPanelHandle} displays the details of {@code requests}
     * correctly and in the correct order.
     */
    public static void assertListMatching(RequestListPanelHandle requestListPanelHandle, Request... requests) {
        for (int i = 0; i < requests.length; i++) {
            requestListPanelHandle.navigateToCard(i);
            assertCardDisplaysRequest(requests[i], requestListPanelHandle.getRequestCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code requestListPanelHandle} displays the details of {@code requests}
     * correctly and in the correct order.
     */
    public static void assertListMatching(RequestListPanelHandle requestListPanelHandle, List<Request> requests) {
        assertListMatching(requestListPanelHandle, requests.toArray(new Request[0]));
    }

    /**
     * Asserts the size of the list in {@code requestListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RequestListPanelHandle requestListPanelHandle, int size) {
        int numberOfRequests = requestListPanelHandle.getListSize();
        assertEquals(size, numberOfRequests);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
