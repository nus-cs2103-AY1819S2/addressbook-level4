package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.hms.testutil.EditCustomerDescriptorBuilder;

public class EditCustomerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCustomerCommand.EditCustomerDescriptor descriptorWithSameValues =
            new EditCustomerCommand.EditCustomerDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCustomerCommand.EditCustomerDescriptor editedAmy =
            new EditCustomerDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different dob -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different id -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withIdNum(VALID_ID_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different hms -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
