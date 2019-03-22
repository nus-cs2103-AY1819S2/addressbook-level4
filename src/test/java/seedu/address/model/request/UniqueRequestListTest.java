package seedu.address.model.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.request.exceptions.DuplicateRequestException;
import seedu.address.model.request.exceptions.RequestNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.RequestBuilder;

class UniqueRequestListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRequestList uniqueRequestList = new UniqueRequestList();

    @Test
    public void contains_nullRequest_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRequestList.contains(null));
    }

    @Test
    public void contains_requestNotInList_returnsFalse() {
        assertFalse(uniqueRequestList.contains(ALICE_REQUEST));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueRequestList.add(ALICE_REQUEST);
        assertTrue(uniqueRequestList.contains(ALICE_REQUEST));
    }

    @Test
    public void contains_personWithSameRequestFieldsInList_returnsTrue() {
        uniqueRequestList.add(ALICE_REQUEST);
        Request editedAlice = new RequestBuilder(ALICE_REQUEST).withAddress("Blk 472A, Anchorvale"
            + " Street").build();
        assertTrue(uniqueRequestList.contains(editedAlice));
    }

    @Test
    public void add_nullRequest_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRequestList.add(null));
    }

    @Test
    public void add_duplicateRequest_throwsDuplicateRequestxception() {
        uniqueRequestList.add(ALICE_REQUEST);
        assertThrows(DuplicateRequestException.class, () -> uniqueRequestList
            .add(ALICE_REQUEST));
    }

    @Test
    public void setRequest_nullTargetRequest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequestList.setRequest(null, ALICE_REQUEST));
    }

    @Test
    public void setRequest_nullEditedRequest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequestList.setRequest(ALICE_REQUEST,
            null));
    }

    @Test
    public void setRequest_targetRequestNotInList_throwsRequestNotFoundException() {
        assertThrows(RequestNotFoundException.class, () -> uniqueRequestList.setRequest(ALICE_REQUEST,
            ALICE_REQUEST));
    }

    @Test
    public void setRequest_editedRequestIsSameRequest_success() {
        uniqueRequestList.add(ALICE_REQUEST);
        uniqueRequestList.setRequest(ALICE_REQUEST, ALICE_REQUEST);
        UniqueRequestList expectedUniqueRequestList = new UniqueRequestList();
        expectedUniqueRequestList.add(ALICE_REQUEST);
        assertEquals(expectedUniqueRequestList, uniqueRequestList);
    }

    @Test
    public void setRequest_editedRequestHasSameIdentity_success() {
        uniqueRequestList.add(ALICE_REQUEST);
        Request editedAlice =
            new RequestBuilder(ALICE_REQUEST).withPhone(VALID_PHONE_BOB).build();
        uniqueRequestList.setRequest(ALICE_REQUEST, editedAlice);
        UniqueRequestList expectedUniqueReqList = new UniqueRequestList();
        expectedUniqueReqList.add(editedAlice);
        assertEquals(expectedUniqueReqList, uniqueRequestList);
    }

    @Test
    public void setRequest_editedRequestHasDifferentIdentity_success() {
        uniqueRequestList.add(ALICE_REQUEST);
        uniqueRequestList.setRequest(ALICE_REQUEST, BENSON_REQUEST);
        UniqueRequestList expectedUniqueReqList = new UniqueRequestList();
        expectedUniqueReqList.add(BENSON_REQUEST);
        assertEquals(expectedUniqueReqList, uniqueRequestList);
    }

    @Test
    public void setRequest_editedRequestHasNonUniqueIdentity_throwsDuplicateRequestException() {
        uniqueRequestList.add(ALICE_REQUEST);
        uniqueRequestList.add(BENSON_REQUEST);
        assertThrows(DuplicateRequestException.class, () -> uniqueRequestList
            .setRequest(ALICE_REQUEST, BENSON_REQUEST));
    }

    @Test
    public void remove_nullRequest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequestList.remove(null));
    }

    @Test
    public void remove_requestDoesNotExist_throwsRequestNotFoundException() {
        assertThrows(RequestNotFoundException.class, () -> uniqueRequestList.remove(ALICE_REQUEST));
    }

    @Test
    public void remove_existingReq_removesReq() {
        uniqueRequestList.add(ALICE_REQUEST);
        uniqueRequestList.remove(ALICE_REQUEST);
        UniqueRequestList expectedUniqueReqList = new UniqueRequestList();
        assertEquals(expectedUniqueReqList, uniqueRequestList);
    }

    @Test
    public void setReq_nullUniqueReqList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueRequestList.setRequests((UniqueRequestList) null));
    }

    @Test
    public void setRequests_uniqueRequestList_replacesOwnListWithProvidedUniqueRequestList() {
        uniqueRequestList.add(ALICE_REQUEST);
        UniqueRequestList expectedUniqueReqList = new UniqueRequestList();
        expectedUniqueReqList.add(BENSON_REQUEST);
        uniqueRequestList.setRequests(expectedUniqueReqList);
        assertEquals(expectedUniqueReqList, uniqueRequestList);
    }

    @Test
    public void setReq_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequestList.setRequests((List<Request>) null));
    }

    @Test
    public void setRequests_list_replacesOwnListWithProvidedList() {
        uniqueRequestList.add(ALICE_REQUEST);
        List<Request> requestList = Collections.singletonList(BENSON_REQUEST);
        uniqueRequestList.setRequests(requestList);
        UniqueRequestList expectedUniqueReqList = new UniqueRequestList();
        expectedUniqueReqList.add(BENSON_REQUEST);
        assertEquals(expectedUniqueReqList, uniqueRequestList);
    }

    @Test
    public void setRequests_listWithDuplicateRequests_throwsDuplicateRequestException() {
        List<Request> listWithDuplicatePersons = Arrays.asList(ALICE_REQUEST, ALICE_REQUEST);
        assertThrows(DuplicateRequestException.class, () -> uniqueRequestList
            .setRequests(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueRequestList.asUnmodifiableObservableList().remove(0));
    }
}
