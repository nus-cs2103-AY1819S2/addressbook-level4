package seedu.address.logic.commands.request;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.REQ_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.REQ_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_DIALYSIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import seedu.address.testutil.EditRequestDescriptorBuilder;

class EditRequestDescriptorTest {

    @org.junit.jupiter.api.Test
    public void equals() {
        EditRequestCommand.EditRequestDescriptor descriptor =
            new EditRequestCommand.EditRequestDescriptor(REQ_DESC_ALICE);
        assertTrue(REQ_DESC_ALICE.equals(descriptor));

        // same object -> returns true
        assertTrue(REQ_DESC_ALICE.equals(REQ_DESC_ALICE));

        // null -> returns false
        assertFalse(REQ_DESC_ALICE.equals(null));

        // different types -> returns false
        assertFalse(REQ_DESC_ALICE.equals(5));

        // different values -> returns false
        assertFalse(REQ_DESC_ALICE.equals(REQ_DESC_BOB));

        // different name -> returns false
        EditRequestCommand.EditRequestDescriptor editedAlice =
            new EditRequestDescriptorBuilder(REQ_DESC_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(REQ_DESC_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice =
            new EditRequestDescriptorBuilder(REQ_DESC_ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(REQ_DESC_ALICE.equals(editedAlice));

        // different date -> returns false
        editedAlice =
            new EditRequestDescriptorBuilder(REQ_DESC_ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(REQ_DESC_ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice =
            new EditRequestDescriptorBuilder(REQ_DESC_ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(REQ_DESC_ALICE.equals(editedAlice));

        // different conditions -> returns false
        editedAlice =
            new EditRequestDescriptorBuilder(REQ_DESC_ALICE).withConditions(VALID_CONDITION_DIALYSIS).build();
        assertFalse(REQ_DESC_ALICE.equals(editedAlice));


    }

}
