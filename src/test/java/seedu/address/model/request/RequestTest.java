package seedu.address.model.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;

import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.RequestBuilder;

public class RequestTest {

    @Test
    public void test_default_constructor() {
        HashSet<Tag> conditions = new HashSet<>();
        ALICE_REQUEST.getConditions().getConditions().forEach(conditionTag -> conditions.add(
            new Tag(conditionTag.getName())));

        Request aliceRequest = new Request(ALICE_REQUEST.getName(), ALICE_REQUEST.getNric(),
            ALICE_REQUEST.getPhone(), ALICE_REQUEST.getAddress(), ALICE_REQUEST.getRequestDate(),
            conditions);

        assertFalse(aliceRequest.isOngoingStatus());
        assertTrue(aliceRequest.isSameRequest(ALICE_REQUEST));
        assertNotEquals(aliceRequest, ALICE_REQUEST);

        aliceRequest.setHealthStaff("Bobby");
        assertTrue(aliceRequest.isSameRequest(ALICE_REQUEST));
        assertNotEquals(aliceRequest, ALICE_REQUEST);
    }

    @Test
    public void isSameRequest() {
        // same object -> returns true
        assertTrue(ALICE_REQUEST.isSameRequest(ALICE_REQUEST));

        // null -> returns false
        assertFalse(ALICE_REQUEST.isSameRequest(null));

        assertFalse(ALICE_REQUEST.isSameRequest(BENSON_REQUEST));

        Request request = new RequestBuilder(ALICE_REQUEST).withDate("30-10-2019 10:10:10").build();
        assertFalse(request.isSameRequest(ALICE_REQUEST));

        // everything same, but conditions different -> returns true
        Request editedAlice = new RequestBuilder(ALICE_REQUEST).withConditions(new Conditions(
            new HashSet<>(Collections.singletonList(new ConditionTag("Stroke"))))).build();
        assertTrue(ALICE_REQUEST.isSameRequest(editedAlice));

        // everything same, different nric -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withNric("S1234567G").build();
        assertFalse(ALICE_REQUEST.isSameRequest(editedAlice));

        // different date, everything else same -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withDate("03-10-2018 10:00:01").build();
        assertFalse(ALICE_REQUEST.isSameRequest(editedAlice));

        // only nric and condition same, everything else different -> returns false
        editedAlice =
            new RequestBuilder(BENSON_REQUEST).withNric(ALICE_REQUEST.getNric().toString())
                .withConditions(ALICE_REQUEST.getConditions()).build();
        assertFalse(editedAlice.isSameRequest(ALICE_REQUEST));

        // same everything, different health staff -> returns true
        editedAlice = new RequestBuilder(ALICE_REQUEST)
            .withHealthStaff(BETTY).build();
        assertTrue(ALICE_REQUEST.isSameRequest(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertEquals(ALICE_REQUEST, ALICE_REQUEST);

        // same object -> returns true
        assertTrue(ALICE_REQUEST.equals(ALICE_REQUEST));

        // different type -> returns false
        assertFalse(ALICE_REQUEST.equals(10));

        // different type -> returns false
        assertFalse(ALICE_REQUEST.equals(BENSON_REQUEST));

        // different name -> returns false
        Request editedAlice = new RequestBuilder(ALICE_REQUEST).withName(VALID_NAME_BENSON).build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));

        // different nric -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withNric("S9671238G").build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withPhone("91723812").build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));

        // different address -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withAddress("Blk 472a KSD street").build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));

        // different date -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withDate("01-01-2019 10:00:23").build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));

        // different health staff -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withHealthStaff(BETTY).build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));

        // different treatment conditions -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withConditions(new Conditions(
            new HashSet<>(Collections.singletonList(new ConditionTag("Cancer"))))).build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));

        // different isComplete status -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withStatus("COMPLETED").build();
        assertFalse(ALICE_REQUEST.equals(editedAlice));
    }
}
