package seedu.address.model.request;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;

import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;
import seedu.address.testutil.RequestBuilder;

public class RequestTest {

    @Test
    public void isSameRequest() {
        // same object -> returns true
        assertTrue(ALICE_REQUEST.isSameRequest(ALICE_REQUEST));

        // null -> returns false
        assertFalse(ALICE_REQUEST.isSameRequest(null));

        // everything same, but conditions different -> returns true
        Request editedAlice = new RequestBuilder(ALICE_REQUEST).withConditions(new Conditions(
            new HashSet<>(Collections.singletonList(new ConditionTag("Stroke"))))).build();
        assertTrue(ALICE_REQUEST.isSameRequest(editedAlice));

        // different date, everything else same -> returns false
        editedAlice = new RequestBuilder(ALICE_REQUEST).withDate("03-10-2018 10:00:01").build();
        assertFalse(ALICE_REQUEST.isSameRequest(editedAlice));

        // same everything, different health staff -> returns true
        editedAlice = new RequestBuilder(ALICE_REQUEST)
                .withHealthStaff(BETTY).build();
        assertTrue(ALICE_REQUEST.isSameRequest(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Request aliceCopy = new RequestBuilder(ALICE_REQUEST).build();
        assertTrue(ALICE_REQUEST.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_REQUEST.equals(ALICE_REQUEST));

        // different type -> returns false
        assertFalse(ALICE_REQUEST.equals(10));

        // different type -> returns false
        assertFalse(ALICE_REQUEST.equals(BENSON_REQUEST));

        // different name -> returns false
        Request editedAlice = new RequestBuilder(ALICE_REQUEST).withPatient(BENSON).build();
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
